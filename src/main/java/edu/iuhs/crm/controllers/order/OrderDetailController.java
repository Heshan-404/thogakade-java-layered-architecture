package edu.iuhs.crm.controllers.order;


import edu.iuhs.crm.DTO.OrderDetail;
import edu.iuhs.crm.utils.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailController {
    public static boolean addOrderDetail(List<OrderDetail> orderDetailList) throws SQLException {
        for (OrderDetail orderDetail : orderDetailList) {
            boolean b = addOrderDetail(orderDetail);
            if (!b) {
                return false;
            }
        }
        return true;
    }

    public static boolean addOrderDetail(OrderDetail orderDetail) throws SQLException {
        return CrudUtil.execute("INSERT INTO orderdetail VALUES(?,?,?,?)",
                orderDetail.getOrderID(),
                orderDetail.getItemCode(),
                orderDetail.getQty(),
                orderDetail.getDiscount()
        );
    }
}
