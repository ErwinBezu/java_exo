package com.example.demo.exo5.service;

import com.example.demo.exo5.interfaces.IRecipeService;
import com.example.demo.exo5.mapper.RecipeMapper;
import com.example.demo.exo5.model.dto.RecipeDTO;
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
    private final RecipeMapper recipeMapper;

    public RecipeService(RecipeRepository recipeRepository,
                         CategoryRepository categoryRepository,
                         RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.recipeMapper = recipeMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDTO> getAllRecipies() {
        return recipeRepository.findAll()
                .stream()
                .map(recipeMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeDTO getRecipeByUUID(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("L'UUID ne peut pas être null");
        }

        Recipe recipe = recipeRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Recette non trouvée avec l'ID: " + uuid));

        return recipeMapper.toDTO(recipe);
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeDTO getRecipeByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        List<Recipe> recipes = recipeRepository.findByNameStartingWithIgnoreCase(name.trim());
        return recipes.isEmpty() ? null : recipeMapper.toDTO(recipes.get(0));
    }

    @Override
    public RecipeDTO addRecipe(RecipeDTO recipeDTO) {
        if (recipeDTO == null) {
            throw new IllegalArgumentException("La recette ne peut pas être null");
        }
        if (recipeDTO.getCategoryId() == null) {
            throw new IllegalArgumentException("L'ID de la catégorie ne peut pas être null");
        }

        if (recipeRepository.existsByName(recipeDTO.getName().trim())) {
            throw new DataIntegrityViolationException("Une recette avec ce nom existe déjà");
        }

        Category category = categoryRepository.findById(recipeDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Catégorie non trouvée avec l'ID: " + recipeDTO.getCategoryId()));

        Recipe recipe = recipeMapper.toEntity(recipeDTO);
        recipe.setName(recipe.getName().trim());
        recipe.setIngredients(recipe.getIngredients().trim());
        recipe.setInstructions(recipe.getInstructions().trim());
        recipe.setCategory(category);

        Recipe savedRecipe = recipeRepository.save(recipe);
        return recipeMapper.toDTO(savedRecipe);
    }

    @Override
    public RecipeDTO updateRecipe(RecipeDTO recipeDTO) {
        if (recipeDTO == null || recipeDTO.getId() == null) {
            throw new IllegalArgumentException("La recette et son ID ne peuvent pas être null");
        }
        if (recipeDTO.getCategoryId() == null) {
            throw new IllegalArgumentException("L'ID de la catégorie ne peut pas être null");
        }

        Recipe existingRecipe = recipeRepository.findById(recipeDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Recette non trouvée avec l'ID: " + recipeDTO.getId()));

        if (!existingRecipe.getName().equals(recipeDTO.getName()) &&
                recipeRepository.existsByName(recipeDTO.getName())) {
            throw new DataIntegrityViolationException("Une recette avec ce nom existe déjà");
        }

        Category category = categoryRepository.findById(recipeDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Catégorie non trouvée avec l'ID: " + recipeDTO.getCategoryId()));

        existingRecipe.setName(recipeDTO.getName().trim());
        existingRecipe.setIngredients(recipeDTO.getIngredients().trim());
        existingRecipe.setInstructions(recipeDTO.getInstructions().trim());
        existingRecipe.setCategory(category);

        Recipe savedRecipe = recipeRepository.save(existingRecipe);
        return recipeMapper.toDTO(savedRecipe);
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