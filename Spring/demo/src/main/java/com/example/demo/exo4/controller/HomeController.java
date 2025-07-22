package com.example.demo.exo4.controller;

import com.example.demo.exo4.service.RecipeService;
import com.example.demo.exo4.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final RecipeService recipeService;
    private final CategoryService categoryService;

    public HomeController(RecipeService recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("recipeCount", recipeService.count());
        model.addAttribute("categoryCount", categoryService.count());
        return "index";
    }
}
