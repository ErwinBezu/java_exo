package Exo1.Enums;

public enum OperationStatus {
    DEPOSIT("DÉPÔT"),
    WITHDRAWAL("RETRAIT");

    private final String label;

    OperationStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}