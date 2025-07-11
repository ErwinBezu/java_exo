package org.example.exo5;

public class AccesTableau {
    public static void main(String[] args) {
        int[] tableau = {1, 2, 3, 4, 5};

        try {
            int element4 = tableau[4];
            System.out.println("Élément à l'indice 4 : " + element4);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Erreur : Index 4 hors limites !");
        }

        try {
            int element5 = tableau[5];
            System.out.println("Élément à l'indice 5 : " + element5);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Erreur : Index 5 hors limites !");
        }

        System.out.println("Affichage de tous les éléments valides :");
        for (int i = 0; i < tableau.length; i++) {
            try {
                int element = accederElement(tableau, i);
                System.out.println("Index " + i + " : " + element);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Index " + i + " : " + e.getMessage());
            }
        }

        System.out.println("Affichage avec erreur :");
        for (int i = 0; i < tableau.length + 1; i++) {
            try {
                int element = accederElement(tableau, i);
                System.out.println("Index " + i + " : " + element);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Index " + i + " : " + e.getMessage());
            }
        }
    }

    public static int accederElement(int[] tableau, int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= tableau.length) {
            throw new IndexOutOfBoundsException("Index hors limites : " + index +
                    ". Les indices valides sont entre 0 et " + (tableau.length - 1));
        }
        return tableau[index];
    }

}