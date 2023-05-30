package com.bilgeadam.dto.request;

import com.bilgeadam.repository.entity.Ingredient;
import com.bilgeadam.repository.entity.NutritionalValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateRecipeRequestDto {

    private String foodName;
    private String typesOfFood;
    private double periodOfPreparation;
    private double cookingTime;
    private String recipeInstructions ;
    private List<Ingredient> ingredients;
    private NutritionalValue nutritionalValue;
    private String categoryId;

}
