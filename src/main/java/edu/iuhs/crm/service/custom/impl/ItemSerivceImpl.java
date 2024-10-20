package edu.iuhs.crm.service.custom.impl;

import edu.iuhs.crm.DTO.Item;
import edu.iuhs.crm.DTO.OrderDetail;
import edu.iuhs.crm.entity.ItemEntity;
import edu.iuhs.crm.repository.RepositoryFactory;
import edu.iuhs.crm.repository.custom.ItemRepository;
import edu.iuhs.crm.service.custom.ItemService;
import edu.iuhs.crm.utils.RepositoryType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;

import java.util.List;

public class ItemSerivceImpl implements ItemService {
    private final ItemRepository itemRepository = RepositoryFactory.getInstance().getRepositoryType(RepositoryType.ITEM);
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public boolean addItem(Item item) {
        return itemRepository.permit(mapper.map(item, ItemEntity.class));
    }

    @Override
    public boolean updateItem(Item item) {
        return itemRepository.update(mapper.map(item, ItemEntity.class));
    }

    @Override
    public Item searchItem(String itemCode) {
        ItemEntity itemEntity = itemRepository.search(itemCode);
        if (itemEntity == null) return null;
        return mapper.map(itemEntity, Item.class);
    }

    @Override
    public boolean deleteItem(String itemCode) {
        return itemRepository.delete(itemCode);
    }

    @Override
    public ObservableList<Item> getAllItem() {
        List<ItemEntity> itemEntities = itemRepository.getAll();
        ObservableList<Item> itemList = FXCollections.observableArrayList();
        itemEntities.forEach(itemEntity -> {
            itemList.add(mapper.map(itemEntity, Item.class));
        });
        return itemList;
    }

    @Override
    public ObservableList<String> getAllItemIds() {
        return itemRepository.getAllId();
    }

    @Override
    public boolean updateStock(List<OrderDetail> orderDetails) {
        return false;
    }
}
