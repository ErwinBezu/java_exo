package org.example.exo4;

public class Zoo {
    public static void main(String[] args) {
        Lion lion1 = new Lion("RonRon", 5);
        Lion lion2 = new Lion("PatPat", 4);
        Elephant elephant1 = new Elephant("Loxodon", 10, 5400);
        Elephant elephant2 = new Elephant("Oliphant", 12, 6000);

        EnclosDesLions enclosLions = new EnclosDesLions();
        EnclosDesElephants enclosElephants = new EnclosDesElephants();

        enclosLions.ajouterAnimal(lion1);
        enclosLions.ajouterAnimal(lion2);
        enclosElephants.ajouterAnimal(elephant1);
        enclosElephants.ajouterAnimal(elephant2);

        enclosLions.afficherAnimaux();
        enclosElephants.afficherAnimaux();
    }
}
