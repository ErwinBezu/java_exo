package com.example.demo.exo4.controller;

import com.example.demo.exo4.model.Category;
import com.example.demo.exo4.model.Recipe;
import com.example.demo.exo4.service.CategoryService;
import com.example.demo.exo4.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
public class RecipeController {
    private final RecipeService recipeService;
    private final CategoryService categoryService;

    public RecipeController(RecipeService recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @GetMapping("recipes")
    public String listRecipes(Model model) {
        model.addAttribute("recipes", recipeService.findAllRecipes());
        model.addAttribute("categoryService", categoryService);
        return "recipes/list";
    }

    @GetMapping("/recipes/new")
    public String addCategory(Model model) {
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("categories", categoryService.findAllCategories());
        return "recipes/form";
    }

    @GetMapping("/recipes/edit/{id}")
    public String showEditForm(@PathVariable UUID id, Model model) {
        Recipe recipe = recipeService.getRecipeById(id);
        if (recipe != null) {
            model.addAttribute("recipe", recipe);
            model.addAttribute("categories", categoryService.findAllCategories());
            return "recipes/form";
        }
        return "redirect:/recipes";
    }

    @GetMapping("/recipes/view/{id}")
    public String viewRecipe(@PathVariable UUID id, Model model) {
        Recipe recipe = recipeService.getRecipeById(id);
        if (recipe != null) {
            model.addAttribute("recipe", recipe);

            Category category = categoryService.getCategoryById(recipe.getCategoryId());
            String categoryName = category != null ? category.getName() : "Catégorie inconnue";
            model.addAttribute("categoryName", categoryName);

            return "recipes/view";
        }
        return "redirect:/recipes";
    }

    @GetMapping("/recipes/search")
    public String searchRecipes(@RequestParam String searchedName, Model model) {
        List<Recipe> allRecipes = recipeService.findAllRecipes();
        List<Recipe> foundRecipes = allRecipes.stream()
                .filter(recipe -> recipe.getName().toLowerCase().contains(searchedName.toLowerCase()))
                .toList();
        model.addAttribute("recipes", foundRecipes);
        model.addAttribute("categoryService", categoryService);
        model.addAttribute("searchedName", searchedName);
        return "recipes/searchList";
    }

    @PostMapping("/recipes")
    public String saveRecipe(@Valid Recipe recipe, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAllCategories());
            return "recipes/form";
        }

        if (recipe.getId() != null) {
            recipeService.updateRecipe(recipe.getId(), recipe);
        } else {
            recipeService.createRecipe(recipe);
        }

        return "redirect:/recipes";
    }

    @GetMapping("/recipes/delete/{id}")
    public String deleteRecipe(@PathVariable UUID id) {
        recipeService.deleteRecipeById(id);
        return "redirect:/recipes";
    }
}
