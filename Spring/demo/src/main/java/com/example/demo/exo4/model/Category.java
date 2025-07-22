package com.example.demo.exo4.model;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    private UUID id;

    @NotNull(message="Le nom de la catégorie ne peut pas être vide")
    @NotBlank
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    private String name;

    @NotNull(message = "La description ne peut pas être vide")
    @NotBlank
    @Size(min = 5, max = 200, message = "La description doit contenir entre 5 et 200 caractères")
    private String description;
}
