package org.example.classe;

public class Commercial extends Salarie {
    private double chiffreAffaire;
    private double commissionPourcentage;

    public Commercial() {
        super();
    }

    public Commercial(String matricule, String service, String categorie, String nom, double salaire, double chiffreAffaire, double commissionPourcentage) {
        super(matricule, service, categorie, nom, salaire);
        this.chiffreAffaire = chiffreAffaire;
        this.commissionPourcentage = commissionPourcentage;
    }

    @Override
    public void afficherSalaire() {
        double salaireTotal = salaire + (chiffreAffaire * commissionPourcentage / 100);
        System.out.println(nom + " (Commercial) gagne : " + salaireTotal + " €");
    }

    @Override
    public String toString() {
        return "Commercial : " + nom + " | Matricule: " + matricule + " | Service: " + service +
                " | Catégorie: " + categorie + " | Salaire fixe: " + salaire + " € | CA: " +
                chiffreAffaire + " € | Commission: " + commissionPourcentage + "%";
    }
}