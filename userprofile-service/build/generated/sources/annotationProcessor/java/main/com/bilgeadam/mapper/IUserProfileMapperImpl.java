package com.bilgeadam.mapper;

import com.bilgeadam.rabbitmq.model.RegisterModel;
import com.bilgeadam.repository.entity.UserProfile;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-26T10:29:10+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.jar, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class IUserProfileMapperImpl implements IUserProfileMapper {

    @Override
    public UserProfile fromRegisterModelToUserProfile(RegisterModel registerModel) {
        if ( registerModel == null ) {
            return null;
        }

        UserProfile.UserProfileBuilder<?, ?> userProfile = UserProfile.builder();

        userProfile.userId( registerModel.getUserId() );
        userProfile.name( registerModel.getName() );
        userProfile.surname( registerModel.getSurname() );
        userProfile.username( registerModel.getUsername() );
        userProfile.email( registerModel.getEmail() );
        userProfile.password( registerModel.getPassword() );

        return userProfile.build();
    }
}
