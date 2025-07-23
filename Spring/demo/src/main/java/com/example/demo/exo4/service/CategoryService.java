package com.example.demo.exo4.service;

import com.example.demo.exo4.model.Category;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CategoryService extends AbstractCrudService<Category> {

    @Override
    protected UUID generateIdForEntity(Category category) {
        return category.getId() != null ? category.getId() : UUID.randomUUID();
    }

    @Override
    protected void setEntityId(Category category, UUID id) {
        category.setId(id);
    }

    @Override
    public Category update(UUID id, Category category) {
        Category existing = entities.get(id);
        if (existing == null) return null;

        if (category.getName() != null && !category.getName().isBlank()) {
            existing.setName(category.getName());
        }
        if (category.getDescription() != null && !category.getDescription().isBlank()) {
            existing.setDescription(category.getDescription());
        }
        return existing;
    }

    public List<Category> findAllCategories() {
        return findAll();
    }

    public Category getCategoryById(UUID id) {
        return getById(id);
    }

    public Category createCategory(Category category) {
        return create(category);
    }

    public void deleteCategoryById(UUID id) {
        deleteById(id);
    }

    public Map<UUID, String> getCategoryIdNameMap() {
        Map<UUID, String> map = new LinkedHashMap<>();
        for (Category category : findAllCategories()) {
            map.put(category.getId(), category.getName());
        }
        return map;
    }
}