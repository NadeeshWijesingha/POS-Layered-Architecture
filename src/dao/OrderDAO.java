package dao;

import java.util.List;

import entity.Order;

public interface OrderDAO {

  public List<Order> findAllOrders();

  public  Order findOrder(String orderId);

  public  boolean saveOrder(Order order);

  public  boolean updateOrder(Order order);

  public  boolean deleteOrder(String orderId);

}
