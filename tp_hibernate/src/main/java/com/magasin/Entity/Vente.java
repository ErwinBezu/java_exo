package com.magasin.Entity;

import com.magasin.Enum.StatusSale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ventes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "date_vente", nullable = false)
    private LocalDateTime dateVente;

    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusSale status;

    @OneToMany(mappedBy = "vente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    @Fetch(FetchMode.SUBSELECT)
    private List<LigneVente> lignesVente = new ArrayList<>();

    public void ajouterLigneVente(LigneVente ligneVente) {
        lignesVente.add(ligneVente);
        ligneVente.setVente(this);
        calculerTotal();
    }

    public void calculerTotal() {
        this.total = lignesVente.stream()
                .map(LigneVente::getSousTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
