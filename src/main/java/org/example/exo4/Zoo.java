package org.example.exo4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Zoo {
    private static Scanner scanner = new Scanner(System.in);
    private static List<IEnclos<?>> enclosList = new ArrayList<>();
    private static List<Animal> animaux = new ArrayList<>();


    public static void main(String[] args) {
        start();
    }

    public static void start() {
        while (true) {
            System.out.println("""
                    1/ création d'un animal
                    2/ création d'enclos
                    3/ ajouter des animaux a des enclos
                    4/ visualiser les animaux dans un enclos
                    5/ quitter
                    """);

            int entry = scanner.nextInt();
            scanner.nextLine();

            switch (entry) {
                case 1 -> ajouterAnimal();
                case 2 -> creerEnclos();
                case 3 -> ajouterAnimalEnclos();
                case 4 -> afficherAnimaux();
                case 5 -> {
                    System.out.println("Bye Bye");
                    return;
                }
                default -> {
                    System.out.println("Choix invalide ! Veuillez réessayer.");
                }
            }
        }
    }

    private static void ajouterAnimal() {
        System.out.print("Type d'animal (lion / elephant) : ");
        String type = scanner.nextLine().toLowerCase();

        System.out.print("Nom de l'animal : ");
        String nom = scanner.nextLine();

        System.out.print("Âge de l'animal : ");
        int age = scanner.nextInt();
        scanner.nextLine();

        switch (type) {
            case "lion":
                Lion lion = new Lion(nom, age);
                animaux.add(lion);
                System.out.println("Lion " + nom + " créé avec succès !");
                break;
            case "elephant":
                System.out.print("Poids de l'éléphant (kg) : ");
                double poids = scanner.nextDouble();
                scanner.nextLine();
                Elephant elephant = new Elephant(nom, age, poids);
                animaux.add(elephant);
                System.out.println("Éléphant " + nom + " créé avec succès !");
                break;
            default:
                System.out.println("Type d'animal inconnu.");
        }
    }

    private static void creerEnclos() {
        System.out.print("Type d'enclos (lion / elephant) : ");
        String type = scanner.nextLine().toLowerCase();

        switch (type) {
            case "lion":
                Enclos<Lion> enclosLions = new Enclos<>();
                enclosList.add(enclosLions);
                System.out.println("Enclos des lions créé");
                break;
            case "elephant":
                Enclos<Elephant> enclosElephants = new Enclos<>();
                enclosList.add(enclosElephants);
                System.out.println("Enclos des éléphants créé");
                break;
            default:
                System.out.println("Type d'enclos inconnu.");
        }
    }

    private static void ajouterAnimalEnclos() {
        if (enclosList.isEmpty()) {
            System.out.println("Aucun enclos disponible. Créez d'abord un enclos.");
            return;
        }

        if (animaux.isEmpty()) {
            System.out.println("Aucun animal disponible. Créez d'abord un animal.");
            return;
        }

        System.out.println("Animaux disponibles");
        for (int i = 0; i < animaux.size(); i++) {
            System.out.println(i + ": " + animaux.get(i));
        }

        System.out.print("Choisissez l'ID de l'animal : ");
        int animalId = scanner.nextInt();
        scanner.nextLine();

        if (animalId < 0 || animalId >= animaux.size()) {
            System.out.println("ID d'animal invalide.");
            return;
        }

        Animal animal = animaux.get(animalId);

        System.out.println("Enclos disponibles");
        for (int i = 0; i < enclosList.size(); i++) {
            System.out.println(i + ":" + enclosList.get(i));
        }

        System.out.print("Choisissez l'ID de l'enclos : ");
        int enclosId = scanner.nextInt();
        scanner.nextLine();

        if (enclosId < 0 || enclosId >= enclosList.size()) {
            System.out.println("ID d'enclos invalide.");
            return;
        }

        try {
            IEnclos enclos = enclosList.get(enclosId);

            if (animal instanceof Lion) {
                ((Enclos<Lion>) enclos).ajouterAnimal((Lion) animal);
            } else if (animal instanceof Elephant) {
                ((Enclos<Elephant>) enclos).ajouterAnimal((Elephant) animal);
            }

            System.out.println("Animal ajouté à l'enclos avec succès !");
            animaux.remove(animalId);

        } catch (ClassCastException e) {
            System.out.println("Erreur : Type d'animal incompatible avec cet enclos.");
        }
    }

    private static void afficherAnimaux() {
        if (enclosList.isEmpty()) {
            System.out.println("Aucun enclos disponible.");
            return;
        }

        System.out.println("Enclos disponibles");
        for (int i = 0; i < enclosList.size(); i++) {
            System.out.println(i + ": " + enclosList.get(i));
        }

        System.out.print("Choisissez l'ID de l'enclos à visualiser : ");
        int enclosId = scanner.nextInt();
        scanner.nextLine();

        if (enclosId < 0 || enclosId >= enclosList.size()) {
            System.out.println("ID d'enclos invalide.");
            return;
        }

        System.out.println("=== Animaux dans l'enclos " + enclosId + " ===");
        enclosList.get(enclosId).afficherAnimaux();
    }
}