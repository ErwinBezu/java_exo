package com.magasin.DAO;

import com.magasin.Entity.Vente;
import com.magasin.Entity.Client;
import com.magasin.Enum.StatusSale;
import com.magasin.Util.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

public class VenteDAO extends GenericDAO<Vente> {
    public VenteDAO() {
        super(Vente.class);
    }

    public List<Vente> findByClient(Client client) {
        Session session = null;
        try {
            session = SessionFactorySingleton.getSessionFactory().openSession();
            Query<Vente> query = session.createQuery(
                    "FROM Vente v WHERE v.client = :client ORDER BY v.dateVente DESC", Vente.class);
            query.setParameter("client", client);
            return query.getResultList();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<Vente> findByStatus(StatusSale status) {
        Session session = null;
        try {
            session = SessionFactorySingleton.getSessionFactory().openSession();
            Query<Vente> query = session.createQuery(
                    "FROM Vente v WHERE v.status = :status ORDER BY v.dateVente DESC", Vente.class);
            query.setParameter("status", status);
            return query.getResultList();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<Vente> findByDateRange(LocalDateTime dateDebut, LocalDateTime dateFin) {
        Session session = null;
        try {
            session = SessionFactorySingleton.getSessionFactory().openSession();
            Query<Vente> query = session.createQuery(
                    "FROM Vente v WHERE v.dateVente BETWEEN :dateDebut AND :dateFin ORDER BY v.dateVente DESC", Vente.class);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.getResultList();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<Vente> findVentesFinalisees() {
        return findByStatus(StatusSale.FINALISEE);
    }

    public List<Vente> findVentesEnCours() {
        return findByStatus(StatusSale.EN_COURS);
    }
}
