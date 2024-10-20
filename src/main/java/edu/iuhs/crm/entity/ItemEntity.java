package edu.iuhs.crm.entity;

import lombok.*;


@ToString
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {
    private String itemCode;
    private String description;
    private String packSize;
    private Double unitPrice;
    private Integer qtyOnHand;
}