package com.example.demo.exo6.interfaces;

import com.example.demo.exo6.model.dto.CartItemDTO;

import java.util.List;
import java.util.UUID;

public interface ICartService {
    List<CartItemDTO> getAllCartItems();
    CartItemDTO addCartItem(CartItemDTO cartItemDTO) throws Exception;
    void removeCartItem(UUID id) throws Exception;
    void clearCart();
}
