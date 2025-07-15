package Exo2.Service;

import Exo2.DAO.RegionDao;
import Exo2.Entity.Region;
import Exo2.Enum.Climat;

import java.util.List;

public class RegionService {
    private final RegionDao regionDao;

    public RegionService() {
        this.regionDao = new RegionDao();
    }

    public Region createRegion(String nom, Double surface, Climat climat) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la région ne peut pas être vide");
        }
        if (surface == null || surface <= 0) {
            throw new IllegalArgumentException("La surface doit être positive");
        }
        if (climat == null) {
            throw new IllegalArgumentException("Le climat ne peut pas être null");
        }

        Region region = Region.builder()
                .nom(nom.trim())
                .surface(surface)
                .climat(climat)
                .build();

        return regionDao.create(region);
    }

    public Region findRegionById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }

        return regionDao.findById(id);
    }

    public List<Region> findAllRegions() {
        return regionDao.findAll();
    }

    public Region updateRegion(Long id, String nom, Double surface, Climat climat) {
        Region region = regionDao.findById(id);
        if (region == null) {
            throw new IllegalArgumentException("Région introuvable avec l'ID: " + id);
        }

        if (nom != null && !nom.trim().isEmpty()) {
            region.setNom(nom.trim());
        }

        if (surface != null && surface > 0) {
            region.setSurface(surface);
        }

        if (climat != null) {
            region.setClimat(climat);
        }
        return regionDao.update(region);
    }

    public void deleteRegion(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }

        Region region = regionDao.findById(id);
        if (region == null) {
            throw new IllegalArgumentException("Région introuvable avec l'ID: " + id);
        }

        regionDao.delete(id);
    }

    public void closeService() {
        regionDao.close();
    }
}
