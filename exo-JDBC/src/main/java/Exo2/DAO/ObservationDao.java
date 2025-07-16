package Exo2.DAO;

import Exo2.Entity.Observation;
import Exo2.Entity.Travellog;
import Exo2.Enum.TravelMode;

import java.util.List;

public class ObservationDao extends GenericDao<Observation> {

    public ObservationDao() {
        super(Observation.class);
    }

    public List<Observation> findBySpecieId(Long specieId) {
        return em.createQuery("SELECT o FROM Observation o WHERE o.specie.id = :specieId", Observation.class)
                .setParameter("specieId", specieId)
                .getResultList();
    }

    public Observation updateSpecific(Observation observation, Long id) {
        try {
            Observation observationFound = findById(id);
            if (observationFound != null) {
                em.getTransaction().begin();
                observationFound.setObserverName(observation.getObserverName());
                observationFound.setLocation(observation.getLocation());
                observationFound.setLatitude(observation.getLatitude());
                observationFound.setLongitude(observation.getLongitude());
                observationFound.setObservationDate(observation.getObservationDate());
                observationFound.setComment(observation.getComment());
                observationFound.setTravellog(observation.getTravellog());
                em.getTransaction().commit();
                return observationFound;
            }
            return null;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return null;
        }
    }

    public Observation createWithTravellog(Observation observation, Double distanceKm, TravelMode travelMode) {
        try {
            em.getTransaction().begin();

            em.persist(observation);

            if (distanceKm != null && travelMode != null) {
                Travellog travellog = new Travellog(distanceKm, travelMode);
                travellog.setObservation(observation);
                em.persist(travellog);
                observation.setTravellog(travellog);
            }

            em.getTransaction().commit();
            return observation;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return null;
        }
    }
}
