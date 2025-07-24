package com.example.demo.exo6.service;

import com.example.demo.exo6.interfaces.ICartService;
import com.example.demo.exo6.mapper.CartItemMapper;
import com.example.demo.exo6.model.dto.CartItemDTO;
import com.example.demo.exo6.model.entity.CartItem;
import com.example.demo.exo6.model.entity.Furniture;
import com.example.demo.exo6.repository.CartItemRepository;
import com.example.demo.exo6.repository.FurnitureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartService implements ICartService {
    private CartItemRepository cartItemRepository;
    private FurnitureRepository furnitureRepository;

    public CartService(CartItemRepository cartItemRepository, FurnitureRepository furnitureRepository) {
        this.cartItemRepository = cartItemRepository;
        this.furnitureRepository = furnitureRepository;
    }
    @Override
    public List<CartItemDTO> getAllCartItems() {
        List<CartItem> cartItems = cartItemRepository.findAll();
        return CartItemMapper.cartItemsToCartItemDTOs(cartItems);
    }

    @Override
    public CartItemDTO addCartItem(CartItemDTO cartItemDTO) throws Exception {
        Furniture furniture = furnitureRepository.findById(cartItemDTO.getFurnitureId()).orElse(null);

        if (furniture == null)
            throw new Exception("Le meuble d'ID : " + cartItemDTO.getFurnitureId() + " n'existe pas.");

        CartItem cartItemSaved = cartItemRepository.save(CartItemMapper.cartItemDTOToCartItem(cartItemDTO, furniture));
        return CartItemMapper.cartItemToCartItemDTO(cartItemSaved);
    }

    @Override
    public void removeCartItem(UUID id) throws Exception {
        if (!cartItemRepository.existsById(id))
            throw new Exception("L'article du panier n'existe pas.");

        cartItemRepository.deleteById(id);
    }

    @Override
    public void clearCart() {
        cartItemRepository.deleteAll();
    }
}
