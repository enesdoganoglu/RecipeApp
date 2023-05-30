package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateNewRecipeRequestDto;
import com.bilgeadam.dto.request.UpdateRecipeRequestDto;
import com.bilgeadam.repository.entity.Recipe;
import com.bilgeadam.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bilgeadam.constant.ApiUrls.*;

@RestController
@RequestMapping(RECIPE)
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping(CREATE + "/{token}")
    public ResponseEntity<Recipe> createRecipe(@PathVariable String token, @RequestBody CreateNewRecipeRequestDto dto){
        return ResponseEntity.ok(recipeService.createRecipe(token, dto));
    }

    @DeleteMapping(DELETE_BY_ID)
    public ResponseEntity<Boolean> deleteRecipe(String token, String recipeId){
        return ResponseEntity.ok(recipeService.deleteRecipe(token, recipeId));
    }


    @PutMapping(UPDATE)
    public ResponseEntity<Recipe> updateRecipe(String token,String recipeId, UpdateRecipeRequestDto dto){
        return ResponseEntity.ok(recipeService.updateRecipe(token, recipeId, dto));
    }
}
