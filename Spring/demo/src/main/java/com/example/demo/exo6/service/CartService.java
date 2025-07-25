package com.example.demo.exo6.service;

import com.example.demo.exo6.configs.MessageService;
import com.example.demo.exo6.exceptions.CartItemNotFoundException;
import com.example.demo.exo6.exceptions.CartNotFoundException;
import com.example.demo.exo6.exceptions.FurnitureNotFoundException;
import com.example.demo.exo6.exceptions.OutOfStockException;
import com.example.demo.exo6.interfaces.ICartService;
import com.example.demo.exo6.mapper.CartItemMapper;
import com.example.demo.exo6.mapper.CartMapper;
import com.example.demo.exo6.model.dto.CartDTO;
import com.example.demo.exo6.model.dto.CartItemDTO;
import com.example.demo.exo6.model.entity.Cart;
import com.example.demo.exo6.model.entity.CartItem;
import com.example.demo.exo6.model.entity.Furniture;
import com.example.demo.exo6.repository.CartItemRepository;
import com.example.demo.exo6.repository.CartRepository;
import com.example.demo.exo6.repository.FurnitureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartService implements ICartService {
    private CartItemRepository cartItemRepository;
    private FurnitureRepository furnitureRepository;
    private CartRepository cartRepository;
    private MessageService messageService;

    public CartService(
            CartItemRepository cartItemRepository,
            FurnitureRepository furnitureRepository,
            CartRepository cartRepository,
            MessageService messageService) {
        this.cartItemRepository = cartItemRepository;
        this.furnitureRepository = furnitureRepository;
        this.cartRepository = cartRepository;
        this.messageService = messageService;
    }
    @Override
    public CartDTO createCart() {
        Cart savedCart = cartRepository.save(new Cart(null, null, 0.0));
        return CartMapper.cartToCartDTO(savedCart);
    }

    @Override
    public CartDTO getCartById(UUID cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(
                        messageService.getMessage("exception.cart.not.found", cartId)));

        cart.calculateTotal();
        return CartMapper.cartToCartDTO(cart);
    }

    @Override
    public List<CartItemDTO> getAllCartItems(UUID cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(
                        messageService.getMessage("exception.cart.not.found", cartId)));

        return CartItemMapper.cartItemsToCartItemDTOs(cart.getItems());
    }

    @Override
    public CartItemDTO addCartItem(UUID cartId, CartItemDTO cartItemDTO) {
        Cart cart = findCartById(cartId);
        Furniture furniture = findFurnitureById(cartItemDTO.getFurnitureId());

        validateStock(furniture, cartItemDTO.getQuantity());

        CartItem savedCartItem = cartItemRepository.save(
                CartItemMapper.cartItemDTOToCartItem(cartItemDTO, furniture, cart));

        updateFurnitureStock(furniture, -cartItemDTO.getQuantity());
        updateCartTotal(cart);

        return CartItemMapper.cartItemToCartItemDTO(savedCartItem);
    }

    @Override
    public void removeCartItem(UUID cartId, UUID cartItemId) {
        Cart cart = findCartById(cartId);
        CartItem cartItem = findCartItemById(cartItemId);

        validateCartItemBelongsToCart(cartItem, cartId);

        updateFurnitureStock(cartItem.getFurniture(), cartItem.getQuantity());
        cartItemRepository.deleteById(cartItemId);
        updateCartTotal(cart);
    }

    @Override
    public void clearCart(UUID cartId) {
        Cart cart = findCartById(cartId);

        if (cart.getItems() != null) {
            cart.getItems().forEach(item ->
                    updateFurnitureStock(item.getFurniture(), item.getQuantity()));

            cartItemRepository.deleteAll(cart.getItems());
        }

        cartRepository.save(new Cart(cart.getId(), null, 0.0));
    }

    private Cart findCartById(UUID cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(
                        messageService.getMessage("exception.cart.not.found", cartId)));
    }

    private Furniture findFurnitureById(UUID furnitureId) {
        return furnitureRepository.findById(furnitureId)
                .orElseThrow(() -> new FurnitureNotFoundException(
                        messageService.getMessage("exception.furniture.not.found", furnitureId)));
    }

    private CartItem findCartItemById(UUID cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotFoundException(
                        messageService.getMessage("exception.cart.item.not.found", cartItemId)));
    }

    private void validateStock(Furniture furniture, Integer quantity) {
        if (furniture.getStock() < quantity) {
            throw new OutOfStockException(messageService.getMessage("exception.out.of.stock",
                    furniture.getName(), furniture.getStock(), quantity));
        }
    }

    private void validateCartItemBelongsToCart(CartItem cartItem, UUID cartId) {
        if (!cartItem.getCart().getId().equals(cartId)) {
            throw new CartItemNotFoundException(
                    messageService.getMessage("exception.cart.item.not.in.cart"));
        }
    }

    private void updateFurnitureStock(Furniture furniture, Integer stockChange) {
        Furniture updatedFurniture = new Furniture(
                furniture.getId(),
                furniture.getName(),
                furniture.getDescription(),
                furniture.getPrice(),
                furniture.getStock() + stockChange,
                furniture.getCartItems()
        );
        furnitureRepository.save(updatedFurniture);
    }

    private void updateCartTotal(Cart cart) {
        cart.calculateTotal();
        cartRepository.save(cart);
    }
}
