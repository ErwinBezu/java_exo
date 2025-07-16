package Exo2.DAO;

import Exo2.Entity.Observation;
import Exo2.Entity.Travellog;
import Exo2.Enum.TravelMode;

public class TravellogDao extends GenericDao<Travellog> {

    public TravellogDao() {
        super(Travellog.class);
    }

    public Travellog updateSpecific(Travellog travellog, Long id) {
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

