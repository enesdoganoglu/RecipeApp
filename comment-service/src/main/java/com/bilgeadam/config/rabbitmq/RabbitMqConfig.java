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

    //create comment producer işlemi için gerekli değişkenler
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.queueCreateComment}")
    private String queueComment;
    @Value("${rabbitmq.bindingKeyCreateComment}")
    private String createCommentBindingKey;

    @Bean
    Queue queueComment(){
        return new Queue(queueComment);
    }
    @Bean
    DirectExchange exchange(){
        return new DirectExchange(exchange);
    }
    @Bean
    public Binding commentBindingKey(final Queue queueComment, final DirectExchange exchange){
        return BindingBuilder.bind(queueComment).to(exchange).with(createCommentBindingKey);
    }
}
