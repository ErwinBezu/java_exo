package com.example.demo.exo6.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FurnitureDTO {
    private UUID id;

    @NotBlank(message = "Le nom ne peut pas être vide")
    private String name;

    private String description;

    @NotNull(message = "Le prix est obligatoire")
    @Positive(message = "Le prix doit être positif")
    private Double price;

    @NotNull(message = "Le stock est obligatoire")
    @PositiveOrZero(message = "Le stock doit être positif ou zéro")
    private Integer stock;
}
