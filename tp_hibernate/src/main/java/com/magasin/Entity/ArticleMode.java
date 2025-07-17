package com.magasin.Entity;

import com.magasin.Enum.CategorieMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "articles_mode")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ArticleMode extends Article {
    @Enumerated(EnumType.STRING)
    @Column(name = "categorie", nullable = false)
    private CategorieMode categorie;

    @Column(name = "taille", nullable = false)
    private String taille;

}
