package Exo2.DAO;

import Exo2.Entity.Specie;

public class SpecieDao extends GenericDao<Specie> {
    public SpecieDao() {
        super(Specie.class);
    }

    public Specie updateSpecific(Specie specie, Long id) {
        try {
            Specie specieFound = findById(id);
            if (specieFound != null) {
                em.getTransaction().begin();
                specieFound.setCommonName(specie.getCommonName());
                specieFound.setScientificName(specie.getScientificName());
                specieFound.setCategory(specie.getCategory());
                em.getTransaction().commit();
                return specieFound;
            }
            return null;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return null;
        }
    }
}