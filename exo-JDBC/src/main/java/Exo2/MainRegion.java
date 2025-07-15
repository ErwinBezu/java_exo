package Exo2;

import Exo2.Entity.Region;
import Exo2.Enum.Climat;
import Exo2.Service.RegionService;

import java.util.List;

public class MainRegion {

    public static void main(String[] args) {
        RegionService regionService = new RegionService();

        try {
            Region provence = regionService.createRegion("Provence", 31400.0, Climat.MEDITERRANEEN);
            Region bretagne = regionService.createRegion("Bretagne", 27208.0, Climat.OCEANIQUE);
            Region alsace = regionService.createRegion("Alsace", 8280.0, Climat.CONTINENTAL);
            Region sahara = regionService.createRegion("Sahara", 9000000.0, Climat.DESERTIQUE);

            List<Region> regions = regionService.findAllRegions();
            System.out.println("Nombre total de régions : " + regions.size());

            for (Region region : regions) {
                System.out.println(region);
            }

            Region findRegion = regionService.findRegionById(provence.getId());
            if (findRegion != null) {
                System.out.println("Région trouvée avec l'ID " + provence.getId() + " : " + findRegion);
            } else {
                System.out.println("Aucune région trouvée");
            }

            Region regionFalse = regionService.findRegionById(999L);
            System.out.println("Recherche ID 999 : " + (regionFalse == null ? "Non trouvée" : "Trouvée"));

            System.out.println("Avant modification : " + alsace);

            Region regionUpdate = regionService.updateRegion(
                    alsace.getId(),
                    "Alsace-Lorraine",
                    10000.0,
                    Climat.CONTINENTAL
            );

            System.out.println("Après modification : " + regionUpdate);

            System.out.println("Suppression du Sahara (ID: " + sahara.getId() + ")");
            regionService.deleteRegion(sahara.getId());

            regions = regionService.findAllRegions();
            System.out.println("Nombre total de régions : " + regions.size());

            for (Region region : regions) {
                System.out.println(region);
            }

        } catch (Exception e) {
            System.err.println("erreur : " + e.getMessage());
        } finally {
            regionService.closeService();
            System.out.println("\nService fermé");
        }
    }
}
