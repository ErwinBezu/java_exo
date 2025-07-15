package Exo1.DAO;

import Exo1.Config.DataBaseManager;
import Exo1.Entities.Customer;

import java.sql.*;


public class CustomerDao {
    private Connection connection;
    private PreparedStatement stmt;
    private ResultSet rs;
    private String request;

    public Customer create  (Customer customer) {
        try {
            connection = DataBaseManager.getConnection();
            request = "INSERT INTO customers (first_name,last_name,phone) values (?,?,?)";
            stmt = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,customer.getFirstName());
            stmt.setString(2,customer.getLastName());
            stmt.setString(3,customer.getPhone());
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();

            if(rs.next()){
                customer.setId(rs.getInt(1));
            }
            return customer;

        }catch (SQLException e){
            System.out.println("Error during saving customer : "+e.getMessage());
            return null;
        }finally {
            try{
                connection.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public Customer findById (int id){
        try {
            connection = DataBaseManager.getConnection();
            request = "SELECT * FROM customers where id = ?";
            stmt = connection.prepareStatement(request);
            stmt.setInt(1,id);
            rs = stmt.executeQuery();

            if(rs.next()){
                return Customer.builder()
                        .id(rs.getInt("id"))
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .phone(rs.getString("phone"))
                        .build();
            }

            return null;

        }catch (SQLException e){
            System.out.println("Error during updating customer : "+e.getMessage());
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
