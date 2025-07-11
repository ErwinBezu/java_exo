package IHM;

import Controllers.BankingController;
import java.util.Scanner;

public class ConsoleInterface {

    private final BankingController controller;
    private final Scanner scanner;

    public ConsoleInterface() {
        this.controller = new BankingController();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=== BANQUE === \n");

        boolean continuer = true;

        while (continuer) {
            afficherMenu();

            try {
                int choix = Integer.parseInt(scanner.nextLine());

                switch (choix) {
                    case 1 -> gererCreationClient();
                    case 2 -> gererDepot();
                    case 3 -> gererRetrait();
                    case 4 -> gererCreationCompteSupplementaire();
                    case 5 -> gererAffichageCompte();
                    case 6 -> gererAffichageProfilClient();
                    case 7 -> {
                        continuer = false;
                        System.out.println(" Merci d'avoir utilisé notre application bancaire !");
                    }
                    default -> System.out.println(" Choix invalide. Veuillez réessayer.");
                }

            } catch (NumberFormatException e) {
                System.out.println(" Veuillez entrer un nombre valide.");
            } catch (Exception e) {
                System.out.println(" Une erreur est survenue : " + e.getMessage());
            }
        }

        scanner.close();
    }

    private void afficherMenu() {
        System.out.println("\nMENU PRINCIPAL");
        System.out.println("""
                1. Créer un client et son compte
                2. Effectuer un dépot
                3. Effectuer un retrait
                4. Créer un compte supplémentaire pour un client
                5. Afficher un compte
                6. Afficher le profil complet d'un client
                7. Quitter
                """);
        System.out.print("\n Votre choix: ");
    }

    private void gererCreationClient() {
        System.out.println("\n === CREATION CLIENT & COMPTE ===");

        System.out.print("Prénom: ");
        String prenom = scanner.nextLine();

        System.out.print("Nom: ");
        String nom = scanner.nextLine();

        System.out.print("Téléphone: ");
        String telephone = scanner.nextLine();

        String resultat = controller.createCustomerWithAccount(prenom, nom, telephone);
        System.out.println(resultat);
    }

    private void gererDepot() {
        System.out.println("\n === DEPOT D'ARGENT ===");

        try {
            System.out.print("ID du client: ");
            int customerId = Integer.parseInt(scanner.nextLine());

            String accountsList = controller.listCustomerAccounts(customerId);
            if (accountsList == null) {
                System.out.println("Client non trouvé avec l'ID: " + customerId);
                return;
            }

            System.out.println(accountsList);

            System.out.print("Sélectionnez le numéro du compte (ex: 1, 2, 3...): ");
            int selection = Integer.parseInt(scanner.nextLine());

            int accountId = controller.getAccountIdBySelection(customerId, selection);
            if (accountId == -1) {
                System.out.println("Sélection de compte invalide.");
                return;
            }

            System.out.print("Montant à déposer: ");
            double montant = Double.parseDouble(scanner.nextLine());

            String resultat = controller.makeDeposit(accountId, montant);
            System.out.println(resultat);

        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrer des valeurs numériques valides.");
        }
    }

    private void gererRetrait() {
        System.out.println("\n=== RETRAIT D'ARGENT ===");

        try {
            System.out.print("ID du client: ");
            int customerId = Integer.parseInt(scanner.nextLine());

            String accountsList = controller.listCustomerAccounts(customerId);
            if (accountsList == null) {
                System.out.println("Client non trouvé avec l'ID: " + customerId);
                return;
            }

            System.out.println(accountsList);

            System.out.print("Sélectionnez le numéro du compte (ex: 1, 2, 3...): ");
            int selection = Integer.parseInt(scanner.nextLine());

            int accountId = controller.getAccountIdBySelection(customerId, selection);
            if (accountId == -1) {
                System.out.println("Sélection de compte invalide.");
                return;
            }

            System.out.print("Montant à retirer: ");
            double montant = Double.parseDouble(scanner.nextLine());

            String resultat = controller.makeWithdrawal(accountId, montant);
            System.out.println(resultat);

        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrer des valeurs numériques valides.");
        }
    }

    private void gererAffichageCompte() {
        System.out.println("\n=== AFFICHAGE COMPTE ===");

        try {
            System.out.print("ID du compte: ");
            int accountId = Integer.parseInt(scanner.nextLine());

            String details = controller.getAccountDetails(accountId);
            System.out.println(details);

        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrer un ID de compte valide.");
        }
    }

    private void gererCreationCompteSupplementaire() {
        System.out.println("\n=== CREATION COMPTE SUPPLEMENTAIRE ===");

        try {
            System.out.print("ID du client: ");
            int customerId = Integer.parseInt(scanner.nextLine());

            String resultat = controller.createAdditionalAccount(customerId);
            System.out.println(resultat);

        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrer un ID de client valide.");
        }
    }

    private void gererAffichageProfilClient() {
        System.out.println("\n=== PROFIL CLIENT ===");

        try {
            System.out.print("ID du client: ");
            int customerId = Integer.parseInt(scanner.nextLine());

            String details = controller.getCustomerAccountsDetails(customerId);
            System.out.println(details);

        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrer un ID de client valide.");
        }
    }

}