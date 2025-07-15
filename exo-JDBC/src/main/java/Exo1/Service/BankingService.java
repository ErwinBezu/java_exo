package Exo1.Service;

import Exo1.DAO.BankAccountDao;
import Exo1.DAO.CustomerDao;
import Exo1.DAO.OperationDao;
import Exo1.Entities.BankAccount;
import Exo1.Entities.Customer;
import Exo1.Entities.Operation;
import Exo1.Enums.OperationStatus;

import java.util.List;

public class BankingService {

    private final CustomerDao customerDao;
    private final BankAccountDao accountDao;
    private final OperationDao operationDao;

    public BankingService() {
        this.customerDao = new CustomerDao();
        this.accountDao = new BankAccountDao();
        this.operationDao = new OperationDao();
    }

    public int createCustomerWithAccount(String firstName, String lastName, String phone) {
        try {
            if (firstName == null || firstName.trim().isEmpty() ||
                    lastName == null || lastName.trim().isEmpty() ||
                    phone == null || phone.trim().isEmpty()) {
                return -1;
            }
            Customer customer = Customer.builder()
                    .firstName(firstName.trim())
                    .lastName(lastName.trim())
                    .phone(phone.trim())
                    .build();

            Customer savedCustomer = customerDao.create(customer);

            if (savedCustomer != null && savedCustomer.getId() > 0) {
                BankAccount account = BankAccount.builder()
                        .customerId(savedCustomer.getId())
                        .totalAmount(0.0)
                        .build();

                BankAccount savedAccount = accountDao.create(account);

                if (savedAccount != null) {
                    return savedAccount.getId();
                }
            }

        } catch (Exception e) {
            return -1;
        }

        return -1;
    }

    public int createAdditionalAccount(int customerId) {
        try {
            Customer customer = customerDao.findById(customerId);
            if (customer == null) {
                return -1;
            }
            BankAccount account = BankAccount.builder()
                    .customerId(customerId)
                    .totalAmount(0.0)
                    .build();

            BankAccount savedAccount = accountDao.create(account);
            return savedAccount != null ? savedAccount.getId() : -1;

        } catch (Exception e) {
            return -1;
        }
    }


    public boolean deposit(int accountId, double amount) {
        if (amount <= 0) {
            return false;
        }

        try {
            BankAccount account = accountDao.findById(accountId);

            if (account == null) {
                return false;
            }

            Operation operation = Operation.builder()
                    .amount(amount)
                    .status(OperationStatus.DEPOSIT)
                    .accountId(accountId)
                    .build();

            Operation savedOperation = operationDao.create(operation);

            if (savedOperation != null) {
                double newBalance = account.getTotalAmount() + amount;
                account.setTotalAmount(newBalance);
                return accountDao.update(account);
            }

        } catch (Exception e) {
            return false;
        }

        return false;
    }

    public boolean withdraw(int accountId, double amount) {
        if (amount <= 0) {
            return false;
        }

        try {
            BankAccount account = accountDao.findById(accountId);

            if (account == null) {
                return false;
            }

            if (account.getTotalAmount() < amount) {
                return false;
            }

            Operation operation = Operation.builder()
                    .amount(amount)
                    .status(OperationStatus.WITHDRAWAL)
                    .accountId(accountId)
                    .build();

            Operation savedOperation = operationDao.create(operation);

            if (savedOperation != null) {
                double newBalance = account.getTotalAmount() - amount;
                account.setTotalAmount(newBalance);
                return accountDao.update(account);
            }

        } catch (Exception e) {
            return false;
        }

        return false;
    }

    public List<BankAccount> getAccountsByCustomerId(int customerId) {
        try {
            return accountDao.findByCustomerId(customerId);
        } catch (Exception e) {
            return List.of();
        }
    }

    public BankAccount getAccountById(int accountId) {
        try {
            return accountDao.findById(accountId);
        } catch (Exception e) {
            return null;
        }
    }

    public Customer getCustomerById(int customerId) {
        try {
            return customerDao.findById(customerId);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean accountExists(int accountId) {
        return getAccountById(accountId) != null;
    }

    public boolean customerExists(int customerId) {
        return getCustomerById(customerId) != null;
    }

    public double getAccountBalance(int accountId) {
        BankAccount account = getAccountById(accountId);
        return account != null ? account.getTotalAmount() : -1;
    }

    public boolean canWithdraw(int accountId, double amount) {
        if (amount <= 0) return false;

        BankAccount account = getAccountById(accountId);
        return account != null && account.getTotalAmount() >= amount;
    }
}