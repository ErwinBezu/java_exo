package com.example.demo.exo5.interfaces;

import com.example.demo.exo5.model.dto.RecipeDTO;

import java.util.List;
import java.util.UUID;

public interface IRecipeService {
    List<RecipeDTO> getAllRecipies();
    RecipeDTO getRecipeByUUID(UUID uuid);
    RecipeDTO getRecipeByName(String nom);
    RecipeDTO addRecipe(RecipeDTO recipeDTO);
    RecipeDTO updateRecipe(RecipeDTO recipeDTO);
    void deleteRecipe(UUID id);
}