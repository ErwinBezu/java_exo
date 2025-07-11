package DAO;

import Config.DataBaseManager;
import Entities.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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

    public boolean update (Customer customer){
        try {
            connection = DataBaseManager.getConnection();
            request = "UPDATE customers set first_name = ?,last_name = ?, phone = ? where id = ?";
            stmt = connection.prepareStatement(request);
            stmt.setString(1,customer.getFirstName());
            stmt.setString(2,customer.getLastName());
            stmt.setString(3,customer.getPhone());
            stmt.setInt(4,customer.getId());
            int rows = stmt.executeUpdate();

            return rows == 1;

        }catch (SQLException e){
            System.out.println("Error during updating customer : "+e.getMessage());
            return false;
        }finally {
            try{
                connection.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public Boolean delete (int id){
        try {
            connection = DataBaseManager.getConnection();
            request = "DELETE  FROM customers where id = ?";
            stmt = connection.prepareStatement(request);
            stmt.setInt(1,id);
            int rows = stmt.executeUpdate();

            return rows == 1;

        }catch (SQLException e){
            System.out.println("Error during updating customer : "+e.getMessage());
            return false;
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

    public List<Customer> findAll(){
        try {
            connection = DataBaseManager.getConnection();
            List<Customer> customers = new ArrayList<>();
            request = "SELECT * FROM customers";
            stmt = connection.prepareStatement(request);
            rs = stmt.executeQuery();

            while(rs.next()){
                customers.add(Customer.builder()
                        .id(rs.getInt("id"))
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .phone(rs.getString("phone"))
                        .build());
            }

            return customers;

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
