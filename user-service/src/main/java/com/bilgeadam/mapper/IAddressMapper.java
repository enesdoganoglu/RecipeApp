package com.bilgeadam.mapper;

import com.bilgeadam.rabbitmq.model.RegisterAddressModel;
import com.bilgeadam.repository.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAddressMapper {

    IAddressMapper INSTANCE = Mappers.getMapper(IAddressMapper.class);
    Address fromRegisterAddressModelToAddress(final RegisterAddressModel model);
}
