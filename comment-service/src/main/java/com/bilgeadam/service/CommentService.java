package com.bilgeadam.service;

import com.bilgeadam.dto.CreateNewCommentRequestDto;
import com.bilgeadam.exception.CommentManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.rabbitmq.model.CreateCommentModel;
import com.bilgeadam.rabbitmq.model.RecipeResponseModel;
import com.bilgeadam.rabbitmq.producer.CreateCommentProducer;
import com.bilgeadam.repository.ICommentRepository;

import com.bilgeadam.repository.entity.Comment;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService extends ServiceManager<Comment, String> {

    private final CreateCommentProducer createCommentProducer;
    private final JwtTokenProvider jwtTokenProvider;
    private final ICommentRepository commentRepository;

    public CommentService(CreateCommentProducer createCommentProducer, JwtTokenProvider jwtTokenProvider, ICommentRepository commentRepository) {
        super(commentRepository);
        this.createCommentProducer = createCommentProducer;
        this.jwtTokenProvider = jwtTokenProvider;
        this.commentRepository = commentRepository;
    }

    /*public Comment createCommentWithRabbitMq(String token, CreateNewCommentRequestDto dto, String recipeId){
        Optional<Long> userId = jwtTokenProvider.getIdFromToken(token);
        if (userId.isEmpty()){
            throw new CommentManagerException(ErrorType.INVALID_TOKEN);
        }
        CreateCommentModel model = CreateCommentModel.builder().recipeId(recipeId).build();
        RecipeResponseModel recipe = (RecipeResponseModel) createCommentProducer.createComment(model);

        return save();
    }*/
}
