package Controllers;

import Service.BankingService;
import Entities.BankAccount;
import Entities.Customer;
import Entities.Operation;
import Enums.OperationStatus;

public class BankingController {

    private final BankingService bankingService;

    public BankingController() {
        this.bankingService = new BankingService();
    }

    public String createCustomerWithAccount(String firstName, String lastName, String phone) {
        if (firstName == null || firstName.trim().isEmpty()) {
            return "Le prénom est obligatoire";
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            return "Le nom est obligatoire";
        }
        if (phone == null || phone.trim().isEmpty()) {
            return "Le téléphone est obligatoire";
        }

        int accountId = bankingService.createCustomerWithAccount(firstName, lastName, phone);

        if (accountId > 0) {
            return "Client créé avec succès !\n Numéro de compte: " + accountId;
        } else {
            return "Erreur lors de la création du client/compte";
        }
    }

    public String makeDeposit(int accountId, double amount) {
        if (amount <= 0) {
            return "Le montant doit être positif";
        }

        if (!bankingService.accountExists(accountId)) {
            return "Compte " + accountId + " non trouvé";
        }

        boolean success = bankingService.deposit(accountId, amount);

        if (success) {
            double newBalance = bankingService.getAccountBalance(accountId);
            return String.format("Dépôt de %.2f € effectué avec succès !\n Nouveau solde: %.2f €",
                    amount, newBalance);
        } else {
            return "Erreur lors du dépôt";
        }
    }

    public String makeWithdrawal(int accountId, double amount) {
        if (amount <= 0) {
            return "Le montant doit être positif";
        }

        if (!bankingService.accountExists(accountId)) {
            return "Compte " + accountId + " non trouvé";
        }

        if (!bankingService.canWithdraw(accountId, amount)) {
            double balance = bankingService.getAccountBalance(accountId);
            return String.format("Solde insuffisant\n Solde actuel: %.2f €\n Retrait demandé: %.2f €",
                    balance, amount);
        }

        boolean success = bankingService.withdraw(accountId, amount);

        if (success) {
            double newBalance = bankingService.getAccountBalance(accountId);
            return String.format("Retrait de %.2f € effectué avec succès !\n Nouveau solde: %.2f €",
                    amount, newBalance);
        } else {
            return "Erreur lors du retrait";
        }
    }

    public String getAccountDetails(int accountId) {
        BankAccount account = bankingService.getAccountById(accountId);

        if (account == null) {
            return "Compte non trouvé avec l'ID: " + accountId;
        }

        Customer customer = bankingService.getCustomerById(account.getCustomerId());

        if (customer == null) {
            return "Client associé non trouvé pour le compte: " + accountId;
        }

        String details =
                "DETAILS DU COMPTE BANCAIRE\n" +
                        "ID Compte      : " + account.getId() + "\n" +
                        " Titulaire      : " + customer.getFullName() + "\n" +
                        "Téléphone      : " + customer.getPhone() + "\n" +
                        "Solde actuel   : " + String.format("%.2f €", account.getTotalAmount()) + "\n" +
                        "Solde calculé  : " + String.format("%.2f €", account.calculateBalance()) + "\n" +
                        "\nHISTORIQUE DES OPÉRATIONS:\n";


        if (account.getOperations().isEmpty()) {
            details += "   Aucune opération effectuée\n";
        } else {
            for (Operation op : account.getOperations()) {
                String type = op.getStatus() == OperationStatus.DEPOSIT ? " DÉPÔT" : " RETRAIT";
                details += String.format("   %s | %.2f € | %s%n", type, op.getAmount(), op.getOperationDate());
            }
        }
        return details;
    }
}