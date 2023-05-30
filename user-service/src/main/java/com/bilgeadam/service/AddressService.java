package com.bilgeadam.service;

import com.bilgeadam.mapper.IAddressMapper;

import com.bilgeadam.rabbitmq.model.RegisterAddressModel;
import com.bilgeadam.repository.IAddressRepository;
import com.bilgeadam.repository.entity.Address;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends ServiceManager<Address, Long> {
    private final IAddressRepository addressRepository;

    public AddressService(JpaRepository<Address, Long> repository, IAddressRepository addressRepository) {
        super(repository);
        this.addressRepository = addressRepository;
    }

    public Boolean createUserAddressWithRabbitMq(RegisterAddressModel model){
        try {
            Address address = save(IAddressMapper.INSTANCE.fromRegisterAddressModelToAddress(model));

            return true;
        }catch (Exception e){
            throw new RuntimeException("Beklenmeyen bir hata oluştu.");
        }
    }
}
