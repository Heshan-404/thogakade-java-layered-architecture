package edu.iuhs.crm.DTO;

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
public class Order {
    private String orderId;
    private LocalDate orderDate;
    private String customerID;
    private List<OrderDetail> orderDetails;

}
