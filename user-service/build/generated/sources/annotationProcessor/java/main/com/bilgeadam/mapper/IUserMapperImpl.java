package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.request.UpdateUserInformationRequestDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.rabbitmq.model.RegisterMailModel;
import com.bilgeadam.rabbitmq.model.RegisterModel;
import com.bilgeadam.repository.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-29T15:25:47+0300",
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
    public RegisterMailModel fromUserToRegisterMailModel(User user) {
        if ( user == null ) {
            return null;
        }

        RegisterMailModel.RegisterMailModelBuilder registerMailModel = RegisterMailModel.builder();

        registerMailModel.username( user.getUsername() );
        registerMailModel.email( user.getEmail() );
        registerMailModel.activationCode( user.getActivationCode() );

        return registerMailModel.build();
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

    @Override
    public void updateUserInformation(UpdateUserInformationRequestDto dto, User user) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getUserId() != null ) {
            user.setUserId( dto.getUserId() );
        }
        if ( dto.getName() != null ) {
            user.setName( dto.getName() );
        }
        if ( dto.getSurname() != null ) {
            user.setSurname( dto.getSurname() );
        }
        if ( dto.getUsername() != null ) {
            user.setUsername( dto.getUsername() );
        }
        if ( dto.getEmail() != null ) {
            user.setEmail( dto.getEmail() );
        }
    }
}
