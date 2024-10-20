package edu.iuhs.crm.DTO;

import lombok.*;


@ToString
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private String itemCode;
    private String description;
    private String packSize;
    private Double unitPrice;
    private Integer qtyOnHand;
}