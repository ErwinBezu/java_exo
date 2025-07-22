package com.example.demo.exo4.controller;

import com.example.demo.exo4.model.Category;
import com.example.demo.exo4.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

@Controller
public class CategoryController {

    private CategoryService categoryService;

    public  CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.findAllCategories());
        return "categories/list";
    }

    @GetMapping("/categories/new")
    public String addCategory(Model model) {
        model.addAttribute("category", new Category());
        return "categories/form";
    }

    @GetMapping("/categories/edit/{id}")
    public String updateCategory(@PathVariable UUID id, Model model) {
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            model.addAttribute("category", category);
            return "categories/form";
        }
        return "redirect:/categories";
    }

    @PostMapping("/categories")
    public String saveCategory(@Valid Category category, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "categories/form";
        }

        if (category.getId() != null) {
            categoryService.update(category.getId(), category);
        } else {
            categoryService.createCategory(category);
        }
        return "redirect:/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategoryById(id);
        return "redirect:/categories";
    }
}
