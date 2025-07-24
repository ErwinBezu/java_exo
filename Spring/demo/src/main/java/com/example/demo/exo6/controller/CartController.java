package com.example.demo.exo6.controller;

import com.example.demo.exo6.interfaces.ICartService;
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
    private ICartService cartService;

    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemDTO>> getAllCartItems() {
        return new ResponseEntity<>(cartService.getAllCartItems(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CartItemDTO> addCartItem(@Validated @RequestBody CartItemDTO cartItemDTO) {
        CartItemDTO addedItem;
        try {
            addedItem = cartService.addCartItem(cartItemDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(addedItem, HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity removeCartItem(@PathVariable UUID id) {
        try {
            cartService.removeCartItem(id);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/clear")
    public ResponseEntity clearCart() {
        cartService.clearCart();
        return new ResponseEntity(HttpStatus.OK);
    }
}