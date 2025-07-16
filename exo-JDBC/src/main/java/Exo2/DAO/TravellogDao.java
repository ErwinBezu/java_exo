package Exo2.DAO;

import Exo2.Entity.Observation;
import Exo2.Entity.Travellog;
import Exo2.Enum.TravelMode;
import Exo2.Util.DatabaseManager;

import javax.persistence.EntityManager;
import java.util.List;

public class TravellogDao {
    private EntityManager em;

    public TravellogDao() {
        this.em = DatabaseManager.getEntityManager();
    }

    public Travellog create(Travellog travellog) {
        try {
            em.getTransaction().begin();
            em.persist(travellog);
            em.getTransaction().commit();
            return travellog;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return null;
        }
    }

    public Travellog findById(Long id) {
        return em.find(Travellog.class, id);
    }

    public List<Travellog> findAll() {
        return em.createQuery("SELECT t FROM Travellog t", Travellog.class).getResultList();
    }

    public Travellog update(Travellog travellog, Long id) {
        try {
            Travellog travellogFound = findById(id);
            if (travellogFound != null) {
                em.getTransaction().begin();
                travellogFound.setDistanceKm(travellog.getDistanceKm());
                travellogFound.setMode(travellog.getMode());
                em.getTransaction().commit();
                return travellogFound;
            }
            return null;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return null;
        }
    }

    public boolean delete(Long id) {
        try {
            Travellog travellogFound = findById(id);
            if (travellogFound != null) {
                em.getTransaction().begin();
                em.remove(travellogFound);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        }
    }

    public Travellog createWithObservation(Long observationId, Double distanceKm, TravelMode mode) {
        try {
            em.getTransaction().begin();

            Observation observation = em.find(Observation.class, observationId);
            if (observation == null) {
                em.getTransaction().rollback();
                return null;
            }

            Travellog travellog = new Travellog(distanceKm, mode);
            travellog.setObservation(observation);
            em.persist(travellog);

            em.getTransaction().commit();
            return travellog;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return null;
        }
    }
}

