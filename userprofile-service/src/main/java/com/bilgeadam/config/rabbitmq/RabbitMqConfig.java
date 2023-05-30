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

    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Bean
    DirectExchange exchange(){
        return new DirectExchange(exchange);
    }


    //register consumer queue
    @Value("${rabbitmq.queueRegister}")
    String queueNameRegister;
    @Bean
    Queue registerQueue(){
        return new Queue(queueNameRegister);
    }

    //register address producer

    @Value("${rabbitmq.queueAddressRegister}")
    private String queueAddressRegister;
    @Value("${rabbitmq.addressRegisterBindingKey}")
    private String addressRegisterBindingKey;

    @Bean
    Queue queueAddressRegister(){
        return new Queue(queueAddressRegister);
    }
    @Bean
    public Binding bindingAddressRegister(final Queue queueAddressRegister, final DirectExchange exchange) {
        return BindingBuilder.bind(queueAddressRegister).to(exchange).with(addressRegisterBindingKey);
    }

    //register elasticsearch producer
    @Value("${rabbitmq.queueElasticRegister}")
    private String queueElasticRegister;
    @Value("${rabbitmq.elasticRegisterBindingKey}")
    private String elasticRegisterBindingKey;

    @Bean
    Queue queueElasticRegister(){
        return new Queue(queueElasticRegister);
    }

    @Bean
    public Binding bindingElasticRegister(final Queue queueElasticRegister, final DirectExchange exchange) {
        return BindingBuilder.bind(queueElasticRegister).to(exchange).with(elasticRegisterBindingKey);
    }

}