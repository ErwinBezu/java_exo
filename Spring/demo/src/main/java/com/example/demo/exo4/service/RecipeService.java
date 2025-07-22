package com.example.demo.exo4.service;

import com.example.demo.exo4.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class RecipeService extends AbstractCrudService<Recipe> {

    @Override
    protected UUID generateIdForEntity(Recipe recipe) {
        return recipe.getId() != null ? recipe.getId() : UUID.randomUUID();
    }

    @Override
    protected void setEntityId(Recipe recipe, UUID id) {
        recipe.setId(id);
    }

    @Override
    public Recipe update(UUID id, Recipe recipe) {
        Recipe existing = entities.get(id);
        if (existing == null) return null;

        if (recipe.getName() != null && !recipe.getName().isBlank()) {
            existing.setName(recipe.getName());
        }
        if (recipe.getIngredientList() != null && !recipe.getIngredientList().isBlank()) {
            existing.setIngredientList(recipe.getIngredientList());
        }
        if (recipe.getInstructions() != null && !recipe.getInstructions().isBlank()) {
            existing.setInstructions(recipe.getInstructions());
        }
        if (recipe.getCategoryId() != null) {
            existing.setCategoryId(recipe.getCategoryId());
        }
        return existing;
    }

    public List<Recipe> findAllRecipes() { return findAll(); }
    public Recipe getRecipeById(UUID id) { return getById(id); }
    public Recipe createRecipe(Recipe recipe) { return create(recipe); }
    public Recipe updateRecipe(UUID id, Recipe recipe) { return update(id, recipe); }
    public void deleteRecipeById(UUID id) { deleteById(id); }
}
