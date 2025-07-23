package com.example.demo.exo5.service;

import com.example.demo.exo5.interfaces.ICategoryService;
import com.example.demo.exo5.model.entity.Category;
import com.example.demo.exo5.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategoryByUUID(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("L'UUID ne peut pas être null");
        }

        return categoryRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Catégorie non trouvée avec l'ID: " + uuid));
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategoryByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        return categoryRepository.findByName(name).orElse(null);
    }

    @Override
    public Category addCategory(String name, String description) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la catégorie ne peut pas être vide");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("La description ne peut pas être vide");
        }

        if (categoryRepository.existsByName(name.trim())) {
            throw new DataIntegrityViolationException("Une catégorie avec ce nom existe déjà");
        }

        Category category = Category.builder()
                .name(name.trim())
                .description(description.trim())
                .build();

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        if (category == null || category.getId() == null) {
            throw new IllegalArgumentException("La catégorie et son ID ne peuvent pas être null");
        }

        Category existingCategory = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new EntityNotFoundException("Catégorie non trouvée avec l'ID: " + category.getId()));

        if (!existingCategory.getName().equals(category.getName()) &&
                categoryRepository.existsByName(category.getName())) {
            throw new DataIntegrityViolationException("Une catégorie avec ce nom existe déjà");
        }

        existingCategory.setName(category.getName().trim());
        existingCategory.setDescription(category.getDescription().trim());

        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Catégorie non trouvée avec l'ID: " + id));

        long recipeCount = categoryRepository.countRecipesByCategory(id);
        if (recipeCount > 0) {
            throw new DataIntegrityViolationException(
                    "Impossible de supprimer la catégorie: " + recipeCount + " recette(s) y sont associées");
        }

        categoryRepository.delete(category);
    }
}