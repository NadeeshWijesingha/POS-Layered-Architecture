package business.custom.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import business.custom.ItemBO;
import dao.DAOFactory;
import dao.DAOType;
import dao.custom.ItemDAO;
import entity.Item;
import util.ItemTM;

public class ItemBOImpl implements ItemBO {

  public String getNewItemCode() {
    ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
    String lastItemCode = null;
    try {
      lastItemCode = itemDAO.getLastItemCode();
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (lastItemCode == null) {
      return "I001";
    } else {
      int maxId = Integer.parseInt(lastItemCode.replace("I", ""));
      maxId = maxId + 1;
      String id = "";
      if (maxId < 10) {
        id = "I00" + maxId;
      } else if (maxId < 100) {
        id = "I0" + maxId;
      } else {
        id = "I" + maxId;
      }
      return id;
    }
  }

  public List<ItemTM> getAllItems() {
    ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
    List<Item> allItems = null;
    try {
      allItems = itemDAO.findAll();
    } catch (Exception e) {
      e.printStackTrace();
    }
    List<ItemTM> items = new ArrayList<>();
    for (Item item : allItems) {
      items.add(new ItemTM(item.getCode(), item.getDescription(), item.getQtyOnHand(),
          item.getUnitPrice().doubleValue()));
    }
    return items;
  }

  public boolean saveItem(String code, String description, int qtyOnHand, double unitPrice) {
    ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
    try {
      return itemDAO.save(new Item(code, description, BigDecimal.valueOf(unitPrice), qtyOnHand));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean deleteItem(String itemCode) {
    ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
    try {
      return itemDAO.delete(itemCode);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean updateItem(String description, int qtyOnHand, double unitPrice, String itemCode) {
    ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
    try {
      return itemDAO.update(new Item(itemCode, description,
          BigDecimal.valueOf(unitPrice), qtyOnHand));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

}
