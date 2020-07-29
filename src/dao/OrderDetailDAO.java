package dao;

import java.util.List;

import entity.OrderDetail;
import entity.OrderDetailPK;

public interface OrderDetailDAO {

  public List<OrderDetail> findAllOrderDetails();

  public  OrderDetail findOrderDetail(OrderDetailPK orderDetailPK);

  public  boolean saveOrderDetail(OrderDetail orderDetail);

  public  boolean updateOrderDetail(OrderDetail orderDetail);

  public  boolean deleteOrderDetail(OrderDetailPK orderDetailPK);

}
