package com.example.demo.exo6.mapper;

import com.example.demo.exo6.model.dto.CartItemDTO;
import com.example.demo.exo6.model.entity.Cart;
import com.example.demo.exo6.model.entity.CartItem;
import com.example.demo.exo6.model.entity.Furniture;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartItemMapper {

    public static CartItemDTO cartItemToCartItemDTO(CartItem cartItem){
        if (cartItem == null || cartItem.getFurniture() == null) return null;

        return new CartItemDTO(
                cartItem.getId(),
                cartItem.getFurniture().getName(),
                cartItem.getFurniture().getDescription(),
                cartItem.getFurniture().getPrice(),
                cartItem.getQuantity(),
                cartItem.getFurniture().getId()
        );
    }

    public static List<CartItemDTO> cartItemsToCartItemDTOs(List<CartItem> cartItems){
        if (cartItems == null) return null;

        return cartItems.stream()
                .map(CartItemMapper::cartItemToCartItemDTO)
                .toList();
    }

    public static CartItem cartItemDTOToCartItem(CartItemDTO cartItemDTO, Furniture furniture) {
        if (cartItemDTO == null || furniture == null) return null;

        return new CartItem(
                cartItemDTO.getId(),
                furniture,
                null,
                cartItemDTO.getQuantity()
        );
    }

    public static CartItem cartItemDTOToCartItem(CartItemDTO cartItemDTO, Furniture furniture, Cart cart) {
        if (cartItemDTO == null || furniture == null) return null;

        return new CartItem(
                cartItemDTO.getId(),
                furniture,
                cart,
                cartItemDTO.getQuantity()
        );
    }
}
