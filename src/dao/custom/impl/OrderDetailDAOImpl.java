package dao.custom.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.CrudUtil;
import dao.custom.OrderDetailDAO;
import entity.OrderDetail;
import entity.OrderDetailPK;

public class OrderDetailDAOImpl implements OrderDetailDAO {

  @Override
  public List<OrderDetail> findAll() {
    try {
      ResultSet rst = CrudUtil.execute("SELECT * FROM `OrderDetail`");
      List<OrderDetail> orderDetails = new ArrayList<>();
      while (rst.next()) {
        orderDetails.add(new OrderDetail(rst.getString(1),
            rst.getString(2),
            rst.getInt(3),
            rst.getBigDecimal(4)));
      }
      return orderDetails;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return null;
    }
  }

  @Override
  public OrderDetail find(OrderDetailPK key) {
    try {
      ResultSet rst = CrudUtil.execute("SELECT * FROM `OrderDetail` WHERE orderId=? AND itemCode=?", key.getOrderId(), key.getItemCode());
      if (rst.next()) {
        return new OrderDetail(rst.getString(1),
            rst.getString(2),
            rst.getInt(3),
            rst.getBigDecimal(4));
      }
      return null;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return null;
    }
  }

  @Override
  public boolean save(OrderDetail orderDetail) {
    try {
      return CrudUtil.execute("INSERT INTO `OrderDetail` VALUES (?,?,?,?)", orderDetail.getOrderDetailPK().getOrderId(),
          orderDetail.getOrderDetailPK().getItemCode(), orderDetail.getQty(), orderDetail.getUnitPrice());
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean update(OrderDetail orderDetail) {
    try {
      return CrudUtil.execute("UPDATE OrderDetail SET qty=?, unitPrice=? WHERE orderId=? AND itemCode=?", orderDetail.getQty(), orderDetail.getUnitPrice(), orderDetail.getOrderDetailPK().getOrderId(), orderDetail.getOrderDetailPK().getItemCode());
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean delete(OrderDetailPK orderDetailPK) {
    try {
      return CrudUtil.execute("DELETE FROM `OrderDetail` WHERE orderId=? AND itemCode=?", orderDetailPK.getOrderId(), orderDetailPK.getItemCode());
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return false;
    }
  }
}
