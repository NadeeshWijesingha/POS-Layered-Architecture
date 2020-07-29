package dao;

import java.util.List;

import entity.Customer;

public interface CustomerDAO {

  public List<Customer> findAllCustomers();

  public  Customer findCustomer(String customerId);

  public  boolean saveCustomer(Customer customer);

  public  boolean updateCustomer(Customer customer);

  public  boolean deleteCustomer(String customerId);

  public  String getLastCustomerId();

}
