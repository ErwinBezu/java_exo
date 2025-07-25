package com.example.demo.exo6.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> items;

    private double total;

    public void calculateTotal() {
        if (items != null) {
            this.total = items.stream()
                    .mapToDouble(item -> item.getFurniture().getPrice() * item.getQuantity())
                    .sum();
        } else {
            this.total = 0.0;
        }
    }
}
