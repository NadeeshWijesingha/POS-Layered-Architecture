package business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import dao.DAOFactory;
import dao.DAOType;
import dao.custom.ItemDAO;
import entity.Item;
import util.ItemTM;

public class ItemBO {

  public static String getNewItemCode() {
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

  public static List<ItemTM> getAllItems() {
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

  public static boolean saveItem(String code, String description, int qtyOnHand, double unitPrice) {
    ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
    try {
      return itemDAO.save(new Item(code, description, BigDecimal.valueOf(unitPrice), qtyOnHand));
    } catch (Exception e) {
      e.printStackTrace();
    }return false;
  }

  public static boolean deleteItem(String itemCode) {
    ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
    try {
      return itemDAO.delete(itemCode);
    } catch (Exception e) {
      e.printStackTrace();
    }return false;
  }

  public static boolean updateItem(String description, int qtyOnHand, double unitPrice, String itemCode) {
    ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
    try {
      return itemDAO.update(new Item(itemCode, description,
          BigDecimal.valueOf(unitPrice), qtyOnHand));
    } catch (Exception e) {
      e.printStackTrace();
    }return false;
  }

}
