package Exo2.DAO;

import Exo2.Entity.Specie;
import Exo2.Util.DatabaseManager;

import javax.persistence.EntityManager;
import java.util.List;

public class SpecieDao {
    private EntityManager em;

    public SpecieDao() {
        this.em = DatabaseManager.getEntityManager();
    }

    public Specie create(Specie specie) {
        try {
            em.getTransaction().begin();
            em.persist(specie);
            em.getTransaction().commit();
            return specie;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return null;
        }
    }

    public Specie findById(Long id) {
        return em.find(Specie.class, id);
    }

    public List<Specie> findAll() {
        return em.createQuery("SELECT s FROM Specie s", Specie.class).getResultList();
    }

    public Specie update(Specie specie, Long id) {
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

    public boolean delete(Long id) {
        try {
            Specie specieFound = findById(id);
            if (specieFound != null) {
                em.getTransaction().begin();
                em.remove(specieFound);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        }
    }
}