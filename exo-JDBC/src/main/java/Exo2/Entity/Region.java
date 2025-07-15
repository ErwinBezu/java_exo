package Exo2.Entity;

import Exo2.Enum.Climat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private String nom;
    private double surface;
    @Enumerated(EnumType.STRING)
    @Column(name = "climat", nullable = false)
    private Climat climat;

    public Region(String nom, Double surface, Climat climat) {
        this.nom = nom;
        this.surface = surface;
        this.climat = climat;
    }
}
