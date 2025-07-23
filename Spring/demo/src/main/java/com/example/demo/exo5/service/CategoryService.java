package com.example.demo.exo5.service;

import com.example.demo.exo5.interfaces.ICategoryService;
import com.example.demo.exo5.mapper.CategoryMapper;
import com.example.demo.exo5.model.dto.CategoryDTO;
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
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getCategoryByUUID(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("L'UUID ne peut pas être null");
        }

        Category category = categoryRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Catégorie non trouvée avec l'ID: " + uuid));

        return categoryMapper.toDTO(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getCategoryByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        return categoryRepository.findByName(name)
                .map(categoryMapper::toDTO)
                .orElse(null);
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            throw new IllegalArgumentException("La catégorie ne peut pas être null");
        }

        if (categoryRepository.existsByName(categoryDTO.getName().trim())) {
            throw new DataIntegrityViolationException("Une catégorie avec ce nom existe déjà");
        }

        Category category = categoryMapper.toEntity(categoryDTO);
        category.setName(category.getName().trim());
        category.setDescription(category.getDescription().trim());

        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        if (categoryDTO == null || categoryDTO.getId() == null) {
            throw new IllegalArgumentException("La catégorie et son ID ne peuvent pas être null");
        }

        Category existingCategory = categoryRepository.findById(categoryDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Catégorie non trouvée avec l'ID: " + categoryDTO.getId()));

        if (!existingCategory.getName().equals(categoryDTO.getName()) &&
                categoryRepository.existsByName(categoryDTO.getName())) {
            throw new DataIntegrityViolationException("Une catégorie avec ce nom existe déjà");
        }

        existingCategory.setName(categoryDTO.getName().trim());
        existingCategory.setDescription(categoryDTO.getDescription().trim());

        Category savedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.toDTO(savedCategory);
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
