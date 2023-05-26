package com.bilgeadam.service;

import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.UserManagerException;
import com.bilgeadam.mapper.IUserMapper;
import com.bilgeadam.rabbitmq.producer.RegisterProducer;
import com.bilgeadam.repository.IUserRepository;
import com.bilgeadam.repository.entity.User;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static com.bilgeadam.utility.CodeGenerator.generateCode;

@Service
public class UserService extends ServiceManager<User, Long> {
    private final IUserRepository userRepository;

    private final RegisterProducer registerProducer ;
    private final PasswordEncoder passwordEncoder;


    public UserService(JpaRepository<User, Long> repository,
                       IUserRepository userRepository,
                       JwtTokenProvider jwtTokenProvider,
                       RegisterProducer registerProducer,
                       PasswordEncoder passwordEncoder) {
        super(repository);
        this.userRepository = userRepository;
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
}
