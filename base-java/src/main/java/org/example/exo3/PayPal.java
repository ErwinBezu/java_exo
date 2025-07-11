package org.example.exo3;

public class PayPal implements Paiement {
    private String mail;
    private String password;
    private Compte compte;

    public PayPal() {}
    public PayPal(String mail, String password) {
        this.mail = mail;
        this.password = password;
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

        if (mail == null || mail.isEmpty()) {
            return "Adresse email manquante";
        }

        if (password == null || password.isEmpty()) {
            return "Mot de passe manquant";
        }
        if (compte == null) {
            return "Aucun compte lié à ce PayPal";
        }

        if (compte.getSolde() < montant) {
            return "Solde insuffisant. Solde disponible: " + compte.getSolde() + " euros";
        }

        if (compte.debiter(montant)) {
            return "Paiement de " + montant + " euros effectué avec le PayPal de " + mail +
                    ". Nouveau solde: " + compte.getSolde() + " euros";
        } else {
            return "Échec du débit du compte";
        }
    }
}
