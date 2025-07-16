package Exo2.Enum;

public enum Category {
    BIRD("Oiseau"),
    MAMMAL("Mammifère"),
    INSECT("Insecte"),
    PLANT("Plante"),
    OTHER("Autre");

    private final String label;

    Category(String label) {
        this.label = label;
    }
}
