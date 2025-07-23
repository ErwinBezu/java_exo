package com.example.demo.exo5.interfaces;

import com.example.demo.exo5.model.entity.Category;

import java.util.List;
import java.util.UUID;

public interface ICategoryService {
    List<Category> getAllCategories();
    Category getCategoryByUUID(UUID uuid);
    Category getCategoryByName(String nom);
    Category addCategory(String name, String description);
    Category updateCategory(Category category);
    void deleteCategory(UUID id);
}
