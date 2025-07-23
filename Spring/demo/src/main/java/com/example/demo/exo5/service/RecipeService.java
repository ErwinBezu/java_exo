package com.example.demo.exo5.service;

import com.example.demo.exo5.interfaces.IRecipeService;
import com.example.demo.exo5.model.entity.Category;
import com.example.demo.exo5.model.entity.Recipe;
import com.example.demo.exo5.repository.CategoryRepository;
import com.example.demo.exo5.repository.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RecipeService implements IRecipeService {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;

    public RecipeService(RecipeRepository recipeRepository,
                         CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Recipe getRecipeByUUID(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("L'UUID ne peut pas être null");
        }

        return recipeRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Recette non trouvée avec l'ID: " + uuid));
    }

    @Override
    @Transactional(readOnly = true)
    public Recipe getRecipeByName(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            return null;
        }

        List<Recipe> recipes = recipeRepository.findByNameStartingWithIgnoreCase(nom.trim());
        return recipes.isEmpty() ? null : recipes.get(0);
    }

    @Override
    public Recipe addRecipe(String name, String ingredients, String instructions, UUID categoryId) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la recette ne peut pas être vide");
        }
        if (ingredients == null || ingredients.trim().isEmpty()) {
            throw new IllegalArgumentException("Les ingrédients ne peuvent pas être vides");
        }
        if (instructions == null || instructions.trim().isEmpty()) {
            throw new IllegalArgumentException("Les instructions ne peuvent pas être vides");
        }
        if (categoryId == null) {
            throw new IllegalArgumentException("L'ID de la catégorie ne peut pas être null");
        }

        if (recipeRepository.existsByName(name.trim())) {
            throw new DataIntegrityViolationException("Une recette avec ce nom existe déjà");
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Catégorie non trouvée avec l'ID: " + categoryId));

        Recipe recipe = Recipe.builder()
                .name(name.trim())
                .ingredients(ingredients.trim())
                .instructions(instructions.trim())
                .category(category)
                .build();

        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe updateRecipe(Recipe recipe, UUID categoryId) {
        if (recipe == null || recipe.getId() == null) {
            throw new IllegalArgumentException("La recette et son ID ne peuvent pas être null");
        }
        if (categoryId == null) {
            throw new IllegalArgumentException("L'ID de la catégorie ne peut pas être null");
        }

        Recipe existingRecipe = recipeRepository.findById(recipe.getId())
                .orElseThrow(() -> new EntityNotFoundException("Recette non trouvée avec l'ID: " + recipe.getId()));

        if (!existingRecipe.getName().equals(recipe.getName()) &&
                recipeRepository.existsByName(recipe.getName())) {
            throw new DataIntegrityViolationException("Une recette avec ce nom existe déjà");
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Catégorie non trouvée avec l'ID: " + categoryId));

        existingRecipe.setName(recipe.getName().trim());
        existingRecipe.setIngredients(recipe.getIngredients().trim());
        existingRecipe.setInstructions(recipe.getInstructions().trim());
        existingRecipe.setCategory(category);

        return recipeRepository.save(existingRecipe);
    }

    @Override
    public void deleteRecipe(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }

        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recette non trouvée avec l'ID: " + id));

        recipeRepository.delete(recipe);
    }
}