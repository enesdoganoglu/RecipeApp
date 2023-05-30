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
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserProfileMapper {

    IUserProfileMapper INSTANCE = Mappers.getMapper(IUserProfileMapper.class);

    UserProfile fromRegisterModelToUserProfile(final RegisterModel registerModel);

    RegisterElasticModel fromUserProfileToElasticModel(final UserProfile userProfile);

    RegisterAddressModel fromUserProfileToRegisterAddressModel(final UserProfile userProfile);


    ToAuthPasswordChangeDto fromUserProfileToUserPasswordChangeDto(final UserProfile userProfile);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserProfile updateFromDtoToUserProfile(UserProfileUpdateRequestDto dto, @MappingTarget UserProfile userProfile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserProfile createFromAddressDtoToUserProfile(RegisterAddressDto dto, @MappingTarget UserProfile userProfile);


    UpdateUserInformationRequestDto toUpdateUserInformation(final UserProfileUpdateRequestDto dto);

    @Mapping(source = "id", target = "userId")
    UserProfileResponseDto fromUserProfileToResponseDto(final UserProfile userProfile);
}