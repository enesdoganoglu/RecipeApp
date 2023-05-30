package com.bilgeadam.service;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.ForgotPasswordMailResponseDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.UserManagerException;
import com.bilgeadam.manager.IEmailManager;
import com.bilgeadam.manager.IUserProfileManager;
import com.bilgeadam.mapper.IUserMapper;

import com.bilgeadam.rabbitmq.producer.RegisterMailProducer;
import com.bilgeadam.rabbitmq.producer.RegisterProducer;
import com.bilgeadam.repository.IUserRepository;

import com.bilgeadam.repository.entity.User;
import com.bilgeadam.repository.entity.enums.ERole;
import com.bilgeadam.repository.entity.enums.EStatus;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.bilgeadam.utility.CodeGenerator.generateCode;

@Service
public class UserService extends ServiceManager<User, Long> {
    private final IUserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final RegisterMailProducer registerMailProducer;
    private final IUserProfileManager userManager;
    private final RegisterProducer registerProducer ;
    private final PasswordEncoder passwordEncoder;

    private final IEmailManager emailManager;


    public UserService(JpaRepository<User, Long> repository,
                       IUserRepository userRepository,
                       JwtTokenProvider jwtTokenProvider,
                       JwtTokenProvider jwtTokenProvider1, RegisterMailProducer registerMailProducer, IUserProfileManager userManager, RegisterProducer registerProducer,
                       PasswordEncoder passwordEncoder, IEmailManager emailManager) {
        super(repository);
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider1;
        this.registerMailProducer = registerMailProducer;
        this.userManager = userManager;
        this.registerProducer = registerProducer;
        this.passwordEncoder = passwordEncoder;

        this.emailManager = emailManager;
    }

    public RegisterResponseDto registerWithRabbitMq(RegisterRequestDto dto){
        User user = IUserMapper.INSTANCE.fromRequestDtoToUser(dto);
        if (dto.getPassword().equals(dto.getRepassword())){
            user.setActivationCode(generateCode());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            save(user);
            registerProducer.sendNewUser(IUserMapper.INSTANCE.fromUserToRegisterModel(user));
            registerMailProducer.sendActivationCode(IUserMapper.INSTANCE.fromUserToRegisterMailModel(user));
        }else {
            throw new UserManagerException(ErrorType.PASSWORD_ERROR);
        }
        RegisterResponseDto responseDto = IUserMapper.INSTANCE.fromUserToResponseDto(user);
        return responseDto;
    }

    public Boolean passwordChange(FromUserProfilePasswordChangeDto dto){
        Optional<User> user = userRepository.findById(dto.getUserId());
        if (user.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        user.get().setPassword(dto.getPassword());
        userRepository.save(user.get());
        return true;
    }

    public Boolean forgotPassword(String email, String username){
        Optional<User> user = userRepository.findOptionalByEmail(email);
        if (user.get().getStatus().equals(EStatus.ACTIVE)){
            if (user.get().getUsername().equals(username)){
                //random password variable
                String randomPassword = UUID.randomUUID().toString();
                user.get().setPassword(passwordEncoder.encode(randomPassword));
                save(user.get());
                ForgotPasswordMailResponseDto dto = ForgotPasswordMailResponseDto.builder()
                        .password(randomPassword)
                        .email(email)
                        .build();
                emailManager.forgotPasswordMail(dto);
                UserProfileChangePasswordRequestDto userProfileDto = UserProfileChangePasswordRequestDto.builder()
                        .userId(user.get().getUserId())
                        .password(user.get().getPassword())
                        .build();
                userManager.forgotPassword(userProfileDto);
                return true;
            }else {
                throw new UserManagerException(ErrorType.USER_NOT_FOUND);
            }
        }else {
            if (user.get().getStatus().equals(EStatus.DELETED)){
                throw new UserManagerException(ErrorType.USER_NOT_FOUND);
            }
            throw new UserManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        }
    }

    public Boolean activateStatus(ActivateRequestDto dto){
        Optional<User> user = findById(dto.getUserId());
        if (user.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }else if (user.get().getActivationCode().equals(dto.getActivateCode())) {
            user.get().setStatus(EStatus.ACTIVE);
            update(user.get());
            userManager.activateStatus(user.get().getUserId());
            return true;
        }
        throw new UserManagerException(ErrorType.ACTIVATE_CODE_ERROR);
    }
    public String login(LoginRequestDto dto){
        Optional<User> user = userRepository.findOptionalByUsername(dto.getUsername());
        if (user.isEmpty() || !passwordEncoder.matches(dto.getPassword(), user.get().getPassword())){
            throw new UserManagerException(ErrorType.LOGIN_ERROR);
        } else if (!user.get().getStatus().equals(EStatus.ACTIVE)) {
            throw new UserManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        }
        return jwtTokenProvider.createToken(user.get().getUserId(), user.get().getRole())
                .orElseThrow(() -> {throw new UserManagerException(ErrorType.TOKEN_NOT_CREATED);
                });
    }

    public Boolean update(UpdateUserInformationRequestDto dto){
        Optional<User> user = userRepository.findById(dto.getUserId());
        if (user.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }

        IUserMapper.INSTANCE.updateUserInformation(dto, user.get());
        update(user.get());
        return true;
    }

    public Boolean delete(String token){
        Optional<Long> userId = jwtTokenProvider.getIdFromToken(token);
        if (userId.isEmpty()){
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }

        Optional<User> user = userRepository.findById(userId.get());
        if (user.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }

        user.get().setStatus(EStatus.DELETED);
        update(user.get());
        userManager.delete(userId.get());
        return true;
    }

    public List<Long> findByRole(String role) {
        ERole roles = ERole.valueOf(role.toUpperCase(Locale.ENGLISH));
        return userRepository.findByRole(roles).stream().map(x -> x.getUserId()).collect(Collectors.toList());
    }





}
