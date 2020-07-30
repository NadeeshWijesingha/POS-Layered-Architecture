package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.OrderDAO;
import db.DBConnection;
import entity.Order;

public class OrderDAOImpl implements OrderDAO {

  public List<Object> findAll() {
    try {
      Connection connection = DBConnection.getInstance().getConnection();
      Statement stm = connection.createStatement();
      ResultSet rst = stm.executeQuery("SELECT * FROM `Order`");
      List<Object> orders = new ArrayList<>();
      while (rst.next()) {
        orders.add(new Order(rst.getString(1),
            rst.getDate(2),
            rst.getString(3)));
      }
      return orders;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return null;
    }
  }

  public Order find(Object key) {
    try {
      Connection connection = DBConnection.getInstance().getConnection();
      PreparedStatement pstm = connection.prepareStatement("SELECT * FROM `Order` WHERE id=?");
      pstm.setObject(1, key);
      ResultSet rst = pstm.executeQuery();
      if (rst.next()) {
        return new Order(rst.getString(1),
            rst.getDate(2),
            rst.getString(3));
      }
      return null;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return null;
    }
  }

  public boolean save(Object entity) {
    try {
      Connection connection = DBConnection.getInstance().getConnection();
      Order order = (Order) entity;
      PreparedStatement pstm = connection.prepareStatement("INSERT INTO `Order` VALUES (?,?,?)");
      pstm.setObject(1, order.getId());
      pstm.setObject(2, order.getDate());
      pstm.setObject(3, order.getCustomerId());
      return pstm.executeUpdate() > 0;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return false;
    }
  }

  public boolean update(Object entity) {
    try {
      Connection connection = DBConnection.getInstance().getConnection();
      Order order = (Order) entity;
      PreparedStatement pstm = connection.prepareStatement("UPDATE Order SET date=?, customerId=? WHERE id=?");
      pstm.setObject(3, order.getId());
      pstm.setObject(1, order.getDate());
      pstm.setObject(2, order.getCustomerId());
      return pstm.executeUpdate() > 0;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return false;
    }
  }

  public boolean delete(Object key) {
    try {
      Connection connection = DBConnection.getInstance().getConnection();
      PreparedStatement pstm = connection.prepareStatement("DELETE FROM Order WHERE code=?");
      pstm.setObject(1, key);
      return pstm.executeUpdate() > 0;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return false;
    }
  }

  public String getLastOrderId(){

    try {
      Connection connection = DBConnection.getInstance().getConnection();
      Statement stm = connection.createStatement();
      ResultSet rst = stm.executeQuery("SELECT * FROM `Order` ORDER BY id DESC LIMIT 1");
      if (!rst.next()){
        return null;
      }else{
        return rst.getString(1);
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return null;

  }

  }
  
}
