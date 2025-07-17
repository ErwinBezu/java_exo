package com.magasin.Enum;

public enum CategorieMode {
    HOMME("Homme"),
    FEMME("Femme"),
    ENFANT("Enfant");

    private final String libelle;

    CategorieMode(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
