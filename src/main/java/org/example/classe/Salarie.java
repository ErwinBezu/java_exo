package org.example.classe;

public class Salarie {
    protected String matricule;
    protected String service;
    protected String categorie;
    protected String nom;
    protected double salaire;

    protected static int totalEmployes = 0;
    protected static double salaireTotal = 0;

    public Salarie(){
    }

    public Salarie (String matricule, String service, String categorie, String nom, double salaire){
        this.matricule = matricule;
        this.service = service;
        this.categorie = categorie;
        this.nom = nom;
        this.salaire = salaire;

        totalEmployes++;
        salaireTotal += salaire;
    }

    public void afficherSalaire() {
        System.out.println("Le salaire de " + nom + " est de " + salaire + " euros");
    }

    public static int getTotalEmployes() {
        return totalEmployes;
    }

    public static double getSalaireTotal() {
        return salaireTotal;
    }

    public static void resetEmployes() {
        totalEmployes = 0;
        salaireTotal = 0;
    }

    @Override
    public String toString() {
        return "Salarie : " + nom + " | Matricule: " + matricule + " | Service: " + service +
                " | Catégorie: " + categorie + " | Salaire: " + salaire + " €";
    }
}
