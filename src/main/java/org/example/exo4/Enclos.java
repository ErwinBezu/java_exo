package org.example.exo4;

import java.util.ArrayList;
import java.util.List;

public class Enclos<T> implements IEnclos<T> {
    private List<T> animaux;

    public Enclos() {
        animaux = new ArrayList<>();
    }

    @Override
    public void ajouterAnimal(T animal) {
        animaux.add(animal);
    }

    @Override
    public void afficherAnimaux() {
        for (T a : animaux) {
            System.out.println(a);
        }
    }

    public String toString() {
        if (animaux.isEmpty()) return "Enclos vide";

        return "Enclos de " + animaux.get(0).getClass().getSimpleName() + "(" + animaux.size() + ")";
    }
}

