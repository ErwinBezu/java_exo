package com.magasin.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "articles_food")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ArticleFood extends Article {
    @Column(name = "date_peremption", nullable = false)
    private LocalDate datePeremption;
}
