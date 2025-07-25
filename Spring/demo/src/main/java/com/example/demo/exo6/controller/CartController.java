package com.example.demo.exo6.controller;

import com.example.demo.exo6.interfaces.ICartService;
import com.example.demo.exo6.model.dto.CartDTO;
import com.example.demo.exo6.model.dto.CartItemDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final ICartService cartService;

    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartDTO> createCart() {
        CartDTO createdCart = cartService.createCart();
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<List<CartItemDTO>> getAllCartItems(@PathVariable UUID cartId) {
        List<CartItemDTO> cartItems = cartService.getAllCartItems(cartId);
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @PostMapping("/{cartId}/add")
    public ResponseEntity<CartItemDTO> addCartItem(@PathVariable UUID cartId,
                                                   @Validated @RequestBody CartItemDTO cartItemDTO) {
        CartItemDTO addedItem = cartService.addCartItem(cartId, cartItemDTO);
        return new ResponseEntity<>(addedItem, HttpStatus.CREATED);
    }

    @DeleteMapping("/{cartId}/remove/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable UUID cartId,
                                               @PathVariable UUID cartItemId) {
        cartService.removeCartItem(cartId, cartItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable UUID cartId) {
        cartService.clearCart(cartId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}