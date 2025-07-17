package com.magasin.Service;

import com.magasin.DAO.ArticleDAO;
import com.magasin.Entity.Article;

import java.time.LocalDate;
import java.util.List;

public class ArticleService {
    private final ArticleDAO articleDAO;

    public ArticleService() {
        this.articleDAO = new ArticleDAO();
    }

    public Article ajouterArticle(Article article) {
        if (article == null) {
            throw new IllegalArgumentException("L'article ne peut pas être null");
        }
        if (article.getNom() == null || article.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'article est obligatoire");
        }
        if (article.getPrice() == null || article.getPrice().signum() <= 0) {
            throw new IllegalArgumentException("Le prix doit être positif");
        }
        if (article.getQuantiteStock() < 0) {
            throw new IllegalArgumentException("La quantité en stock ne peut pas être négative");
        }

        return articleDAO.saveOrUpdate(article);
    }

    public Article modifierArticle(Article article) {
        if (article == null || article.getId() == null) {
            throw new IllegalArgumentException("L'article et son ID ne peuvent pas être null");
        }

        Article existant = articleDAO.get(article.getId());
        if (existant == null) {
            throw new IllegalArgumentException("Article introuvable avec l'ID: " + article.getId());
        }

        return articleDAO.saveOrUpdate(article);
    }

    public boolean supprimerArticle(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }

        Article article = articleDAO.get(id);
        if (article == null) {
            throw new IllegalArgumentException("Article introuvable avec l'ID: " + id);
        }

        if (article.getLignesVente() != null && !article.getLignesVente().isEmpty()) {
            throw new IllegalStateException("Impossible de supprimer un article qui a des ventes associées");
        }

        return articleDAO.delete(id);
    }

    public Article consulterArticle(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }
        return articleDAO.get(id);
    }

    public List<Article> listerTousLesArticles() {
        return articleDAO.findAll();
    }

    public Article restockerArticle(Long id, int quantite) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }
        if (quantite <= 0) {
            throw new IllegalArgumentException("La quantité de restock doit être positive");
        }

        Article article = articleDAO.get(id);
        if (article == null) {
            throw new IllegalArgumentException("Article introuvable avec l'ID: " + id);
        }

        article.restock(quantite);
        return articleDAO.saveOrUpdate(article);
    }

    public List<Article> rechercherParNom(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de recherche ne peut pas être vide");
        }
        return articleDAO.findByNom(nom.trim());
    }

    public List<Article> articlesStockFaible(int seuilMinimal) {
        if (seuilMinimal < 0) {
            throw new IllegalArgumentException("Le seuil minimal ne peut pas être négatif");
        }
        return articleDAO.findByStockInferieurA(seuilMinimal);
    }

    public List<Article> articlesDisponibles() {
        return articleDAO.findArticlesDisponibles();
    }

    public List<Article> articlesRestockesDepuis(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("La date ne peut pas être null");
        }
        return articleDAO.findByRestockDateApres(date);
    }

    public boolean verifierDisponibilite(Long articleId, int quantiteRequise) {
        if (articleId == null) {
            throw new IllegalArgumentException("L'ID de l'article ne peut pas être null");
        }
        if (quantiteRequise <= 0) {
            throw new IllegalArgumentException("La quantité requise doit être positive");
        }

        Article article = articleDAO.get(articleId);
        if (article == null) {
            return false;
        }

        return article.isAvailable(quantiteRequise);
    }

    public Article reduireStock(Long articleId, int quantite) {
        if (articleId == null) {
            throw new IllegalArgumentException("L'ID de l'article ne peut pas être null");
        }

        Article article = articleDAO.get(articleId);
        if (article == null) {
            throw new IllegalArgumentException("Article introuvable avec l'ID: " + articleId);
        }

        article.reduireStock(quantite);
        return articleDAO.saveOrUpdate(article);
    }
}
