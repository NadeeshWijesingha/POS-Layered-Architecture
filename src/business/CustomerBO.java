package business;

import java.util.ArrayList;
import java.util.List;

import dao.DAOFactory;
import dao.DAOType;
import dao.custom.CustomerDAO;
import entity.Customer;
import util.CustomerTM;

public class CustomerBO {

  public String getNewCustomerId() {
    CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);
    String lastCustomerId = null;
    try {
      lastCustomerId = customerDAO.getLastCustomerId();
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (lastCustomerId == null) {
      return "C001";
    } else {
      int maxId = Integer.parseInt(lastCustomerId.replace("C", ""));
      maxId = maxId + 1;
      String id = "";
      if (maxId < 10) {
        id = "C00" + maxId;
      } else if (maxId < 100) {
        id = "C0" + maxId;
      } else {
        id = "C" + maxId;
      }
      return id;
    }
  }

  public List<CustomerTM> getAllCustomers() {
    CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);
    List<Customer> allCustomers = null;
    try {
      allCustomers = customerDAO.findAll();
    } catch (Exception e) {
      e.printStackTrace();
    }
    List<CustomerTM> customers = new ArrayList<>();
    for (Customer customer : allCustomers) {
      customers.add(new CustomerTM(customer.getId(), customer.getName(), customer.getAddress()));
    }
    return customers;
  }

  public boolean saveCustomer(String id, String name, String address) {
    CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);
    try {
      return customerDAO.save(new Customer(id, name, address));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean deleteCustomer(String customerId) {
    CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);
    try {
      return customerDAO.delete(customerId);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean updateCustomer(String name, String address, String customerId) {
    CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);
    try {
      return customerDAO.update(new Customer(customerId, name, address));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

}
