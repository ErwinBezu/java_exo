package Exo2;

import Exo2.Entity.Observation;
import Exo2.Entity.Region;
import Exo2.Entity.Specie;
import Exo2.Enum.Category;
import Exo2.Enum.Climat;
import Exo2.Enum.TravelMode;
import Exo2.Service.ObservationService;
import Exo2.Service.RegionService;
import Exo2.Service.SpecieService;

import java.time.LocalDate;
import java.util.List;

public class MainRegion {

    public static void main(String[] args) {
        RegionService regionService = new RegionService();
        SpecieService specieService = new SpecieService();
        ObservationService observationService = new ObservationService();

        try {
            System.out.println("=== CRÉATION DES RÉGIONS ===");
            Region provence = regionService.createRegion("Provence", 31400.0, Climat.MEDITERRANEEN);
            Region bretagne = regionService.createRegion("Bretagne", 27208.0, Climat.OCEANIQUE);
            Region alsace = regionService.createRegion("Alsace", 8280.0, Climat.CONTINENTAL);

            System.out.println("Régions créées :");
            regionService.findAllRegions().forEach(System.out::println);

            System.out.println("\n=== CRÉATION DES ESPÈCES ===");
            Specie rossignol = specieService.createSpecie("Rossignol", "Luscinia megarhynchos", Category.BIRD);
            Specie lavande = specieService.createSpecie("Lavande", "Lavandula angustifolia", Category.PLANT);
            Specie cerf = specieService.createSpecie("Cerf", "Cervus elaphus", Category.MAMMAL);

            System.out.println("Espèces créées :");
            specieService.findAllSpecies().forEach(System.out::println);

            System.out.println("\n=== ASSOCIATION ESPÈCES - RÉGIONS ===");
            regionService.addSpecieToRegion(provence.getId(), rossignol.getId());
            regionService.addSpecieToRegion(provence.getId(), lavande.getId());
            regionService.addSpecieToRegion(bretagne.getId(), cerf.getId());
            regionService.addSpecieToRegion(alsace.getId(), cerf.getId());

            System.out.println("Espèces en Provence :");
            regionService.getSpeciesInRegion(provence.getId()).forEach(System.out::println);

            System.out.println("\n=== CRÉATION D'OBSERVATIONS ===");
            Observation obs1 = observationService.createObservation(
                    rossignol.getId(),
                    "Jean Dupont",
                    "Aix-en-Provence",
                    43.5297, 5.4474,
                    LocalDate.now(),
                    "Chant magnifique au lever du soleil",
                    2.5,
                    TravelMode.WALKING
            );

            Observation obs2 = observationService.createObservation(
                    lavande.getId(),
                    "Marie Martin",
                    "Valensole",
                    43.8356, 5.9844,
                    LocalDate.now().minusDays(2),
                    "Champs de lavande en pleine floraison",
                    15.0,
                    TravelMode.CAR
            );

            Observation obs3 = observationService.createObservation(
                    cerf.getId(),
                    "Pierre Moreau",
                    "Forêt de Brocéliande",
                    48.0297, -2.2694,
                    LocalDate.now().minusDays(5),
                    "Jeune cerf observé à l'aube",
                    8.0,
                    TravelMode.BIKE
            );

            System.out.println("Observations créées :");
            observationService.findAllObservations().forEach(System.out::println);

            System.out.println("\n=== RECHERCHE D'OBSERVATIONS PAR ESPÈCE ===");
            System.out.println("Observations de rossignol :");
            observationService.findObservationsBySpecie(rossignol.getId()).forEach(System.out::println);

            System.out.println("\n=== SUPPRESSION D'UNE OBSERVATION ===");
            observationService.deleteObservation(obs2.getId());
            System.out.println("Observation supprimée. Nombre d'observations restantes : " +
                    observationService.findAllObservations().size());

            System.out.println("\n=== STATISTIQUES FINALES ===");
            System.out.println("Nombre de régions : " + regionService.findAllRegions().size());
            System.out.println("Nombre d'espèces : " + specieService.findAllSpecies().size());
            System.out.println("Nombre d'observations : " + observationService.findAllObservations().size());

        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
        } finally {
            System.out.println("\nService fermé");
        }
    }
}
