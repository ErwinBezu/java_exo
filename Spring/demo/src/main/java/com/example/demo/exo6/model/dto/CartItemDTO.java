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

    @NotNull(message = "{validation.cart.item.quantity.not.null}")
    @Positive(message = "{validation.cart.item.quantity.positive}")
    private Integer quantity;

    @NotNull(message = "{validation.cart.item.furniture.id.not.null}")
    private UUID furnitureId;
}