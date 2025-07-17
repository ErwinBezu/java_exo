package com.magasin.Enum;

public enum StatusSale {
    EN_COURS("En cours"),
    FINALISEE("Finalisée"),
    ANNULEE("Annulée");

    private final String libelle;

    StatusSale(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
