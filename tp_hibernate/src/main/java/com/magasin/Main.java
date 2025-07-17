package com.magasin;

import com.magasin.Entity.*;
import com.magasin.Enum.CategorieMode;
import com.magasin.Enum.StatusSale;
import com.magasin.Service.ArticleService;
import com.magasin.Service.ClientService;
import com.magasin.Service.VenteService;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        ArticleService articleService = new ArticleService();
        ClientService clientService = new ClientService();
        VenteService venteService = new VenteService();

        System.out.println("=== INITIALISATION DES DONNÉES ===");

        System.out.println("\n--- Création des clients ---");

        Client client1 = new Client();
        client1.setNom("Jean Dupont");
        client1.setEmail("jean.dupont@email.com");
        client1 = clientService.creerClient(client1);
        System.out.println("Client créé: " + client1.getNom() + " (ID: " + client1.getId() + ")");

        Client client2 = new Client();
        client2.setNom("Marie Martin");
        client2.setEmail("marie.martin@email.com");
        client2 = clientService.creerClient(client2);
        System.out.println("Client créé: " + client2.getNom() + " (ID: " + client2.getId() + ")");

        Client client3 = new Client();
        client3.setNom("Pierre Durand");
        client3.setEmail("pierre.durand@email.com");
        client3 = clientService.creerClient(client3);
        System.out.println("Client créé: " + client3.getNom() + " (ID: " + client3.getId() + ")");

        System.out.println("\n--- Création des articles électroniques ---");

        ArticleElectronic smartphone = ArticleElectronic.builder()
                .nom("iPhone 15")
                .description("Smartphone Apple dernière génération")
                .price(new BigDecimal("999.99"))
                .quantiteStock(25)
                .restockDate(LocalDate.now().minusDays(5))
                .batteryLifeInHour(24)
                .build();
        smartphone = (ArticleElectronic) articleService.ajouterArticle(smartphone);
        System.out.println("Article électronique créé: " + smartphone.getNom() + " (ID: " + smartphone.getId() + ")");

        ArticleElectronic laptop = ArticleElectronic.builder()
                .nom("MacBook Pro")
                .description("Ordinateur portable professionnel")
                .price(new BigDecimal("2499.99"))
                .quantiteStock(15)
                .restockDate(LocalDate.now().minusDays(10))
                .batteryLifeInHour(18)
                .build();
        laptop = (ArticleElectronic) articleService.ajouterArticle(laptop);
        System.out.println("Article électronique créé: " + laptop.getNom() + " (ID: " + laptop.getId() + ")");

        ArticleElectronic casque = ArticleElectronic.builder()
                .nom("AirPods Pro")
                .description("Écouteurs sans fil avec réduction de bruit")
                .price(new BigDecimal("249.99"))
                .quantiteStock(50)
                .restockDate(LocalDate.now().minusDays(3))
                .batteryLifeInHour(6)
                .build();
        casque = (ArticleElectronic) articleService.ajouterArticle(casque);
        System.out.println("Article électronique créé: " + casque.getNom() + " (ID: " + casque.getId() + ")");

        System.out.println("\n--- Création des articles alimentaires ---");

        ArticleFood chocolat = ArticleFood.builder()
                .nom("Chocolat Lindt")
                .description("Chocolat noir 70% cacao")
                .price(new BigDecimal("4.99"))
                .quantiteStock(100)
                .restockDate(LocalDate.now().minusDays(2))
                .datePeremption(LocalDate.now().plusMonths(6))
                .build();
        chocolat = (ArticleFood) articleService.ajouterArticle(chocolat);
        System.out.println("Article alimentaire créé: " + chocolat.getNom() + " (ID: " + chocolat.getId() + ")");

        ArticleFood cafe = ArticleFood.builder()
                .nom("Café Arabica")
                .description("Café en grains premium")
                .price(new BigDecimal("12.50"))
                .quantiteStock(75)
                .restockDate(LocalDate.now().minusDays(7))
                .datePeremption(LocalDate.now().plusYears(1))
                .build();
        cafe = (ArticleFood) articleService.ajouterArticle(cafe);
        System.out.println("Article alimentaire créé: " + cafe.getNom() + " (ID: " + cafe.getId() + ")");

        ArticleFood biscuits = ArticleFood.builder()
                .nom("Biscuits bio")
                .description("Biscuits biologiques au chocolat")
                .price(new BigDecimal("3.25"))
                .quantiteStock(200)
                .restockDate(LocalDate.now().minusDays(1))
                .datePeremption(LocalDate.now().plusMonths(3))
                .build();
        biscuits = (ArticleFood) articleService.ajouterArticle(biscuits);
        System.out.println("Article alimentaire créé: " + biscuits.getNom() + " (ID: " + biscuits.getId() + ")");

        System.out.println("\n--- Création des articles de mode ---");

        ArticleMode tshirtHomme = ArticleMode.builder()
                .nom("T-shirt Nike")
                .description("T-shirt sport homme")
                .price(new BigDecimal("29.99"))
                .quantiteStock(80)
                .restockDate(LocalDate.now().minusDays(4))
                .categorie(CategorieMode.HOMME)
                .taille("M")
                .build();
        tshirtHomme = (ArticleMode) articleService.ajouterArticle(tshirtHomme);
        System.out.println("Article mode créé: " + tshirtHomme.getNom() + " (ID: " + tshirtHomme.getId() + ")");

        ArticleMode robeFemme = ArticleMode.builder()
                .nom("Robe d'été")
                .description("Robe légère pour l'été")
                .price(new BigDecimal("59.99"))
                .quantiteStock(40)
                .restockDate(LocalDate.now().minusDays(6))
                .categorie(CategorieMode.FEMME)
                .taille("S")
                .build();
        robeFemme = (ArticleMode) articleService.ajouterArticle(robeFemme);
        System.out.println("Article mode créé: " + robeFemme.getNom() + " (ID: " + robeFemme.getId() + ")");

        ArticleMode jeanEnfant = ArticleMode.builder()
                .nom("Jean enfant")
                .description("Jean confortable pour enfant")
                .price(new BigDecimal("24.99"))
                .quantiteStock(60)
                .restockDate(LocalDate.now().minusDays(8))
                .categorie(CategorieMode.ENFANT)
                .taille("10 ans")
                .build();
        jeanEnfant = (ArticleMode) articleService.ajouterArticle(jeanEnfant);
        System.out.println("Article mode créé: " + jeanEnfant.getNom() + " (ID: " + jeanEnfant.getId() + ")");

        System.out.println("\n--- Création des ventes ---");

        Vente vente1 = venteService.creerVente(client1.getId());
        System.out.println("Vente créée pour " + client1.getNom() + " (ID: " + vente1.getId() + ")");

        venteService.ajouterArticleAVente(vente1.getId(), smartphone.getId(), 1);
        venteService.ajouterArticleAVente(vente1.getId(), casque.getId(), 1);
        venteService.ajouterArticleAVente(vente1.getId(), chocolat.getId(), 3);

        vente1 = venteService.finaliserVente(vente1.getId());
        System.out.println("Vente finalisée - Total: " + vente1.getTotal() + "€");

        Vente vente2 = venteService.creerVente(client2.getId());
        System.out.println("Vente créée pour " + client2.getNom() + " (ID: " + vente2.getId() + ")");

        venteService.ajouterArticleAVente(vente2.getId(), robeFemme.getId(), 2);
        venteService.ajouterArticleAVente(vente2.getId(), cafe.getId(), 1);
        venteService.ajouterArticleAVente(vente2.getId(), biscuits.getId(), 5);

        vente2 = venteService.finaliserVente(vente2.getId());
        System.out.println("Vente finalisée - Total: " + vente2.getTotal() + "€");

        Vente vente3 = venteService.creerVente(client3.getId());
        System.out.println("Vente créée pour " + client3.getNom() + " (ID: " + vente3.getId() + ")");

        venteService.ajouterArticleAVente(vente3.getId(), laptop.getId(), 1);
        venteService.ajouterArticleAVente(vente3.getId(), tshirtHomme.getId(), 2);
        venteService.ajouterArticleAVente(vente3.getId(), jeanEnfant.getId(), 1);

        System.out.println("Vente en cours - Total actuel: " + vente3.getTotal() + "€");

        Vente vente4 = venteService.creerVente(client1.getId());
        System.out.println("Deuxième vente créée pour " + client1.getNom() + " (ID: " + vente4.getId() + ")");

        venteService.ajouterArticleAVente(vente4.getId(), chocolat.getId(), 10);
        venteService.ajouterArticleAVente(vente4.getId(), cafe.getId(), 2);

        vente4 = venteService.finaliserVente(vente4.getId());
        System.out.println("Vente finalisée - Total: " + vente4.getTotal() + "€");

        System.out.println("\n=== STATISTIQUES FINALES ===");

        System.out.println("\n--- Nombre total d'entités ---");
        System.out.println("Clients: " + clientService.listerTousLesClients().size());
        System.out.println("Articles: " + articleService.listerTousLesArticles().size());
        System.out.println("Ventes: " + venteService.listerToutesLesVentes().size());
        System.out.println("Ventes finalisées: " + venteService.listerVentesFinalisees().size());
        System.out.println("Ventes en cours: " + venteService.listerVentesEnCours().size());

        System.out.println("\n--- Articles avec stock faible (< 30) ---");
        articleService.articlesStockFaible(30).forEach(article ->
                System.out.println("- " + article.getNom() + " : " + article.getQuantiteStock() + " en stock")
        );

        System.out.println("\n--- Achats par client ---");
        clientService.listerTousLesClients().forEach(client -> {
            int nombreAchats = venteService.listerVentesParClient(client.getId()).size();
            System.out.println("- " + client.getNom() + " : " + nombreAchats + " achat(s)");
        });

        System.out.println("\n=== INITIALISATION TERMINÉE ===");
    }
}
