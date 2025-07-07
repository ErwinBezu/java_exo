package org.example.exo5;

import java.util.Scanner;

public class ConvertionStringToInt {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int entier = 0;
        boolean saisieValide = false;

        while (!saisieValide) {
            System.out.print("Saisir un nombre entier : ");
            String entree = scanner.nextLine();

            try {
                entier = Integer.parseInt(entree);
                saisieValide = true;
            } catch (NumberFormatException e) {
                System.out.println("Erreur : '" + entree + "' est pas un entier valide. Essaie Encore !");
            }
        }

        System.out.println("Vous avez saisi l'entier : " + entier);
        scanner.close();
    }
}
