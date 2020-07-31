package dao.custom.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import entity.Item;

public class ItemDAOImpl implements ItemDAO {

  @Override
  public String getLastItemCode() {
    try {
      ResultSet rst = CrudUtil.execute("SELECT * FROM Item ORDER BY code DESC LIMIT 1");
      if (!rst.next()) {
        return null;
      } else {
        return rst.getString(1);
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return null;
    }
  }

  @Override
  public List<Item> findAll() {
    try {
      ResultSet rst = CrudUtil.execute("SELECT * FROM Item");
      List<Item> items = new ArrayList<>();
      while (rst.next()) {
        items.add(new Item(rst.getString(1),
            rst.getString(2),
            rst.getBigDecimal(3),
            rst.getInt(4)));
      }
      return items;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return null;
    }
  }

  @Override
  public Item find(String key) {
    try {
      ResultSet rst = CrudUtil.execute("SELECT * FROM Item WHERE code=?",key);
      if (rst.next()) {
        return new Item(rst.getString(1),
            rst.getString(2),
            rst.getBigDecimal(3),
            rst.getInt(4));
      }
      return null;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return null;
    }
  }

  @Override
  public boolean save(Item item) {
    try {
      return CrudUtil.execute("INSERT INTO Item VALUES (?,?,?,?)", item.getCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand());
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean update(Item item) {
    try {
      return CrudUtil.execute("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?", item.getDescription(), item.getUnitPrice(), item.getQtyOnHand(), item.getCode());
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean delete(String key) {
    try {
      return CrudUtil.execute("DELETE FROM Item WHERE code=?",key);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return false;
    }
  }
}
