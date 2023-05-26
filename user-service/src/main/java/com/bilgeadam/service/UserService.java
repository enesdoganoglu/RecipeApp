package com.bilgeadam.service;

import com.bilgeadam.dto.request.ActivateRequestDto;
import com.bilgeadam.dto.request.LoginRequestDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.request.UpdateEmailOrUsernameRequestDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.UserManagerException;
import com.bilgeadam.manager.IUserProfileManager;
import com.bilgeadam.mapper.IUserMapper;
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
import java.util.stream.Collectors;

import static com.bilgeadam.utility.CodeGenerator.generateCode;

@Service
public class UserService extends ServiceManager<User, Long> {
    private final IUserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final IUserProfileManager userManager;
    private final RegisterProducer registerProducer ;
    private final PasswordEncoder passwordEncoder;


    public UserService(JpaRepository<User, Long> repository,
                       IUserRepository userRepository,
                       JwtTokenProvider jwtTokenProvider,
                       JwtTokenProvider jwtTokenProvider1, IUserProfileManager userManager, RegisterProducer registerProducer,
                       PasswordEncoder passwordEncoder) {
        super(repository);
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider1;
        this.userManager = userManager;
        this.registerProducer = registerProducer;
        this.passwordEncoder = passwordEncoder;

    }

    public RegisterResponseDto registerWithRabbitMq(RegisterRequestDto dto){
        User user = IUserMapper.INSTANCE.fromRequestDtoToUser(dto);
        if (dto.getPassword().equals(dto.getRepassword())){
            user.setActivationCode(generateCode());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            save(user);
            registerProducer.sendNewUser(IUserMapper.INSTANCE.fromUserToRegisterModel(user));
        }else {
            throw new UserManagerException(ErrorType.PASSWORD_ERROR);
        }
        RegisterResponseDto responseDto = IUserMapper.INSTANCE.fromUserToResponseDto(user);
        return responseDto;
    }

    public Boolean activateStatus(ActivateRequestDto dto){
        Optional<User> auth = findById(dto.getUserId());
        if (auth.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }else if (auth.get().getActivationCode().equals(dto.getActivateCode())) {
            auth.get().setStatus(EStatus.ACTIVE);
            update(auth.get());
            userManager.activateStatus(auth.get().getUserId());
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

    public Boolean update(UpdateEmailOrUsernameRequestDto dto){
        Optional<User> user = userRepository.findById(dto.getUserId());
        if (user.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }

        IUserMapper.INSTANCE.updateUsernameOrEmail(dto, user.get());
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




}
