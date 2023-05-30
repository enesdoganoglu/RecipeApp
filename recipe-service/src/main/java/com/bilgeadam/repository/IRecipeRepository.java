package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IRecipeRepository extends MongoRepository<Recipe, String> {

    /*List<Recipe> findByOrderByYas(); // A...Z
    List<Recipe> findAllByOrderByYasDesc(); //z..a*/
}
