package dao.custom.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import dao.CrudUtil;
import dao.custom.QueryDAO;
import db.DBConnection;
import entity.CustomEntity;

public class QueryDAOImpl implements QueryDAO {

  Connection connection = DBConnection.getInstance().getConnection();

  @Override
  public CustomEntity getOrderDetail(String orderId) throws Exception {

      ResultSet rst = CrudUtil.execute("SELECT o.id, c.name, o.date FROM `order` o INNER JOIN customer c ON o.customerId = c.id WHERE o.id =?", orderId);
      if (rst.next()) {
        return new CustomEntity(rst.getString(1), rst.getString(2), rst.getDate(3));
      }
    return null;
  }

  @Override
  public CustomEntity getOrderDetail2(String orderId) throws Exception {
      ResultSet rst = CrudUtil.execute("SELECT c.id, c.name, o.id FROM `order` o INNER JOIN customer c ON o.customerId = c.id WHERE o.id = ?", orderId);
      if (rst.next()) {
        return new CustomEntity(rst.getString(1), rst.getString(2), rst.getString(3));
      }
    return null;
  }

  @Override
  public List<CustomEntity> search(String orderId) throws Exception {
      ResultSet rst = CrudUtil.execute("SELECT o.id, o.date, c.id, c.name, (SUM(od.qty * od.unitPrice)) AS total FROM `Order` o INNER JOIN orderdetail od ON o.id = od.orderId INNER JOIN Customer c on o.customerId = c.id WHERE o.id=?",orderId);

      if (rst.next()){
        return (List<CustomEntity>) new CustomEntity(rst.getString(1),
            rst.getString(2),
            rst.getDate(3),
            rst.getString(4),
            rst.getInt(5));
      }
      return null;
  }

}
