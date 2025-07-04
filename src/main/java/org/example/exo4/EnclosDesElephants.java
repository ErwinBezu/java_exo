package org.example.exo4;

import java.util.ArrayList;
import java.util.List;

public class EnclosDesElephants implements Enclos {
    private List<Elephant> elephants = new ArrayList<>();

    @Override
    public void ajouterAnimal(Animal animal) {
        if (animal instanceof Elephant) {
            elephants.add((Elephant) animal);
        } else {
            System.out.println("Seuls les éléphants peuvent être ajoutés à cet enclos.");
        }
    }

    @Override
    public void afficherAnimaux() {
        System.out.println("Enclos des éléphants :");
        for (Elephant elephant : elephants) {
            System.out.println(elephant);
        }
    }
}

