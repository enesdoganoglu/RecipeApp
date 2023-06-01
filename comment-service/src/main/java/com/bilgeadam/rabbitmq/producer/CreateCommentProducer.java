package com.bilgeadam.rabbitmq.producer;


import com.bilgeadam.rabbitmq.model.CreateCommentModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCommentProducer {
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.bindingKeyCreateComment}")
    private String createCommentBindingKey;

    private final RabbitTemplate rabbitTemplate;
    public Object createComment(CreateCommentModel model){
        return rabbitTemplate.convertSendAndReceive(exchange, createCommentBindingKey, model);
    }
}
