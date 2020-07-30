package dao.custom.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dao.custom.QueryDAO;
import db.DBConnection;
import entity.CustomEntity;

public class QueryDAOImpl implements QueryDAO {

  Connection connection = DBConnection.getInstance().getConnection();

  @Override
  public CustomEntity getOrderDetail(String orderId) {

    try {
      PreparedStatement pstm = connection.prepareStatement(
          "SELECT o.id, c.name, o.date FROM `order` o INNER JOIN customer c ON o.customerId = c.id WHERE o.id =?");
      pstm.setObject(1, orderId);
      ResultSet rst = pstm.executeQuery();
      if (rst.next()) {
        return new CustomEntity(rst.getString(1), rst.getString(2), rst.getString(3));
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return null;
  }

  @Override
  public CustomEntity getOrderDetail2(String orderId) {
    try {
      PreparedStatement pstm = connection.prepareStatement(
          "SELECT c.id, c.name, o.id FROM `order` o INNER JOIN customer c ON o.customerId = c.id WHERE o.id = ?");
      pstm.setObject(1, orderId);
      ResultSet rst = pstm.executeQuery();
      if (rst.next()) {
        return new CustomEntity(rst.getString(1), rst.getString(2), rst.getString(3));
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return null;
  }
}
