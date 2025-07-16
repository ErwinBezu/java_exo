package Exo2.DAO;

import Exo2.Entity.Region;
import Exo2.Entity.Specie;
import Exo2.Util.DatabaseManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class RegionDao {
    private EntityManager em;

    public RegionDao() {
        this.em = DatabaseManager.getEntityManager();
    }

    public Region create(Region region) {
        try {
            em.getTransaction().begin();
            em.persist(region);
            em.getTransaction().commit();
            return region;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return null;
        }
    }

    public Region findById(Long id) {
        return em.find(Region.class,id);
    }

    public List<Region> findAll() {
        return em.createQuery("select r from Region r ", Region.class).getResultList();
    }

    public Region update (Region region , long id){
        try{
            Region regionFound = findById(id);
            if(regionFound != null){
                em.getTransaction().begin();
                regionFound.setNom(region.getNom());
                regionFound.setSurface(region.getSurface());
                regionFound.setClimat(region.getClimat());
                em.getTransaction().commit();
                return regionFound;
            }
            return null;
        }catch (Exception e){
            em.getTransaction().rollback();
            return null;
        }
    }

    public boolean delete (long id){
        try{
            Region regionFound = findById(id);
            if(regionFound != null){
                em.getTransaction().begin();
                em.remove(regionFound);
                em.getTransaction().commit();
                return true;
            }
            return false;
        }catch (Exception e){
            em.getTransaction().rollback();
            return false;
        }
    }

    public boolean addSpecieToRegion(Long regionId, Long specieId) {
        try {
            em.getTransaction().begin();

            Region region = em.find(Region.class, regionId);
            Specie specie = em.find(Specie.class, specieId);

            if (region != null && specie != null) {
                region.addSpecie(specie);
                em.merge(region);
                em.getTransaction().commit();
                return true;
            }

            em.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        }
    }

    public boolean removeSpecieFromRegion(Long regionId, Long specieId) {
        try {
            em.getTransaction().begin();

            Region region = em.find(Region.class, regionId);
            Specie specie = em.find(Specie.class, specieId);

            if (region != null && specie != null) {
                region.getSpecieList().remove(specie);
                specie.getRegionList().remove(region);
                em.merge(region);
                em.merge(specie);
                em.getTransaction().commit();
                return true;
            }

            em.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        }
    }

}
