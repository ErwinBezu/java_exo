package com.example.demo.exo5.controller;

import com.example.demo.exo5.interfaces.ICategoryService;
import com.example.demo.exo5.interfaces.IRecipeService;
import com.example.demo.exo5.model.dto.CategoryDTO;
import com.example.demo.exo5.model.dto.RecipeDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/recette")
public class RecipeController {

    private final IRecipeService recipeService;
    private final ICategoryService categoryService;

    public RecipeController(IRecipeService recipeService, ICategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String listPage(Model model) {
        List<CategoryDTO> allCategories = categoryService.getAllCategories();
        Map<UUID, String> categories = new HashMap<>();
        for (CategoryDTO category : allCategories) {
            categories.put(category.getId(), category.getName());
        }

        model.addAttribute("allRecette", recipeService.getAllRecipies());
        model.addAttribute("allCategory", categories);
        model.addAttribute("isFilter", false);
        return "listRecette";
    }

    @GetMapping("/detail")
    public String detailPage(@RequestParam("uuid") UUID uuid, Model model) {
        try {
            RecipeDTO recette = recipeService.getRecipeByUUID(uuid);
            model.addAttribute("recette", recette);
            model.addAttribute("categorie", recette.getCategoryName());
            return "detailRecette";
        } catch (EntityNotFoundException e) {
            return "redirect:/recette/list";
        }
    }

    @GetMapping("/add")
    public String addRecipe(Model model) {
        RecipeDTO recette = new RecipeDTO();
        model.addAttribute("recette", recette);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "addRecette";
    }

    @PostMapping("/add")
    public String submitRecipe(@Validated @ModelAttribute("recette") RecipeDTO recette,
                               BindingResult bindingResult,
                               Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "addRecette";
        }

        try {
            if (recette.getId() != null) {
                recipeService.updateRecipe(recette);
            } else {
                recipeService.addRecipe(recette);
            }
            return "redirect:/recette/list";
        } catch (DataIntegrityViolationException | EntityNotFoundException e) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "addRecette";
        }
    }

    @GetMapping("/update")
    public String updateRecipe(@RequestParam("uuid") UUID uuid, Model model) {
        try {
            RecipeDTO recette = recipeService.getRecipeByUUID(uuid);
            model.addAttribute("recette", recette);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "addRecette";
        } catch (EntityNotFoundException e) {
            return "redirect:/recette/list";
        }
    }

    @GetMapping("/delete")
    public String deleteRecipe(@RequestParam("uuid") UUID uuid) {
        try {
            recipeService.deleteRecipe(uuid);
        } catch (Exception e) {
        }
        return "redirect:/recette/list";
    }
}