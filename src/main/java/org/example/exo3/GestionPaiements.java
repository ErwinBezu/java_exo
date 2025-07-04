package org.example.exo3;

import java.time.YearMonth;

public class GestionPaiements {
    public static void main(String[] args) {
        Compte compteBancaire = new Compte("FR7612345678147442377", "José Bové", 500.00);
        Compte comptePayPal = new Compte("PP123456789", "José Bové", 200.00);

        CarteDeCredit carteCredit = new CarteDeCredit(
                "1234567890123456",
                "José Bové",
                YearMonth.of(2025, 12),
                "007"
        );

        PayPal paypal = new PayPal(
                "josebove@email.com",
                "motdepasse123"
        );

        carteCredit.lierCompte(compteBancaire);
        paypal.lierCompte(comptePayPal);

        String resultatCarte = carteCredit.effectuerPaiement(150.50);
        System.out.println("Résultat: " + resultatCarte);

        String resultatPayPal = paypal.effectuerPaiement(75.25);
        System.out.println("Résultat: " + resultatPayPal);

        String resultatInsuffisant = carteCredit.effectuerPaiement(500.00);
        System.out.println("Résultat: " + resultatInsuffisant);

        String resultatPayPalInsuffisant = paypal.effectuerPaiement(200.00);
        System.out.println("Résultat: " + resultatPayPalInsuffisant);

    }
}
