package edu.iuhs.crm.service.custom;

import edu.iuhs.crm.DTO.Item;
import edu.iuhs.crm.DTO.OrderDetail;
import edu.iuhs.crm.service.SuperService;
import javafx.collections.ObservableList;

import java.util.List;

public interface ItemService extends SuperService {
    boolean addItem(Item item);

    boolean updateItem(Item item);

    Item searchItem(String itemCode);

    boolean deleteItem(String itemCode);

    ObservableList<Item> getAllItem();

    ObservableList<String> getAllItemIds();

    boolean updateStock(List<OrderDetail> orderDetails);
}
