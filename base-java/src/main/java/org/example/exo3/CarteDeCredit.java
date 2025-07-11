package org.example.exo3;

import java.time.YearMonth;

public class CarteDeCredit implements Paiement {
    private String numeroCarte;
    private String titulaire;
    private YearMonth expiration;
    private String codeCvv;
    private Compte compte;

    public CarteDeCredit() {}

    public CarteDeCredit(String numeroCarte, String titulaire, YearMonth expiration, String codeCvv) {
        this.numeroCarte = numeroCarte;
        this.titulaire = titulaire;
        this.expiration = expiration;
        this.codeCvv = codeCvv;
    }

    @Override
    public void lierCompte(Compte compte) {
        this.compte = compte;
    }

    @Override
    public Compte getCompte() {
        return compte;
    }

    @Override
    public String effectuerPaiement(double montant) {
        if (montant <= 0) {
            return "Montant invalide";
        }

        if (numeroCarte == null || numeroCarte.isEmpty()) {
            return "Numéro de carte manquant";
        }

        if (codeCvv == null || codeCvv.isEmpty()) {
            return "Code CVV manquant";
        }

        YearMonth now = YearMonth.now();
        if (expiration.isBefore(now)) {
            return "carte expirée";
        }

        if (compte == null) {
            return "Aucun compte lié à cette carte";
        }

        if (compte.getSolde() < montant) {
            return "Solde insuffisant. Solde disponible: " + compte.getSolde() + " euros";
        }

        if (compte.debiter(montant)) {
            return "Paiement de " + montant + " euros effectué avec la CB de " + titulaire +
                    ". Nouveau solde: " + compte.getSolde() + " euros";
        } else {
            return "Échec du débit du compte";
        }
    }
}
