package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import entity.Customer;

public class CustomerDAO {

  public static List<Customer> findAllCustomers() {

    Connection connection = DBConnection.getInstance().getConnection();
    try {

      Statement stm = connection.createStatement();
      ResultSet rst = stm.executeQuery("SELECT * FROM Customer");
      ArrayList<Customer> customer = new ArrayList<>();
      while (rst.next()) {
        customer.add(new Customer(rst.getString(1), rst.getString(2), rst.getString(3)));
      }

      return customer;

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return null;
    }

  }

  public static Customer findCustomer(String customerId) {

    Connection connection = DBConnection.getInstance().getConnection();
    try {

      PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Customer WHERE id=?");
      pstm.setObject(1,customerId);
      ResultSet rst = pstm.executeQuery();
      if (rst.next()){
        return new Customer(rst.getString(1),rst.getString(2),rst.getString(2));
      }

      return null;

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return null;
    }

  }

  public static boolean saveCustomer(Customer customer) {

    Connection connection = DBConnection.getInstance().getConnection();
    try {

      PreparedStatement pstm = connection
          .prepareStatement("INSERT INTO Customer VALUES (?,?,?)");
      pstm.setObject(1, customer.getId());
      pstm.setObject(2, customer.getName());
      pstm.setObject(3, customer.getAddress());

      return pstm.executeUpdate() > 0;



    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return false;
    }

  }

  public static boolean updateCustomer(Customer customer) {

    Connection connection = DBConnection.getInstance().getConnection();
    try {

      PreparedStatement pstm = connection
          .prepareStatement("UPDATE Customer SET name=?, address=? WHERE id=?");
      pstm.setObject(1, customer.getId());
      pstm.setObject(2, customer.getName());
      pstm.setObject(3, customer.getAddress());

      return pstm.executeUpdate() > 0;



    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return false;
    }

  }

  public static boolean deleteCustomer(String customerId) {

    Connection connection = DBConnection.getInstance().getConnection();
    try {

      PreparedStatement pstm = connection
          .prepareStatement("DELETE FROM Customer WHERE id=?");
      pstm.setObject(1,customerId);

      return pstm.executeUpdate() > 0;

    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return false;
    }

  }

}
