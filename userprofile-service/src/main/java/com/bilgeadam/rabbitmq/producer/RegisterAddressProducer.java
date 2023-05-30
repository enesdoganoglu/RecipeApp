package com.bilgeadam.rabbitmq.producer;


import com.bilgeadam.rabbitmq.model.RegisterAddressModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterAddressProducer {
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.addressRegisterBindingKey}")
    private String addressRegisterBindingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendNewUserAddress(RegisterAddressModel registerAddressModel){
        rabbitTemplate.convertAndSend(exchange, addressRegisterBindingKey, registerAddressModel);
    }

}
