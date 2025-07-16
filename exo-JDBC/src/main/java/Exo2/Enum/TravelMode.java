package Exo2.Enum;

public enum TravelMode {
    WALKING("Marche"),
    BIKE("Vélo"),
    CAR("Voiture"),
    BUS("Bus"),
    TRAIN("Train"),
    PLANE("Avion");

    private final String label;

    TravelMode(String label) {
        this.label = label;
    }
}
