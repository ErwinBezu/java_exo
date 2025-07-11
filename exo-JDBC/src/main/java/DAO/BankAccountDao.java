package DAO;

import Config.DataBaseManager;
import Entities.BankAccount;
import Entities.Customer;
import Entities.Operation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankAccountDao {
    private Connection connection;
    private PreparedStatement stmt;
    private ResultSet rs;
    private String request;

    public BankAccount create (BankAccount bankAccount) {
        try {
            connection = DataBaseManager.getConnection();
            request = "INSERT INTO bank_accounts (customer_id, total_amount) VALUES (?, 0.0)";
            stmt = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1,bankAccount.getCustomerId());
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                bankAccount.setId(rs.getInt(1));
            }
            return bankAccount;
        } catch (SQLException e) {
            System.out.println("Error during saving customer : " + e.getMessage());
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean update (BankAccount bankAccount) {
        try {
            connection = DataBaseManager.getConnection();
            request = "UPDATE bank_accounts SET customer_id = ?, total_amount = ? WHERE id = ?";
            stmt = connection.prepareStatement(request);
            stmt.setInt(1,bankAccount.getCustomerId());
            stmt.setDouble(2,bankAccount.getTotalAmount());
            stmt.setInt(3,bankAccount.getId());
            int rows = stmt.executeUpdate();

            return rows == 1;

        }catch (SQLException e){
            System.out.println("Error during updating Bank Account : "+e.getMessage());
            return false;
        }finally {
            try{
                connection.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public BankAccount findById (int id){
        try {
            connection = DataBaseManager.getConnection();
            request = """
                SELECT 
                    ba.id as account_id,
                    ba.customer_id as account_customer_id,
                    ba.total_amount,
                    c.id as customer_id,
                    c.first_name,
                    c.last_name,
                    c.phone
                FROM bank_accounts ba
                JOIN customers c ON ba.customer_id = c.id
                WHERE ba.id = ?
            """;
            stmt = connection.prepareStatement(request);
            stmt.setInt(1,id);
            rs = stmt.executeQuery();

            if(rs.next()){
                Customer customer = Customer.builder()
                        .id(rs.getInt("customer_id"))
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .phone(rs.getString("phone"))
                        .build();

                OperationDao operationDao = new OperationDao();
                List<Operation> operations = operationDao.findByAccountId(id);

                return BankAccount.builder()
                        .id(rs.getInt("account_id"))
                        .customerId(rs.getInt("account_customer_id"))
                        .customer(customer)
                        .operations(operations)
                        .totalAmount(rs.getDouble("total_amount"))
                        .build();
            }

            return null;

        }catch (SQLException e){
            System.out.println("Error during getting bank Account : "+e.getMessage());
            return null;
        }finally {
            try{
                connection.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public List<BankAccount> findByCustomerId(int customerId) {
        try {
            connection = DataBaseManager.getConnection();
            List<BankAccount> accounts = new ArrayList<>();
            request = """
                SELECT 
                    ba.id as account_id,
                    ba.customer_id as account_customer_id,
                    ba.total_amount,
                    c.id as customer_id,
                    c.first_name,
                    c.last_name,
                    c.phone
                FROM bank_accounts ba
                JOIN customers c ON ba.customer_id = c.id
                WHERE ba.customer_id = ?
                ORDER BY ba.id
            """;
            stmt = connection.prepareStatement(request);
            stmt.setInt(1, customerId);
            rs = stmt.executeQuery();

            while(rs.next()){
                Customer customer = Customer.builder()
                        .id(rs.getInt("customer_id"))
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .phone(rs.getString("phone"))
                        .build();

                OperationDao operationDao = new OperationDao();
                List<Operation> operations = operationDao.findByAccountId(rs.getInt("account_id"));

                BankAccount account = BankAccount.builder()
                        .id(rs.getInt("account_id"))
                        .customerId(rs.getInt("account_customer_id"))
                        .customer(customer)
                        .operations(operations)
                        .totalAmount(rs.getDouble("total_amount"))
                        .build();

                accounts.add(account);
            }

            return accounts;

        }catch (SQLException e){
            System.out.println("Error during getting bank Accounts by customer : "+e.getMessage());
            return new ArrayList<>();
        }finally {
            try{
                connection.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
