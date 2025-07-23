package com.example.demo.exo5.controller;

import com.example.demo.exo5.interfaces.ICategoryService;
import com.example.demo.exo5.model.dto.CategoryDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/categorie")
public class CategoryController {

    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String listPage(Model model) {
        model.addAttribute("allCategorie", categoryService.getAllCategories());
        model.addAttribute("isFilter", false);
        return "listCategorie";
    }

    @GetMapping("/detail")
    public String detailPage(@RequestParam("uuid") UUID uuid, Model model) {
        try {
            CategoryDTO category = categoryService.getCategoryByUUID(uuid);
            model.addAttribute("categorie", category);
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
    public String submitCategory(@Validated @ModelAttribute("categorie") CategoryDTO categorie,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "addCategorie";
        }

        try {
            if (categorie.getId() != null) {
                categoryService.updateCategory(categorie);
            } else {
                categoryService.addCategory(categorie);
            }
            return "redirect:/categorie/list";
        } catch (DataIntegrityViolationException e) {
            return "addCategorie";
        }
    }

    @GetMapping("/update")
    public String updateCategory(@RequestParam("uuid") UUID uuid, Model model) {
        try {
            CategoryDTO category = categoryService.getCategoryByUUID(uuid);
            model.addAttribute("categorie", category);
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