package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.rabbitmq.model.RegisterModel;
import com.bilgeadam.repository.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-26T19:37:37+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.jar, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class IUserMapperImpl implements IUserMapper {

    @Override
    public User fromRequestDtoToUser(RegisterRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder<?, ?> user = User.builder();

        user.name( dto.getName() );
        user.surname( dto.getSurname() );
        user.username( dto.getUsername() );
        user.email( dto.getEmail() );
        user.password( dto.getPassword() );

        return user.build();
    }

    @Override
    public RegisterModel fromUserToRegisterModel(User user) {
        if ( user == null ) {
            return null;
        }

        RegisterModel.RegisterModelBuilder registerModel = RegisterModel.builder();

        registerModel.userId( user.getUserId() );
        registerModel.name( user.getName() );
        registerModel.surname( user.getSurname() );
        registerModel.username( user.getUsername() );
        registerModel.email( user.getEmail() );
        registerModel.password( user.getPassword() );

        return registerModel.build();
    }

    @Override
    public RegisterResponseDto fromUserToResponseDto(User user) {
        if ( user == null ) {
            return null;
        }

        RegisterResponseDto.RegisterResponseDtoBuilder registerResponseDto = RegisterResponseDto.builder();

        registerResponseDto.userId( user.getUserId() );
        registerResponseDto.username( user.getUsername() );
        registerResponseDto.activationCode( user.getActivationCode() );

        return registerResponseDto.build();
    }
}
