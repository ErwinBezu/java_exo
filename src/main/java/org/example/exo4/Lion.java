package org.example.exo4;

class Lion extends Animal {

    public Lion(String nom, int age) {
        super(nom, age);
    }

    @Override
    public void manger() {
        System.out.println(nom + " le lion mange de la viande");
    }

    @Override
    public void dormir() {
        System.out.println(nom + " le lion fait dodo");
    }

    @Override
    public void faireDuBruit() {
        System.out.println(nom + " le lion rugit miaou !");
    }

    @Override
    public String toString() {
        return "Lion - " + super.toString();
    }
}
