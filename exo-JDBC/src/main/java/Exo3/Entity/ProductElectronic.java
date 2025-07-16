package Exo3.Entity;

import lombok.*;
import javax.persistence.*;

@Entity
@DiscriminatorValue("ELECTRONIC")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductElectronic extends Product {

    @Column(name = "battery_duration")
    private int batteryDuration;

    public ProductElectronic(String name, int price, Integer batteryDuration) {
        super(name, price);
        this.batteryDuration = batteryDuration;
    }
}
