package com.bilgeadam.service;

import com.bilgeadam.dto.request.ActivateRequestDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.UserManagerException;
import com.bilgeadam.manager.IUserProfileManager;
import com.bilgeadam.mapper.IUserMapper;
import com.bilgeadam.rabbitmq.producer.RegisterProducer;
import com.bilgeadam.repository.IUserRepository;
import com.bilgeadam.repository.entity.User;
import com.bilgeadam.repository.entity.enums.EStatus;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.bilgeadam.utility.CodeGenerator.generateCode;

@Service
public class UserService extends ServiceManager<User, Long> {
    private final IUserRepository userRepository;

    private final IUserProfileManager userManager;
    private final RegisterProducer registerProducer ;
    private final PasswordEncoder passwordEncoder;


    public UserService(JpaRepository<User, Long> repository,
                       IUserRepository userRepository,
                       JwtTokenProvider jwtTokenProvider,
                       IUserProfileManager userManager, RegisterProducer registerProducer,
                       PasswordEncoder passwordEncoder) {
        super(repository);
        this.userRepository = userRepository;
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


}
