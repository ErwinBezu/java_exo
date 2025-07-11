package DAO;

import Config.DataBaseManager;
import Entities.Customer;
import Entities.Operation;
import Enums.OperationStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperationDao {
    private Connection connection;
    private PreparedStatement stmt;
    private ResultSet rs;
    private String request;

    public Operation create (Operation operation){
        try {
            connection = DataBaseManager.getConnection();
            request = "INSERT INTO operations (amount, status, account_id) VALUES (?, ?, ?)";
            stmt = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            stmt.setDouble(1, operation.getAmount());
            stmt.setString(2, operation.getStatus().name());
            stmt.setInt(3, operation.getAccountId());
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                operation.setId(rs.getInt(1));
            }
            return operation;
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

    public List<Operation> findByAccountId(int account_id){
        try {
            connection = DataBaseManager.getConnection();
            List<Operation> operations = new ArrayList<>();
            request = "SELECT * FROM operations WHERE account_id = ? ORDER BY operation_date DESC";
            stmt = connection.prepareStatement(request);
            stmt.setInt(1, account_id);
            rs = stmt.executeQuery();

            while(rs.next()){
                operations.add(Operation.builder()
                        .id(rs.getInt("id"))
                        .amount(rs.getDouble("amount"))
                        .status(OperationStatus.valueOf(rs.getString("status")))
                        .accountId(rs.getInt("account_id"))
                        .operationDate(rs.getTimestamp("operation_date"))
                        .build());
            }

            return operations;

        }catch (SQLException e){
            System.out.println("Error during updating customer : "+e.getMessage());
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
