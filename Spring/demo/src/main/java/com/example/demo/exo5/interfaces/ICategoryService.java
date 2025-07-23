package com.example.demo.exo5.interfaces;

import com.example.demo.exo5.model.dto.CategoryDTO;

import java.util.List;
import java.util.UUID;

public interface ICategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryByUUID(UUID uuid);
    CategoryDTO getCategoryByName(String nom);
    CategoryDTO addCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(CategoryDTO categoryDTO);
    void deleteCategory(UUID id);
}