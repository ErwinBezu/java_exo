package com.example.demo.exo5.mapper;

import com.example.demo.exo5.model.dto.RecipeDTO;
import com.example.demo.exo5.model.entity.Recipe;
import org.springframework.stereotype.Service;

@Service
public class RecipeMapper {

    public RecipeDTO toDTO(Recipe recipe) {
        if (recipe == null) return null;
        return new RecipeDTO(
                recipe.getId(),
                recipe.getName(),
                recipe.getIngredients(),
                recipe.getInstructions(),
                recipe.getCategory() != null ? recipe.getCategory().getId() : null,
                recipe.getCategory() != null ? recipe.getCategory().getName() : null
        );
    }

    public Recipe toEntity(RecipeDTO recipeDTO) {
        if (recipeDTO == null) return null;
        return Recipe.builder()
                .id(recipeDTO.getId())
                .name(recipeDTO.getName())
                .ingredients(recipeDTO.getIngredients())
                .instructions(recipeDTO.getInstructions())
                .build();
    }
}