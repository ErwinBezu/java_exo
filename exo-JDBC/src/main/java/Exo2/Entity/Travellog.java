package Exo2.Entity;

import Exo2.Enum.TravelMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "travellogs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Travellog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "observation_id")
    private Observation observation;

    @Column(name = "distance_km")
    private Double distanceKm;

    @Enumerated(EnumType.STRING)
    @Column(name = "travel_mode")
    private TravelMode mode;

    public Travellog(Double distanceKm, TravelMode mode) {
        this.distanceKm = distanceKm;
        this.mode = mode;
    }
}
