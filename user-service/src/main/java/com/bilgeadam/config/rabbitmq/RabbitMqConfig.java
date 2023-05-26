package com.bilgeadam.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Value("${rabbitmq.exchange-user}")
    String exchange;

    @Bean
    DirectExchange exchangeUser() {
        return new DirectExchange(exchange);
    }

    //Register işlemi için
    @Value("${rabbitmq.registerKey}")
    String registerBindingKey;
    @Value("${rabbitmq.queueRegister}")
    String queueNameRegister;

    @Bean
    Queue registerQueue() {
        return new Queue(queueNameRegister);
    }

    @Bean
    public Binding bindingRegister(final Queue registerQueue, final DirectExchange exchangeAuth) {
        return BindingBuilder.bind(registerQueue).to(exchangeAuth).with(registerBindingKey);
    }
}