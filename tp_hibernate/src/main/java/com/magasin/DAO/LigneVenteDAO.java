package com.magasin.DAO;

import com.magasin.Entity.LigneVente;
import com.magasin.Entity.Article;
import com.magasin.Entity.Vente;
import com.magasin.Util.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

public class LigneVenteDAO extends GenericDAO<LigneVente> {
    public LigneVenteDAO() {
        super(LigneVente.class);
    }

    public List<LigneVente> findByArticle(Article article) {
        Session session = null;
        try {
            session = SessionFactorySingleton.getSessionFactory().openSession();
            Query<LigneVente> query = session.createQuery(
                    "FROM LigneVente lv WHERE lv.article = :article", LigneVente.class);
            query.setParameter("article", article);
            return query.getResultList();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<LigneVente> findByVente(Vente vente) {
        Session session = null;
        try {
            session = SessionFactorySingleton.getSessionFactory().openSession();
            Query<LigneVente> query = session.createQuery(
                    "FROM LigneVente lv WHERE lv.vente = :vente", LigneVente.class);
            query.setParameter("vente", vente);
            return query.getResultList();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
