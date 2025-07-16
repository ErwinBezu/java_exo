
package Exo2.Service;

import Exo2.DAO.SpecieDao;
import Exo2.Entity.Specie;
import Exo2.Enum.Category;

import java.util.List;

public class SpecieService {
    private final SpecieDao specieDao;

    public SpecieService() {
        this.specieDao = new SpecieDao();
    }

    public Specie createSpecie(String commonName, String scientificName, Category category) {
        if (commonName == null || commonName.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom commun ne peut pas être vide");
        }
        if (scientificName == null || scientificName.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom scientifique ne peut pas être vide");
        }
        if (category == null) {
            throw new IllegalArgumentException("La catégorie ne peut pas être null");
        }

        Specie specie = Specie.builder()
                .commonName(commonName.trim())
                .scientificName(scientificName.trim())
                .category(category)
                .build();

        return specieDao.create(specie);
    }

    public Specie findSpecieById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }
        return specieDao.findById(id);
    }

    public List<Specie> findAllSpecies() {
        return specieDao.findAll();
    }

    public Specie updateSpecie(Long id, String commonName, String scientificName, Category category) {
        Specie specie = specieDao.findById(id);
        if (specie == null) {
            throw new IllegalArgumentException("Espèce introuvable avec l'ID: " + id);
        }

        if (commonName != null && !commonName.trim().isEmpty()) {
            specie.setCommonName(commonName.trim());
        }

        if (scientificName != null && !scientificName.trim().isEmpty()) {
            specie.setScientificName(scientificName.trim());
        }

        if (category != null) {
            specie.setCategory(category);
        }

        return specieDao.update(specie, id);
    }

    public void deleteSpecie(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }

        Specie specie = specieDao.findById(id);
        if (specie == null) {
            throw new IllegalArgumentException("Espèce introuvable avec l'ID: " + id);
        }
        specieDao.delete(id);
    }
}