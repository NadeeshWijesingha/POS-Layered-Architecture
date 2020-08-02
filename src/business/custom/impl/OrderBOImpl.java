package business.custom.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import business.custom.OrderBO;
import dao.DAOFactory;
import dao.DAOType;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import db.DBConnection;
import entity.Item;
import entity.Order;
import entity.OrderDetail;
import util.OrderDetailTM;
import util.OrderTM;

public class OrderBOImpl implements OrderBO {

  OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOType.ORDER);
  OrderDetailDAO orderDetailDAO = DAOFactory.getInstance().getDAO(DAOType.ORDERDETAIL);
  ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);

  public String getNewOrderId() {
    String lastOrderId = null;
    try {
      lastOrderId = orderDAO.getLastOrderId();
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (lastOrderId == null) {
      return "OD001";
    } else {
      int maxId = Integer.parseInt(lastOrderId.replace("OD", ""));
      maxId = maxId + 1;
      String id = "";
      if (maxId < 10) {
        id = "OD00" + maxId;
      } else if (maxId < 100) {
        id = "OD0" + maxId;
      } else {
        id = "OD" + maxId;
      }
      return id;
    }
  }

  public boolean placeOrder(OrderTM order, List<OrderDetailTM> orderDetails) throws Exception {
    Connection connection = DBConnection.getInstance().getConnection();
    try {
      connection.setAutoCommit(false);
      boolean result = orderDAO.save(new Order(order.getOrderId(),
          Date.valueOf(order.getOrderDate()),
          order.getCustomerId()));
      if (!result) {
        connection.rollback();
        return false;
      }
      for (OrderDetailTM orderDetail : orderDetails) {
        result = orderDetailDAO.save(new OrderDetail(
            order.getOrderId(), orderDetail.getCode(),
            orderDetail.getQty(), BigDecimal.valueOf(orderDetail.getUnitPrice())
        ));
        if (!result) {
          connection.rollback();
          return false;
        }
        Item item = itemDAO.find(orderDetail.getCode());
        item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
        itemDAO.update(item);
        if (!result) {
          connection.rollback();
          return false;
        }
      }
      connection.commit();
      return true;
    } catch (Throwable throwables) {
      throwables.printStackTrace();
      try {
        connection.rollback();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      return false;
    } finally {
      try {
        connection.setAutoCommit(true);
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }
  }

}
