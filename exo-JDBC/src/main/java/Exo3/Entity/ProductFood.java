package Exo3.Entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("FOOD")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFood extends Product {

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    public ProductFood(String name, int price, LocalDate expiryDate) {
        super(name, price);
        this.expiryDate = expiryDate;
    }
}
