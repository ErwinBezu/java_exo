package com.example.demo.exo5.controller;

import com.example.demo.exo5.interfaces.ICategoryService;
import com.example.demo.exo5.interfaces.IRecipeService;
import com.example.demo.exo5.mapper.CategoryMapper;
import com.example.demo.exo5.mapper.RecipeMapper;
import com.example.demo.exo5.model.dto.CategoryDTO;
import com.example.demo.exo5.model.dto.RecipeDTO;
import com.example.demo.exo5.model.entity.Category;
import com.example.demo.exo5.model.entity.Recipe;
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
    private final RecipeMapper recipeMapper;
    private final CategoryMapper categoryMapper;

    public RecipeController(IRecipeService recipeService,
                            ICategoryService categoryService,
                            RecipeMapper recipeMapper,
                            CategoryMapper categoryMapper) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
        this.recipeMapper = recipeMapper;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping("/list")
    public String listPage(Model model) {
        List<Category> allCategories = categoryService.getAllCategories();
        List<Recipe> allRecipes = recipeService.getAllRecipes();

        Map<UUID, String> categories = new HashMap<>();
        for (Category category : allCategories) {
            categories.put(category.getId(), category.getName());
        }

        List<RecipeDTO> recipeDTOs = allRecipes.stream()
                .map(recipeMapper::toDTO)
                .toList();

        model.addAttribute("allRecette", recipeDTOs);
        model.addAttribute("allCategory", categories);
        model.addAttribute("isFilter", false);
        return "listRecette";
    }

    @GetMapping("/detail")
    public String detailPage(@RequestParam("uuid") UUID uuid, Model model) {
        try {
            Recipe recipe = recipeService.getRecipeByUUID(uuid);
            RecipeDTO recipeDTO = recipeMapper.toDTO(recipe);
            model.addAttribute("recette", recipeDTO);
            model.addAttribute("categorie", recipeDTO.getCategoryName());
            return "detailRecette";
        } catch (EntityNotFoundException e) {
            return "redirect:/recette/list";
        }
    }

    @GetMapping("/add")
    public String addRecipe(Model model) {
        RecipeDTO recette = new RecipeDTO();

        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(categoryMapper::toDTO)
                .toList();

        model.addAttribute("recette", recette);
        model.addAttribute("categories", categoryDTOs);
        return "addRecette";
    }

    @PostMapping("/add")
    public String submitRecipe(@Validated @ModelAttribute("recette") RecipeDTO recipeDTO,
                               BindingResult bindingResult,
                               Model model) {

        if (bindingResult.hasErrors()) {
            List<Category> categories = categoryService.getAllCategories();
            List<CategoryDTO> categoryDTOs = categories.stream()
                    .map(categoryMapper::toDTO)
                    .toList();
            model.addAttribute("categories", categoryDTOs);
            return "addRecette";
        }

        try {
            if (recipeDTO.getId() != null) {
                Recipe recipe = recipeMapper.toEntity(recipeDTO);
                recipeService.updateRecipe(recipe, recipeDTO.getCategoryId());
            } else {
                recipeService.addRecipe(
                        recipeDTO.getName(),
                        recipeDTO.getIngredients(),
                        recipeDTO.getInstructions(),
                        recipeDTO.getCategoryId()
                );
            }
            return "redirect:/recette/list";
        } catch (DataIntegrityViolationException | EntityNotFoundException e) {
            List<Category> categories = categoryService.getAllCategories();
            List<CategoryDTO> categoryDTOs = categories.stream()
                    .map(categoryMapper::toDTO)
                    .toList();
            model.addAttribute("categories", categoryDTOs);
            return "addRecette";
        }
    }

    @GetMapping("/update")
    public String updateRecipe(@RequestParam("uuid") UUID uuid, Model model) {
        try {
            Recipe recipe = recipeService.getRecipeByUUID(uuid);
            RecipeDTO recipeDTO = recipeMapper.toDTO(recipe);
            List<Category> categories = categoryService.getAllCategories();
            List<CategoryDTO> categoryDTOs = categories.stream()
                    .map(categoryMapper::toDTO)
                    .toList();

            model.addAttribute("recette", recipeDTO);
            model.addAttribute("categories", categoryDTOs);
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