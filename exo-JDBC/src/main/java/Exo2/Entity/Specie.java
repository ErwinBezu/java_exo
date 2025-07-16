package Exo2.Entity;

import Exo2.Enum.Category;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "species")
public class Specie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "common_name", nullable = false)
    private String commonName;
    @Column(name = "scientific_name", nullable = false)
    private String scientificName;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @ManyToMany(mappedBy = "specieList")
    @ToString.Exclude
    private List<Region> regionList = new ArrayList<>();

    @OneToMany(mappedBy = "specie", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Observation> observations = new ArrayList<>();

    public Specie(String commonName, String scientificName, Category category) {
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.category = category;
        this.regionList = new ArrayList<>();
        this.observations = new ArrayList<>();
    }

    public void addRegion(Region region) {
        if (this.regionList == null) {
            this.regionList = new ArrayList<>();
        }

        if (!this.regionList.contains(region)) {
            this.regionList.add(region);
        }

        if (region.getSpecieList() == null) {
            region.setSpecieList(new ArrayList<>());
        }
        if (!region.getSpecieList().contains(this)) {
            region.getSpecieList().add(this);
        }
    }
}
