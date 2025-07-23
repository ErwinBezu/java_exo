package com.example.demo.exo5.model.dto;

import jakarta.validation.constraints.NotBlank;
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
public class CategoryDTO {
    private UUID id;

    @NotBlank(message = "Le nom de la catégorie ne peut pas être vide")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    private String name;

    @NotBlank(message = "La description ne peut pas être vide")
    @Size(min = 5, max = 500, message = "La description doit contenir entre 5 et 500 caractères")
    private String description;
}