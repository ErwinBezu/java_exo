package Exo2.DAO;

import Exo2.Entity.Region;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class RegionDao {
    private final EntityManagerFactory emf;

    public RegionDao() {
        this.emf = Persistence.createEntityManagerFactory("Demo_Jpa");
    }

    public Region create(Region region) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(region);
            em.getTransaction().commit();
            return region;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Region findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Region region = em.find(Region.class, id);
            return region;
        } finally {
            em.close();
        }
    }

    public List<Region> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Region> query = em.createQuery("SELECT r FROM Region r ORDER BY r.nom", Region.class);
            List<Region> regions = query.getResultList();
            return regions;
        } finally {
            em.close();
        }
    }

    public Region update(Region region) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Region updatedRegion = em.merge(region);
            em.getTransaction().commit();
            return updatedRegion;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Region region = em.find(Region.class, id);
            if (region != null) {
                em.remove(region);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
