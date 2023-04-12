package com.example.demo.services;

import com.example.demo.models.Cart;
import com.example.demo.models.CartItem;
import com.example.demo.models.Product;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
@Autowired
    private final CartRepository cartRepository;
    @Autowired
    private final CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
    public CartItem addToCart(Long cartId, Long productId) throws Exception {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new Exception("Cart not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new Exception("Product not found"));

        CartItem existingCartItem = cartItemRepository.findByCartAndProduct(cart, product);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
            return cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(1)
                    .build();

            return cartItemRepository.save(cartItem);
        }
    }
    public void updateCartItem(Long cartItemId, int quantity) throws Exception {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new Exception("Cart item not found"));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }
    @Transactional
    public void removeCartItem(Long userId, Long cartItemId) throws Exception {
        Cart cart = cartRepository.findByUserId(userId);
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new Exception("Cart item not found"));

        cart.getCartItems().remove(cartItem);
        cartRepository.save(cart);
    }
    public void clearCart(Long cartId) throws Exception {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new Exception("Cart not found"));
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

}