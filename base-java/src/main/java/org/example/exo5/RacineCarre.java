package org.example.exo5;

import java.util.InputMismatchException;
import java.util.Scanner;

public class RacineCarre {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean saisieValide = true;

        while (saisieValide) {
            try {
                System.out.print("Veuillez saisir un nombre entier positif : ");
                int nombre = scanner.nextInt();

                if (nombre < 0) {
                    throw new IllegalArgumentException("Le nombre ne peut pas être négatif !");
                }

                double racine = Math.sqrt(nombre);

                System.out.printf("La racine carrée de %d est : %.2f%n", nombre, racine);

            } catch (InputMismatchException e) {
                System.out.println("Erreur : Veuillez saisir un nombre entier valide !");
                scanner.nextLine();

            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
                System.out.println("Veuillez saisir un nombre positif ou zéro.");

            } catch (Exception e) {
                System.out.println("Une erreur inattendue s'est produite : " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
}
