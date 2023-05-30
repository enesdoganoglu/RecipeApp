package com.bilgeadam.service;



import com.bilgeadam.dto.request.CreateNewRecipeRequestDto;
import com.bilgeadam.dto.request.UpdateRecipeRequestDto;
import com.bilgeadam.dto.response.UserProfileResponseDto;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.RecipeManagerException;
import com.bilgeadam.manager.IRecipeManager;
import com.bilgeadam.mapper.IRecipeMapper;
import com.bilgeadam.repository.IRecipeRepository;
import com.bilgeadam.repository.entity.Recipe;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecipeService extends ServiceManager<Recipe, String> {
    private final IRecipeRepository recipeRepository;

    private final IRecipeMapper recipeMapper;
    private final IRecipeManager recipeManager;
    private final JwtTokenProvider jwtTokenProvider;


    public RecipeService(IRecipeRepository recipeRepository, IRecipeMapper recipeMapper, IRecipeManager userProfileManager, JwtTokenProvider jwtTokenProvider) {
        super(recipeRepository);
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
        this.recipeManager = userProfileManager;
        this.jwtTokenProvider = jwtTokenProvider;

    }


    public Recipe createRecipe(String token, CreateNewRecipeRequestDto dto){
        Optional<Long> userId = jwtTokenProvider.getIdFromToken(token);
        if (userId.isEmpty()){
            throw new RecipeManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<String> userRole = jwtTokenProvider.getRoleFromToken(token);
        if(userRole.get().equals("USER")){
            throw new RecipeManagerException(ErrorType.ONLY_ADMIN);
        }
        UserProfileResponseDto userProfile = recipeManager.findByUserProfileDto(userId.get()).getBody();
        Recipe recipe = IRecipeMapper.INSTANCE.toPost(dto);
        recipe.setUserId(userProfile.getUserId());
        recipe.setUsername(userProfile.getUsername());
        recipe.setAvatar(userProfile.getAvatar());
        return save(recipe);
    }

    public Recipe updateRecipe(String token, String recipeId, UpdateRecipeRequestDto dto){
        Optional<Long> userId = jwtTokenProvider.getIdFromToken(token);
        userId.orElseThrow(() -> new RecipeManagerException(ErrorType.INVALID_TOKEN));

        Optional<String> userRole = jwtTokenProvider.getRoleFromToken(token);
        if(userRole.get().equals("USER")){
            throw new RecipeManagerException(ErrorType.ONLY_ADMIN);
        }

        UserProfileResponseDto userProfile = recipeManager.findByUserProfileDto(userId.get()).getBody();
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        if (userProfile.getUserId().equals(recipe.get().getUserId())){
            IRecipeMapper.INSTANCE.updateRecipe(dto, recipe.get());
            return update(recipe.get());
        }
        throw new RecipeManagerException(ErrorType.RECIPE_NOT_FOUND);
    }

    public Boolean deleteRecipe(String token, String recipeId){
        Optional<Long> userId = jwtTokenProvider.getIdFromToken(token);
        if (userId.isEmpty()){
            throw new RecipeManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<String> userRole = jwtTokenProvider.getRoleFromToken(token);
        if(userRole.get().equals("USER")){
            throw new RecipeManagerException(ErrorType.ONLY_ADMIN);
        }
        UserProfileResponseDto userProfile = recipeManager.findByUserProfileDto(userId.get()).getBody();
        Optional<Recipe> recipe = findById(recipeId);
        if (recipe.get().getUserId().equals(userProfile.getUserId())){

            //recipe silme
            deleteById(recipeId);
            return true;
        }else {

            throw new RecipeManagerException(ErrorType.RECIPE_NOT_FOUND);
        }
    }





}
