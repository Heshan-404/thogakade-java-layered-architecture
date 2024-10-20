package edu.iuhs.crm.controllers.order;


import edu.iuhs.crm.service.ServiceFactory;
import edu.iuhs.crm.service.custom.ItemService;
import edu.iuhs.crm.utils.CrudUtil;
import edu.iuhs.crm.utils.DB;
import edu.iuhs.crm.utils.ServiceType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderController {
    private final ItemService itemService = ServiceFactory.getInstance().getService(ServiceType.ITEM);

    public String getNextOrderID() throws SQLException {
        ResultSet execute = CrudUtil.execute("SELECT COUNT(*) FROM orders");
        while (execute.next()) {
            return String.format("D%04d", (execute.getInt(1) + 1));
        }
        return null;
    }

    public Boolean placeOrder(edu.iuhs.crm.DTO.Order order) throws SQLException {
        Connection connection = DB.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean isOrderAdd;
            try (PreparedStatement psTm = connection.prepareStatement("INSERT INTO orders VALUE(?,?,?)")) {
                psTm.setObject(1, order.getOrderId());
                psTm.setObject(2, order.getOrderDate());
                psTm.setObject(3, order.getCustomerID());
                isOrderAdd = psTm.executeUpdate() > 0;
            }
            if (isOrderAdd) {
                boolean isOrderDetailAdd = OrderDetailController.addOrderDetail(order.getOrderDetails());

                if (isOrderDetailAdd) {
                    boolean isUpdateStock = itemService.updateStock(order.getOrderDetails());

                    if (isUpdateStock) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(false);
        }
    }
}