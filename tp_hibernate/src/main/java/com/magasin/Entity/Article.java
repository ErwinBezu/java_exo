package com.magasin.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="articles")
@Inheritance(strategy= InheritanceType.JOINED)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "quantity_stock", nullable = false)
    private int quantiteStock;

    @Column(name = "restock_date")
    private LocalDate restockDate;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @ToString.Exclude
    private List<LigneVente> lignesVente = new ArrayList<>();

    public void restock (int quantity){
        this.quantiteStock += quantity;
        this.restockDate = LocalDate.now();
    }

    public boolean isAvailable(int quantiteRequise) {
        return this.quantiteStock >= quantiteRequise;
    }

    public void reduireStock(int quantite) {
        if (quantite > this.quantiteStock) {
            throw new IllegalArgumentException("Stock insuffisant pour l'article: " + this.nom);
        }
        if (quantite <= 0) {
            throw new IllegalArgumentException("La quantité doit être positive");
        }
        this.quantiteStock -= quantite;
    }

    public int getTotalVendu() {
        return lignesVente.stream()
                .mapToInt(LigneVente::getQuantite)
                .sum();
    }
}
