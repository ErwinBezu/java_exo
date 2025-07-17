package com.magasin.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "articles_electronic")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ArticleElectronic extends Article {
    @Column(name = "battery_life", nullable = false)
    private int batteryLifeInHour;
}
