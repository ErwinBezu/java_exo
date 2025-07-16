
package Exo2.Service;

import Exo2.DAO.TravellogDao;
import Exo2.Entity.Travellog;
import Exo2.Enum.TravelMode;

import java.util.List;

public class TravellogService {
    private final TravellogDao travellogDao;

    public TravellogService() {
        this.travellogDao = new TravellogDao();
    }

    public Travellog createTravellog(Long observationId, Double distanceKm, TravelMode mode) {
        if (observationId == null) {
            throw new IllegalArgumentException("L'ID de l'observation ne peut pas être null");
        }
        if (distanceKm == null || distanceKm <= 0) {
            throw new IllegalArgumentException("La distance doit être positive");
        }
        if (mode == null) {
            throw new IllegalArgumentException("Le mode de transport ne peut pas être null");
        }

        Travellog travellog = travellogDao.createWithObservation(observationId, distanceKm, mode);
        if (travellog == null) {
            throw new RuntimeException("Erreur lors de la création du travellog. Vérifiez que l'observation existe.");
        }

        return travellog;
    }

    public Travellog findTravellogById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }
        return travellogDao.findById(id);
    }

    public List<Travellog> findAllTravellogs() {
        return travellogDao.findAll();
    }

    public Travellog updateTravellog(Long id, Double distanceKm, TravelMode mode) {
        Travellog travellog = travellogDao.findById(id);
        if (travellog == null) {
            throw new IllegalArgumentException("Travellog introuvable avec l'ID: " + id);
        }

        if (distanceKm != null && distanceKm > 0) {
            travellog.setDistanceKm(distanceKm);
        }

        if (mode != null) {
            travellog.setMode(mode);
        }

        return travellogDao.update(travellog, id);
    }

    public void deleteTravellog(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }

        Travellog travellog = travellogDao.findById(id);
        if (travellog == null) {
            throw new IllegalArgumentException("Travellog introuvable avec l'ID: " + id);
        }
        travellogDao.delete(id);
    }
}