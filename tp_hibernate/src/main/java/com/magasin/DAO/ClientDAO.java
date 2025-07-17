package com.magasin.DAO;

import com.magasin.Entity.Client;
import com.magasin.Util.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;


public class ClientDAO extends GenericDAO<Client> {
    public ClientDAO() {
        super(Client.class);
    }

    public Client findByEmail(String email) {
        Session session = null;
        try {
            session = SessionFactorySingleton.getSessionFactory().openSession();
            Query<Client> query = session.createQuery(
                    "FROM Client c WHERE LOWER(c.email) = LOWER(:email)", Client.class);
            query.setParameter("email", email);
            return query.uniqueResult();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<Client> findByNom(String nom) {
        Session session = null;
        try {
            session = SessionFactorySingleton.getSessionFactory().openSession();
            Query<Client> query = session.createQuery(
                    "FROM Client c WHERE LOWER(c.nom) LIKE LOWER(:nom)", Client.class);
            query.setParameter("nom", "%" + nom + "%");
            return query.getResultList();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public boolean emailExists(String email) {
        Session session = null;
        try {
            session = SessionFactorySingleton.getSessionFactory().openSession();
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(c) FROM Client c WHERE LOWER(c.email) = LOWER(:email)", Long.class);
            query.setParameter("email", email);
            Long count = query.uniqueResult();
            return count > 0;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
