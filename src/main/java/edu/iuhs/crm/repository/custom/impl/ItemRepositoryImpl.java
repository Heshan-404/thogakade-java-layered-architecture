package edu.iuhs.crm.repository.custom.impl;

import edu.iuhs.crm.entity.ItemEntity;
import edu.iuhs.crm.repository.custom.ItemRepository;
import edu.iuhs.crm.utils.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemRepositoryImpl implements ItemRepository {
    @Override
    public boolean permit(ItemEntity item) {
        String sql = "INSERT INTO item VALUES(?,?,?,?,?)";
        try {
            return CrudUtil.execute(
                    sql,
                    item.getItemCode(),
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQtyOnHand()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(ItemEntity item) {

        String sql = "UPDATE item SET Description=?, PackSize=?, UnitPrice=?, QtyOnHand=? WHERE ItemCode=?";

        try {
            return CrudUtil.execute(sql,
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQtyOnHand(),
                    item.getItemCode());
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<ItemEntity> getAll() {
        ObservableList<ItemEntity> itemEntitiesList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM item";

        try {
            try (ResultSet resultSet = CrudUtil.execute(sql)) {
                while (resultSet.next()) {
                    itemEntitiesList.add(new ItemEntity(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getDouble(4),
                            resultSet.getInt(5)
                    ));
                }
            }
            return itemEntitiesList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String itemCode) {
        String sql = "DELETE FROM item WHERE ItemCode=?";
        try {
            if (CrudUtil.execute(sql, itemCode)) {
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    @Override
    public ItemEntity search(String itemCode) {
        try {
            String sql = "SELECT * FROM item WHERE ItemCode=?";
            try (ResultSet resultSet = CrudUtil.execute(sql, itemCode)) {
                resultSet.next();
                return new ItemEntity(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5)
                );
            }
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public ObservableList<String> getAllId() {
        ObservableList<String> itemIDList = FXCollections.observableArrayList();
        try {
            ResultSet resItemIDList = CrudUtil.execute("SELECT ItemCode FROM item");
            while (resItemIDList.next()) {
                itemIDList.add(resItemIDList.getString("ItemCode"));
            }
            return itemIDList;
        } catch (SQLException e) {
            return itemIDList;
        }
    }


}
