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
            request = "SELECT * FROM bank_accounts WHERE id = ?";
            stmt = connection.prepareStatement(request);
            stmt.setInt(1,id);
            rs = stmt.executeQuery();

            if(rs.next()){
                OperationDao operationDao = new OperationDao();
                List<Operation> operations = operationDao.findByAccountId(id);

                return BankAccount.builder()
                        .id(rs.getInt("id"))
                        .customerId(rs.getInt("customer_id"))
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

}
