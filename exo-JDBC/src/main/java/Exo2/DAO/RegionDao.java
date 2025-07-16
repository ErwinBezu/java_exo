package Exo2.DAO;

import Exo2.Entity.Region;
import Exo2.Entity.Specie;

public class RegionDao extends GenericDao<Region>  {

    public RegionDao() {
        super(Region.class);
    }

    public Region updateSpecific(Region region, long id) {
        try {
            Region regionFound = findById(id);
            if (regionFound != null) {
                em.getTransaction().begin();
                regionFound.setNom(region.getNom());
                regionFound.setSurface(region.getSurface());
                regionFound.setClimat(region.getClimat());
                em.getTransaction().commit();
                return regionFound;
            }
            return null;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return null;
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
