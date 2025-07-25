package com.example.demo.exo6.mapper;

import com.example.demo.exo6.model.dto.CartDTO;
import com.example.demo.exo6.model.entity.Cart;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartMapper {

    public static CartDTO cartToCartDTO(Cart cart){
        if (cart == null) return null;

        return new CartDTO(
                cart.getId(),
                cart.getItems() != null ? CartItemMapper.cartItemsToCartItemDTOs(cart.getItems()) : null,
                cart.getTotal()
        );
    }

    public static List<CartDTO> cartsToCartDTOs(List<Cart> carts){
        if (carts == null) return null;

        return carts.stream()
                .map(CartMapper::cartToCartDTO)
                .toList();
    }

    public static Cart cartDTOToCart(CartDTO cartDTO) {
        if (cartDTO == null) return null;

        return new Cart(
                cartDTO.getId(),
                null,
                cartDTO.getTotal()
        );
    }
}
