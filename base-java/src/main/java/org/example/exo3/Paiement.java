package org.example.exo3;

public interface Paiement {
    String effectuerPaiement(double montant);
    void lierCompte(Compte compte);
    Compte getCompte();
}
