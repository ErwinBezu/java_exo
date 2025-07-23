package com.example.demo.exo5.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {
    private UUID id;

    @NotBlank(message = "Le nom de la recette ne peut pas être vide")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String name;

    @NotBlank(message = "Les ingrédients ne peuvent pas être vides")
    @Size(min = 5, message = "Les ingrédients doivent contenir au moins 5 caractères")
    private String ingredients;

    @NotBlank(message = "Les instructions ne peuvent pas être vides")
    @Size(min = 10, message = "Les instructions doivent contenir au moins 10 caractères")
    private String instructions;

    @NotNull(message = "Une recette doit appartenir à une catégorie")
    private UUID categoryId;

    private String categoryName;
}