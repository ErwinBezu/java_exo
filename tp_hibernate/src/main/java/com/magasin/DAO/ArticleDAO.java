package com.magasin.DAO;

import com.magasin.Entity.Article;
import com.magasin.Util.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class ArticleDAO extends GenericDAO<Article> {
    public ArticleDAO() {
        super(Article.class);
    }

    public List<Article> findByNom(String nom) {
        Session session = null;
        try {
            session = SessionFactorySingleton.getSessionFactory().openSession();
            Query<Article> query = session.createQuery(
                    "FROM Article a WHERE LOWER(a.nom) LIKE LOWER(:nom)", Article.class);
            query.setParameter("nom", "%" + nom + "%");
            return query.getResultList();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<Article> findByStockInferieurA(int quantiteMinimale) {
        Session session = null;
        try {
            session = SessionFactorySingleton.getSessionFactory().openSession();
            Query<Article> query = session.createQuery(
                    "FROM Article a WHERE a.quantiteStock < :quantite ORDER BY a.quantiteStock ASC", Article.class);
            query.setParameter("quantite", quantiteMinimale);
            return query.getResultList();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<Article> findByRestockDateApres(LocalDate date) {
        Session session = null;
        try {
            session = SessionFactorySingleton.getSessionFactory().openSession();
            Query<Article> query = session.createQuery(
                    "FROM Article a WHERE a.restockDate >= :date ORDER BY a.restockDate DESC", Article.class);
            query.setParameter("date", date);
            return query.getResultList();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<Article> findArticlesDisponibles() {
        Session session = null;
        try {
            session = SessionFactorySingleton.getSessionFactory().openSession();
            Query<Article> query = session.createQuery(
                    "FROM Article a WHERE a.quantiteStock > 0 ORDER BY a.nom", Article.class);
            return query.getResultList();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

}
