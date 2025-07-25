package com.example.demo.exo6.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private UUID id;

    private List<CartItemDTO> items;

    @NotNull(message = "{validation.cart.total.not.null}")
    private Double total;
}