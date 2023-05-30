package com.bilgeadam.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document
public class Recipe {
    @Id
    private String id;
    private String userId;
    private String username;
    private String avatar;
    private String foodName;
    private String typesOfFood;
    private double periodOfPreparation;
    private double cookingTime;
    private String recipeInstructions ;
    private List<Ingredient> ingredients;
    private NutritionalValue nutritionalValue;
    private String categoryId;
    private List<String> likes = new ArrayList<>();
    private List<String> dislikes;
    private List<String> comments;
}
