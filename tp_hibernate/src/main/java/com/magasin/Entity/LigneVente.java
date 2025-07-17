package com.magasin.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "lignes_vente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LigneVente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vente_id", nullable = false)
    private Vente vente;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name = "quantite", nullable = false)
    private int quantite;

    @Column(name = "prix_unitaire", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixUnitaire;

    @Column(name = "sous_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal sousTotal;

    public void calculerSousTotal() {
        if (prixUnitaire != null && quantite > 0) {
            this.sousTotal = prixUnitaire.multiply(BigDecimal.valueOf(quantite));
        }
    }
}