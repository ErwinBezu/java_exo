package Exo1.Controllers;

import Exo1.Service.BankingService;
import Exo1.Entities.BankAccount;
import Exo1.Entities.Customer;
import Exo1.Entities.Operation;
import Exo1.Enums.OperationStatus;

import java.util.List;

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

    public String createAdditionalAccount(int customerId) {
        if (!bankingService.customerExists(customerId)) {
            return "Client avec l'ID " + customerId + " non trouvé";
        }

        int accountId = bankingService.createAdditionalAccount(customerId);

        if (accountId > 0) {
            return "Nouveau compte créé avec succès !\n Numéro de compte: " + accountId;
        } else {
            return "Erreur lors de la création du compte";
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

    public String listCustomerAccounts(int customerId) {
        Customer customer = bankingService.getCustomerById(customerId);

        if (customer == null) {
            return null;
        }

        List<BankAccount> accounts = bankingService.getAccountsByCustomerId(customerId);

        if (accounts.isEmpty()) {
            return "Aucun compte trouvé pour le client: " + customer.getFullName();
        }

        String accountList = "COMPTES DISPONIBLES POUR " + customer.getFullName() + ":\n";

        for (int i = 0; i < accounts.size(); i++) {
            BankAccount account = accounts.get(i);
            accountList += String.format("  %d. Compte ID: %d - Solde: %.2f €\n",
                    i + 1, account.getId(), account.getTotalAmount());
        }

        return accountList;
    }

    public int getAccountIdBySelection(int customerId, int selection) {
        List<BankAccount> accounts = bankingService.getAccountsByCustomerId(customerId);

        if (selection < 1 || selection > accounts.size()) {
            return -1;
        }

        return accounts.get(selection - 1).getId();
    }

    public String getCustomerAccountsDetails(int customerId) {
        Customer customer = bankingService.getCustomerById(customerId);

        if (customer == null) {
            return "Client non trouvé avec l'ID: " + customerId;
        }

        List<BankAccount> accounts = bankingService.getAccountsByCustomerId(customerId);

        if (accounts.isEmpty()) {
            return "Aucun compte trouvé pour le client: " + customer.getFullName();
        }

        String details =
            "PROFIL CLIENT\n" +
            "ID Client      : " + customer.getId() + "\n" +
            "Nom complet    : " + customer.getFullName() + "\n" +
            "Téléphone      : " + customer.getPhone() + "\n" +
            "Nombre de comptes: " + accounts.size() + "\n";

        double totalBalance = 0.0;
        for (BankAccount account : accounts) {
            totalBalance += account.getTotalAmount();
        }
        details += "Solde total    : " + String.format("%.2f €", totalBalance) + "\n";

        details += "\n=== LISTE DES COMPTES ===\n";

        for (int i = 0; i < accounts.size(); i++) {
            BankAccount account = accounts.get(i);
            details += "\nCOMPTE #" + (i + 1) + "\n";
            details += "ID Compte      : " + account.getId() + "\n";
            details += "Solde          : " + String.format("%.2f €", account.getTotalAmount()) + "\n";
            details += "Nb opérations  : " + account.getOperations().size() + "\n";

            if (!account.getOperations().isEmpty()) {
                Operation lastOp = account.getOperations().get(0);
                String type = lastOp.getStatus() == OperationStatus.DEPOSIT ? "DEPOT" : "RETRAIT";
                details += "Dernière opération: " + String.format("%s de %.2f € le %s", type, lastOp.getAmount(), lastOp.getOperationDate());
            } else {
                details += "Dernière opération: Aucune opération";
            }

            details += "\n";
        }

        return details;
    }
}