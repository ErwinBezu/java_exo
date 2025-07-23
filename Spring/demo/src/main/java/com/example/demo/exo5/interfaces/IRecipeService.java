package com.example.demo.exo5.interfaces;

import com.example.demo.exo5.model.entity.Recipe;

import java.util.List;
import java.util.UUID;

public interface IRecipeService {
    List<Recipe> getAllRecipes();
    Recipe getRecipeByUUID(UUID uuid);
    Recipe getRecipeByName(String nom);
    Recipe addRecipe(String name, String ingredients, String instructions, UUID categoryId);
    Recipe updateRecipe(Recipe recipe, UUID categoryId);
    void deleteRecipe(UUID id);
}