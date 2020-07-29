package dao;

import java.util.List;

import entity.Item;

public interface ItemDAO {

  public List<Item> findAllItems();

  public  Item findItem(String itemCode);

  public  boolean saveItem(Item item);

  public  boolean updateItem(Item item);

  public  boolean deleteItem(String itemCode);

}
