package edu.iuhs.crm.entity;

import edu.iuhs.crm.entity.OrderDetailEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrderEntity {
    private String orderId;
    private LocalDate orderDate;
    private String customerID;
    private List<OrderDetailEntity> orderDetails;

}
