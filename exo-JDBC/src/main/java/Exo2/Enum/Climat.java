package Exo2.Enum;

public enum Climat {
    TROPICAL("Tropical"),
    MEDITERRANEEN("Méditerranéen"),
    CONTINENTAL("Continental"),
    OCEANIQUE("Océanique"),
    DESERTIQUE("Désertique"),
    POLAIRE("Polaire"),
    MONTAGNARD("Montagnard");

    private final String label;

    Climat(String label) {
        this.label = label;
    }

}
