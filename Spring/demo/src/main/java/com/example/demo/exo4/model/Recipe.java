package com.example.demo.exo4.model;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {
    private UUID id;

    @NotBlank(message = "Le nom de la recette ne peut pas être vide")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String name;

    @NotBlank(message = "Les ingrédients ne peuvent pas être vides")
    @Size(min = 5, message = "Les ingrédients doivent contenir au moins 5 caractères")
    private String ingredientList;

    @NotBlank(message = "Les instructions ne peuvent pas être vides")
    @Size(min = 10, message = "Les instructions doivent contenir au moins 10 caractères")
    private String instructions;

    @NotNull(message = "Une catégorie doit être sélectionnée")
    private UUID categoryId;
}
