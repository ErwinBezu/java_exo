package org.example.exo3;

public class Compte {
    private String numeroCompte;
    private String proprietaire;
    private double solde;

    public Compte() {}

    public Compte(String numeroCompte, String proprietaire, double solde) {
        this.numeroCompte = numeroCompte;
        this.proprietaire = proprietaire;
        this.solde = solde;
    }

    public boolean debiter(double montant) {
        if (montant <= 0) {
            return false;
        }
        if (solde >= montant) {
            solde -= montant;
            return true;
        }
        return false;
    }

    public void crediter(double montant) {
        if (montant > 0) {
            solde += montant;
        }
    }

    public String getNumeroCompte() { return numeroCompte; }
    public String getProprietaire() { return proprietaire; }
    public double getSolde() { return solde; }

    public void setNumeroCompte(String numeroCompte) { this.numeroCompte = numeroCompte; }
    public void setProprietaire(String proprietaire) { this.proprietaire = proprietaire; }
    public void setSolde(double solde) { this.solde = solde; }
}