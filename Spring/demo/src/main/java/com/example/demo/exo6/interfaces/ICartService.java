package com.example.demo.exo6.interfaces;

import com.example.demo.exo6.model.dto.CartDTO;
import com.example.demo.exo6.model.dto.CartItemDTO;

import java.util.List;
import java.util.UUID;

public interface ICartService {
    CartDTO createCart();
    CartDTO getCartById(UUID cartId);
    List<CartItemDTO> getAllCartItems(UUID cartId);
    CartItemDTO addCartItem(UUID cartId, CartItemDTO cartItemDTO);
    void removeCartItem(UUID cartId, UUID cartItemId);
    void clearCart(UUID cartId);
}
