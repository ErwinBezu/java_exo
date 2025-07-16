package Exo2.Entity;

import Exo2.Enum.Climat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "regions")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nom", nullable = false)
    private String nom;
    @Column(name = "surface", nullable = false)
    private double surface;
    @Enumerated(EnumType.STRING)
    @Column(name = "climat", nullable = false)
    private Climat climat;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "region_specie",
            joinColumns = @JoinColumn(name = "region_id"),
            inverseJoinColumns = @JoinColumn(name = "specie_id")
    )
    private List<Specie> specieList = new ArrayList<>();

    public Region(String nom, Double surface, Climat climat) {
        this.nom = nom;
        this.surface = surface;
        this.climat = climat;
        this.specieList = new ArrayList<>();
    }

    public void addSpecie(Specie specie) {
        if (this.specieList == null) {
            this.specieList = new ArrayList<>();
        }
        if (!this.specieList.contains(specie)) {
            this.specieList.add(specie);
        }

        if (specie.getRegionList() == null) {
            specie.setRegionList(new ArrayList<>());
        }
        if (!specie.getRegionList().contains(this)) {
            specie.getRegionList().add(this);
        }
    }
}
