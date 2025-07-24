package com.example.demo.exo6.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private UUID id;
    private String name;
    private String description;
    private Double price;

    @NotNull(message = "La quantité est obligatoire")
    @Positive(message = "La quantité doit être positive")
    private Integer quantity;

    @NotNull(message = "L'ID du meuble est obligatoire")
    private UUID furnitureId;
}