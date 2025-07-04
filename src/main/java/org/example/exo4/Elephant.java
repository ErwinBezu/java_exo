package org.example.exo4;

class Elephant extends Animal {
    private double poids;

    public Elephant(String nom, int age, double poids) {
        super(nom, age);
        this.poids = poids;
    }

    @Override
    public void manger() {
        System.out.println(nom + " l'éléphant mange des végétaux");
    }

    @Override
    public void dormir() {
        System.out.println(nom + " l'éléphant dort");
    }

    @Override
    public void faireDuBruit() {
        System.out.println(nom + " l'éléphant barrit rourou");
    }

    public double getPoids() {
        return poids;
    }

    @Override
    public String toString() {
        return "Éléphant - " + super.toString() + ", Poids: " + poids + " kg";
    }
}