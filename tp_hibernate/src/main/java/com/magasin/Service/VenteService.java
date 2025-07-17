package com.magasin.Service;

import com.magasin.DAO.VenteDAO;
import com.magasin.DAO.LigneVenteDAO;
import com.magasin.Entity.Vente;
import com.magasin.Entity.LigneVente;
import com.magasin.Entity.Client;
import com.magasin.Entity.Article;
import com.magasin.Enum.StatusSale;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class VenteService {
    private final VenteDAO venteDAO;
    private final LigneVenteDAO ligneVenteDAO;
    private final ArticleService articleService;
    private final ClientService clientService;

    public VenteService() {
        this.venteDAO = new VenteDAO();
        this.ligneVenteDAO = new LigneVenteDAO();
        this.articleService = new ArticleService();
        this.clientService = new ClientService();
    }

    public Vente creerVente(Long clientId) {
        if (clientId == null) {
            throw new IllegalArgumentException("L'ID du client ne peut pas être null");
        }

        Client client = clientService.consulterClient(clientId);
        if (client == null) {
            throw new IllegalArgumentException("Client introuvable avec l'ID: " + clientId);
        }

        Vente vente = new Vente();
        vente.setClient(client);
        vente.setDateVente(LocalDateTime.now());
        vente.setStatus(StatusSale.EN_COURS);
        vente.setTotal(BigDecimal.ZERO);

        return venteDAO.saveOrUpdate(vente);
    }

    public Vente ajouterArticleAVente(Long venteId, Long articleId, int quantite) {
        if (venteId == null || articleId == null) {
            throw new IllegalArgumentException("L'ID de la vente et l'ID de l'article ne peuvent pas être null");
        }
        if (quantite <= 0) {
            throw new IllegalArgumentException("La quantité doit être positive");
        }

        Vente vente = venteDAO.get(venteId);
        if (vente == null) {
            throw new IllegalArgumentException("Vente introuvable avec l'ID: " + venteId);
        }

        if (vente.getStatus() != StatusSale.EN_COURS) {
            throw new IllegalStateException("Impossible de modifier une vente qui n'est pas en cours");
        }

        Article article = articleService.consulterArticle(articleId);
        if (article == null) {
            throw new IllegalArgumentException("Article introuvable avec l'ID: " + articleId);
        }

        if (!articleService.verifierDisponibilite(articleId, quantite)) {
            throw new IllegalStateException("Stock insuffisant pour l'article: " + article.getNom());
        }

        LigneVente ligneVente = new LigneVente();
        ligneVente.setVente(vente);
        ligneVente.setArticle(article);
        ligneVente.setQuantite(quantite);
        ligneVente.setPrixUnitaire(article.getPrice());
        ligneVente.calculerSousTotal();

        ligneVenteDAO.saveOrUpdate(ligneVente);

        vente.ajouterLigneVente(ligneVente);

        return venteDAO.saveOrUpdate(vente);
    }

    public Vente finaliserVente(Long venteId) {
        if (venteId == null) {
            throw new IllegalArgumentException("L'ID de la vente ne peut pas être null");
        }

        Vente vente = venteDAO.get(venteId);
        if (vente == null) {
            throw new IllegalArgumentException("Vente introuvable avec l'ID: " + venteId);
        }

        if (vente.getStatus() != StatusSale.EN_COURS) {
            throw new IllegalStateException("Seules les ventes en cours peuvent être finalisées");
        }

        if (vente.getLignesVente().isEmpty()) {
            throw new IllegalStateException("Impossible de finaliser une vente sans articles");
        }

        for (LigneVente ligneVente : vente.getLignesVente()) {
            articleService.reduireStock(ligneVente.getArticle().getId(), ligneVente.getQuantite());
        }

        vente.setStatus(StatusSale.FINALISEE);
        return venteDAO.saveOrUpdate(vente);
    }

    public Vente annulerVente(Long venteId) {
        if (venteId == null) {
            throw new IllegalArgumentException("L'ID de la vente ne peut pas être null");
        }

        Vente vente = venteDAO.get(venteId);
        if (vente == null) {
            throw new IllegalArgumentException("Vente introuvable avec l'ID: " + venteId);
        }

        if (vente.getStatus() == StatusSale.ANNULEE) {
            throw new IllegalStateException("Cette vente est déjà annulée");
        }

        vente.setStatus(StatusSale.ANNULEE);
        return venteDAO.saveOrUpdate(vente);
    }

    public Vente consulterVente(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }
        return venteDAO.get(id);
    }

    public List<Vente> listerToutesLesVentes() {
        return venteDAO.findAll();
    }

    public List<Vente> listerVentesParClient(Long clientId) {
        if (clientId == null) {
            throw new IllegalArgumentException("L'ID du client ne peut pas être null");
        }

        Client client = clientService.consulterClient(clientId);
        if (client == null) {
            throw new IllegalArgumentException("Client introuvable avec l'ID: " + clientId);
        }

        return venteDAO.findByClient(client);
    }

    public List<Vente> listerVentesParStatut(StatusSale status) {
        if (status == null) {
            throw new IllegalArgumentException("Le statut ne peut pas être null");
        }
        return venteDAO.findByStatus(status);
    }

    public List<Vente> listerVentesFinalisees() {
        return venteDAO.findVentesFinalisees();
    }

    public List<Vente> listerVentesEnCours() {
        return venteDAO.findVentesEnCours();
    }

    public List<Vente> listerVentesParPeriode(LocalDateTime dateDebut, LocalDateTime dateFin) {
        if (dateDebut == null || dateFin == null) {
            throw new IllegalArgumentException("Les dates de début et de fin ne peuvent pas être null");
        }
        if (dateDebut.isAfter(dateFin)) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin");
        }

        return venteDAO.findByDateRange(dateDebut, dateFin);
    }

    public Vente supprimerLigneVente(Long venteId, Long ligneVenteId) {
        if (venteId == null || ligneVenteId == null) {
            throw new IllegalArgumentException("Les IDs ne peuvent pas être null");
        }

        Vente vente = venteDAO.get(venteId);
        if (vente == null) {
            throw new IllegalArgumentException("Vente introuvable avec l'ID: " + venteId);
        }

        if (vente.getStatus() != StatusSale.EN_COURS) {
            throw new IllegalStateException("Impossible de modifier une vente qui n'est pas en cours");
        }

        LigneVente ligneVente = ligneVenteDAO.get(ligneVenteId);
        if (ligneVente == null) {
            throw new IllegalArgumentException("Ligne de vente introuvable avec l'ID: " + ligneVenteId);
        }

        if (!ligneVente.getVente().getId().equals(venteId)) {
            throw new IllegalArgumentException("Cette ligne de vente n'appartient pas à cette vente");
        }

        ligneVenteDAO.delete(ligneVenteId);

        vente = venteDAO.get(venteId);
        vente.calculerTotal();

        return venteDAO.saveOrUpdate(vente);
    }
}
