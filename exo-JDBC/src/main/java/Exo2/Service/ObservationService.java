package Exo2.Service;

import Exo2.DAO.ObservationDao;
import Exo2.DAO.SpecieDao;
import Exo2.Entity.Observation;
import Exo2.Entity.Specie;
import Exo2.Enum.TravelMode;
import Exo2.Util.DatabaseManager;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class ObservationService {
    private final ObservationDao observationDao;

    public ObservationService() {
        this.observationDao = new ObservationDao();
    }

    public Observation createObservation(Long specieId, String observerName, String location,
                                         Double latitude, Double longitude, LocalDate observationDate,
                                         String comment, Double distanceKm, TravelMode travelMode) {

        if (specieId == null) {
            throw new IllegalArgumentException("L'ID de l'espèce ne peut pas être null");
        }
        if (observerName == null || observerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'observateur ne peut pas être vide");
        }
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("La localisation ne peut pas être vide");
        }
        if (observationDate == null) {
            throw new IllegalArgumentException("La date d'observation ne peut pas être null");
        }

        Specie specie = new SpecieDao().findById(specieId);
        if (specie == null) {
            throw new IllegalArgumentException("Espèce introuvable avec l'ID: " + specieId);
        }

        Observation observation = new Observation(
                specie, observerName.trim(), location.trim(),
                latitude, longitude, observationDate, comment
        );

        Observation result = observationDao.createWithTravellog(observation, distanceKm, travelMode);
        if (result == null) {
            throw new RuntimeException("Erreur lors de la création de l'observation");
        }

        return result;
    }

    public Observation findObservationById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }
        return observationDao.findById(id);
    }

    public List<Observation> findAllObservations() {
        return observationDao.findAll();
    }

    public List<Observation> findObservationsBySpecie(Long specieId) {
        if (specieId == null) {
            throw new IllegalArgumentException("L'ID de l'espèce ne peut pas être null");
        }
        return observationDao.findBySpecieId(specieId);
    }

    public void deleteObservation(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }

        Observation observation = observationDao.findById(id);
        if (observation == null) {
            throw new IllegalArgumentException("Observation introuvable avec l'ID: " + id);
        }
        observationDao.delete(id);
    }
}