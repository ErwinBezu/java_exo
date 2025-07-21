package com.example.demo.exo2.service;

import com.example.demo.exo2.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final List<Product> products = new ArrayList<>();

    public ProductService() {
        initProducts();
    }

    private void initProducts(){
        products.add(Product.builder()
                .id(UUID.randomUUID())
                .name("Ordinateur portable")
                .category("Informatique")
                .price(new BigDecimal("799.99"))
                .build());

        products.add(Product.builder()
                .id(UUID.randomUUID())
                .name("Smartphone")
                .category("Informatique")
                .price(new BigDecimal("599.50"))
                .build());

        products.add(Product.builder()
                .id(UUID.randomUUID())
                .name("Chaise de bureau")
                .category("Mobilier")
                .price(new BigDecimal("149.99"))
                .build());

        products.add(Product.builder()
                .id(UUID.randomUUID())
                .name("Livre de programmation")
                .category("Livres")
                .price(new BigDecimal("39.99"))
                .build());

        products.add(Product.builder()
                .id(UUID.randomUUID())
                .name("Casque audio")
                .category("Informatique")
                .price(new BigDecimal("89.99"))
                .build());
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public Product getProductById(UUID id) {
       return products.stream()
               .filter(p -> p.getId().equals(id))
               .findFirst()
               .orElse(null);
    }

    public List<Product> productWithFilter(String category, BigDecimal maxPrice) {
        return products.stream()
                .filter(p -> (category == null || p.getCategory().equalsIgnoreCase(category)) &&
                        (maxPrice == null || p.getPrice().compareTo(maxPrice) <= 0))
                .toList();
    }
}
