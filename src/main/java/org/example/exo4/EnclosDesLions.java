package org.example.exo4;

import java.util.ArrayList;
import java.util.List;

public class EnclosDesLions implements Enclos {
    private List<Lion> lions = new ArrayList<>();

    @Override
    public void ajouterAnimal(Animal animal) {
        if (animal instanceof Lion) {
            lions.add((Lion) animal);
        } else {
            System.out.println("Seuls les lions peuvent être ajoutés à cet enclos.");
        }
    }

    @Override
    public void afficherAnimaux() {
        System.out.println("Enclos des lions :");
        for (Lion lion : lions) {
            System.out.println(lion);
        }
    }
}