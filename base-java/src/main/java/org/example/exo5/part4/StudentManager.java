package org.example.exo5.part4;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentManager {
    private static List<Student> students =  new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        while (true) {
            System.out.println("""
                    1/ Ajouter un étudiant
                    2/ Afficher la liste des étudiants
                    3/ Quitter
                    """);
            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        displayStudents();
                        break;
                    case 3:
                        System.out.println("Au revoir !");
                        return;
                    default:
                        System.out.println("Option invalide. Veuillez choisir 1, 2 ou 3.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Erreur : Veuillez entrer un nombre valide.");
            }
        }
    }

    public static void addStudent() {
        System.out.print("Entrez le nom de l'étudiant : ");
        String name = scanner.nextLine();

        int age = -1;
        boolean ageValide = false;

        while (!ageValide ) {
            try {
                System.out.print("Entrez l'âge de l'étudiant : ");
                age = Integer.parseInt(scanner.nextLine());
                Student student = new Student(name, age);
                students.add(student);
                System.out.println("Étudiant ajouté avec succès !");
                ageValide  = true;

            } catch (InvalidAgeException e) {
                System.out.println("Erreur : " + e.getMessage());
                System.out.println("Veuillez saisir un âge valide non négatif.");

            } catch (NumberFormatException e) {
                System.out.println("Erreur : Veuillez entrer un nombre valide pour l'âge.");
            }
        }
    }

    public static void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("Aucun étudiant dans la liste.");
            return;
        }

        System.out.println("Liste des étudiants");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i));
        }
        System.out.println("Total : " + students.size() + " étudiant");
    }

}
