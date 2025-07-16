package Exo3.Entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }
}