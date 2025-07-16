package Exo2.DAO;

import Exo2.Util.DatabaseManager;

import javax.persistence.EntityManager;
import java.util.List;

public class GenericDao<T> {
    protected EntityManager em;
    private Class<T> entityClass;

    public GenericDao(Class<T> entityClass) {
        this.em = DatabaseManager.getEntityManager();
        this.entityClass = entityClass;
    }

    public T create(T entity) {
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return null;
        }
    }

    public T findById(Long id) {
        return em.find(entityClass, id);
    }

    public List<T> findAll() {
        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        return em.createQuery(jpql, entityClass).getResultList();
    }

    public boolean delete(Long id) {
        try {
            T entityFound = findById(id);
            if (entityFound != null) {
                em.getTransaction().begin();
                em.remove(entityFound);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        }
    }

    public T update(T entity) {
        try {
            em.getTransaction().begin();
            T updatedEntity = em.merge(entity);
            em.getTransaction().commit();
            return updatedEntity;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return null;
        }
    }
}