package Exo3.Entity;

import lombok.*;
import javax.persistence.*;

@Entity
@DiscriminatorValue("HOUSING")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductHousing extends Product {

    @Column(name = "height")
    private Double height;

    @Column(name = "width")
    private Double width;

    public ProductHousing(String name, int price, Double height, Double width) {
        super(name, price);
        this.height = height;
        this.width = width;
    }
}

