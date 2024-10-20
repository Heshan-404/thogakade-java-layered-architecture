package edu.iuhs.crm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailEntity {
   private String orderID;
   private String itemCode;
   private Integer qty;
   private Double discount;
}
