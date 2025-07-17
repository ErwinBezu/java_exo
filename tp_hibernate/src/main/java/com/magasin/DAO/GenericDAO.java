package com.magasin.DAO;

import com.magasin.Util.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public abstract class GenericDAO <T> {
    protected Class<T> entityClass;
    private final SessionFactory sessionFactory;

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.sessionFactory = SessionFactorySingleton.getSessionFactory();
    }

    public T saveOrUpdate(T entity) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.saveOrUpdate(entity);
            session.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public T get(Long id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.get(entityClass, id);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<T> findAll() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            List<T> entities = session.createQuery("from " + entityClass.getSimpleName()).list();
            return entities;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<T> findByField(String fieldName, Object value) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            String hql = "FROM " + entityClass.getSimpleName() + " e WHERE e." + fieldName + " = :value";
            Query<T> query = session.createQuery(hql, entityClass);
            query.setParameter("value", value);
            return query.getResultList();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<T> findByFieldContaining(String fieldName, String value) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            String hql = "FROM " + entityClass.getSimpleName() +
                    " e WHERE LOWER(e." + fieldName + ") LIKE LOWER(:value)";
            Query<T> query = session.createQuery(hql, entityClass);
            query.setParameter("value", "%" + value + "%");
            return query.getResultList();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public boolean delete(Long id) {
        Session session = null;
        try {
            T entity = get(id);
            if (entity != null) {
                session = sessionFactory.openSession();
                session.beginTransaction();
                session.delete(entity);
                session.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
