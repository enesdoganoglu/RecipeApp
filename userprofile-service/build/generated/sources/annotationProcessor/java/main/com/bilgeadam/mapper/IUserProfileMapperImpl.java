package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.RegisterAddressDto;
import com.bilgeadam.dto.request.ToAuthPasswordChangeDto;
import com.bilgeadam.dto.request.UpdateUserInformationRequestDto;
import com.bilgeadam.dto.request.UserProfileUpdateRequestDto;
import com.bilgeadam.dto.response.UserProfileResponseDto;
import com.bilgeadam.rabbitmq.model.RegisterAddressModel;
import com.bilgeadam.rabbitmq.model.RegisterElasticModel;
import com.bilgeadam.rabbitmq.model.RegisterModel;
import com.bilgeadam.repository.entity.UserProfile;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-30T12:52:22+0300",
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

    @Override
    public RegisterElasticModel fromUserProfileToElasticModel(UserProfile userProfile) {
        if ( userProfile == null ) {
            return null;
        }

        RegisterElasticModel.RegisterElasticModelBuilder registerElasticModel = RegisterElasticModel.builder();

        registerElasticModel.id( userProfile.getId() );
        registerElasticModel.userId( userProfile.getUserId() );
        registerElasticModel.username( userProfile.getUsername() );
        registerElasticModel.email( userProfile.getEmail() );

        return registerElasticModel.build();
    }

    @Override
    public RegisterAddressModel fromUserProfileToRegisterAddressModel(UserProfile userProfile) {
        if ( userProfile == null ) {
            return null;
        }

        RegisterAddressModel.RegisterAddressModelBuilder registerAddressModel = RegisterAddressModel.builder();

        registerAddressModel.userId( userProfile.getUserId() );
        registerAddressModel.avenue( userProfile.getAvenue() );
        registerAddressModel.neighbourhood( userProfile.getNeighbourhood() );
        registerAddressModel.district( userProfile.getDistrict() );
        registerAddressModel.province( userProfile.getProvince() );
        registerAddressModel.country( userProfile.getCountry() );
        registerAddressModel.apartmentNo( userProfile.getApartmentNo() );
        registerAddressModel.houseNo( userProfile.getHouseNo() );
        registerAddressModel.zipCode( userProfile.getZipCode() );

        return registerAddressModel.build();
    }

    @Override
    public ToAuthPasswordChangeDto fromUserProfileToUserPasswordChangeDto(UserProfile userProfile) {
        if ( userProfile == null ) {
            return null;
        }

        ToAuthPasswordChangeDto.ToAuthPasswordChangeDtoBuilder toAuthPasswordChangeDto = ToAuthPasswordChangeDto.builder();

        toAuthPasswordChangeDto.userId( userProfile.getUserId() );
        toAuthPasswordChangeDto.password( userProfile.getPassword() );

        return toAuthPasswordChangeDto.build();
    }

    @Override
    public UserProfile updateFromDtoToUserProfile(UserProfileUpdateRequestDto dto, UserProfile userProfile) {
        if ( dto == null ) {
            return userProfile;
        }

        if ( dto.getName() != null ) {
            userProfile.setName( dto.getName() );
        }
        if ( dto.getSurname() != null ) {
            userProfile.setSurname( dto.getSurname() );
        }
        if ( dto.getUsername() != null ) {
            userProfile.setUsername( dto.getUsername() );
        }
        if ( dto.getEmail() != null ) {
            userProfile.setEmail( dto.getEmail() );
        }
        if ( dto.getAvatar() != null ) {
            userProfile.setAvatar( dto.getAvatar() );
        }

        return userProfile;
    }

    @Override
    public UserProfile createFromAddressDtoToUserProfile(RegisterAddressDto dto, UserProfile userProfile) {
        if ( dto == null ) {
            return userProfile;
        }

        if ( dto.getAvenue() != null ) {
            userProfile.setAvenue( dto.getAvenue() );
        }
        if ( dto.getNeighbourhood() != null ) {
            userProfile.setNeighbourhood( dto.getNeighbourhood() );
        }
        if ( dto.getDistrict() != null ) {
            userProfile.setDistrict( dto.getDistrict() );
        }
        if ( dto.getProvince() != null ) {
            userProfile.setProvince( dto.getProvince() );
        }
        if ( dto.getCountry() != null ) {
            userProfile.setCountry( dto.getCountry() );
        }
        if ( dto.getApartmentNo() != null ) {
            userProfile.setApartmentNo( dto.getApartmentNo() );
        }
        if ( dto.getHouseNo() != null ) {
            userProfile.setHouseNo( dto.getHouseNo() );
        }
        if ( dto.getZipCode() != null ) {
            userProfile.setZipCode( dto.getZipCode() );
        }

        return userProfile;
    }

    @Override
    public UpdateUserInformationRequestDto toUpdateUserInformation(UserProfileUpdateRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        UpdateUserInformationRequestDto.UpdateUserInformationRequestDtoBuilder updateUserInformationRequestDto = UpdateUserInformationRequestDto.builder();

        updateUserInformationRequestDto.name( dto.getName() );
        updateUserInformationRequestDto.surname( dto.getSurname() );
        updateUserInformationRequestDto.username( dto.getUsername() );
        updateUserInformationRequestDto.email( dto.getEmail() );

        return updateUserInformationRequestDto.build();
    }

    @Override
    public UserProfileResponseDto fromUserProfileToResponseDto(UserProfile userProfile) {
        if ( userProfile == null ) {
            return null;
        }

        UserProfileResponseDto.UserProfileResponseDtoBuilder userProfileResponseDto = UserProfileResponseDto.builder();

        userProfileResponseDto.userId( userProfile.getId() );
        userProfileResponseDto.username( userProfile.getUsername() );
        userProfileResponseDto.avatar( userProfile.getAvatar() );

        return userProfileResponseDto.build();
    }
}
