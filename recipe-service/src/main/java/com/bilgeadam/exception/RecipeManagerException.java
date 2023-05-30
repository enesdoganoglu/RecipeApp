package com.bilgeadam.exception;

import lombok.Getter;

@Getter
public class RecipeManagerException extends RuntimeException {

    private final ErrorType errorType;


    public RecipeManagerException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public RecipeManagerException(ErrorType errorType){
        this.errorType = errorType;
    }
}
