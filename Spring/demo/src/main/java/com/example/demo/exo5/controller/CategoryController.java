package com.example.demo.exo5.controller;

import com.example.demo.exo5.interfaces.ICategoryService;
import com.example.demo.exo5.mapper.CategoryMapper;
import com.example.demo.exo5.model.dto.CategoryDTO;
import com.example.demo.exo5.model.entity.Category;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/categorie")
public class CategoryController {

    private final ICategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(ICategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping("/list")
    public String listPage(Model model) {
        List<Category> categories = categoryService.getAllCategories();

        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(categoryMapper::toDTO)
                .toList();

        model.addAttribute("allCategorie", categoryDTOs);
        model.addAttribute("isFilter", false);
        return "listCategorie";
    }

    @GetMapping("/detail")
    public String detailPage(@RequestParam("uuid") UUID uuid, Model model) {
        try {
            Category category = categoryService.getCategoryByUUID(uuid);
            CategoryDTO categoryDTO = categoryMapper.toDTO(category);
            model.addAttribute("categorie", categoryDTO);
            return "detailCategorie";
        } catch (EntityNotFoundException e) {
            return "redirect:/categorie/list";
        }
    }

    @GetMapping("/add")
    public String addCategory(Model model) {
        CategoryDTO categorie = new CategoryDTO();
        model.addAttribute("categorie", categorie);
        return "addCategorie";
    }

    @PostMapping("/add")
    public String submitCategory(@Validated @ModelAttribute("categorie") CategoryDTO categoryDTO,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "addCategorie";
        }

        try {
            if (categoryDTO.getId() != null) {
                Category category = categoryMapper.toEntity(categoryDTO);
                categoryService.updateCategory(category);
            } else {
                categoryService.addCategory(categoryDTO.getName(), categoryDTO.getDescription());
            }
            return "redirect:/categorie/list";
        } catch (DataIntegrityViolationException e) {
            return "addCategorie";
        }
    }

    @GetMapping("/update")
    public String updateCategory(@RequestParam("uuid") UUID uuid, Model model) {
        try {
            Category category = categoryService.getCategoryByUUID(uuid);

            CategoryDTO categoryDTO = categoryMapper.toDTO(category);

            model.addAttribute("categorie", categoryDTO);
            return "addCategorie";
        } catch (EntityNotFoundException e) {
            return "redirect:/categorie/list";
        }
    }

    @GetMapping("/delete")
    public String deleteCategory(@RequestParam("uuid") UUID uuid) {
        try {
            categoryService.deleteCategory(uuid);
        } catch (Exception e) {
        }
        return "redirect:/categorie/list";
    }
}