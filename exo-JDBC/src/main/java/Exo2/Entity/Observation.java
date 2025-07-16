package Exo2.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "observations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Observation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "specie_id")
    @ToString.Exclude
    private Specie specie;

    @Column(name = "observer_name", nullable = false, length = 100)
    private String observerName;

    @Column(name= "location", nullable = false, length = 200)
    private String location;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "observation_date", nullable = false)
    private LocalDate observationDate;

    @Column(name = "comment", length = 500)
    private String comment;

    @OneToOne(mappedBy = "observation", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Travellog travellog;

    public Observation(Specie specie, String observerName, String location,
                       Double latitude, Double longitude, LocalDate observationDate, String comment) {
        this.specie = specie;
        this.observerName = observerName;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.observationDate = observationDate;
        this.comment = comment;
    }
}
