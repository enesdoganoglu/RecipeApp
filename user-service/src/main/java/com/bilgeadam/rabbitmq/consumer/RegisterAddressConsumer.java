package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.RegisterAddressModel;
import com.bilgeadam.service.AddressService;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterAddressConsumer {
    private final AddressService addressService;

    @RabbitListener(queues = ("${rabbitmq.queueAddressRegister}"))
    public void newUserAddressCreate(RegisterAddressModel model){
        log.info("User {}", model.toString());
        addressService.createUserAddressWithRabbitMq(model);
    }
}
