package com.example.demo.exo5.mapper;

import com.example.demo.exo5.model.dto.CategoryDTO;
import com.example.demo.exo5.model.entity.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {

    public CategoryDTO toDTO(Category category) {
        if (category == null) return null;
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    public Category toEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null) return null;
        return Category.builder()
                .id(categoryDTO.getId())
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .build();
    }
}
