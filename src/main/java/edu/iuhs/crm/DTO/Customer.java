package edu.iuhs.crm.DTO;

import lombok.*;

import java.time.LocalDate;
@ToString
@Setter
@Getter
@Data
@NoArgsConstructor
public class Customer {
    private String id;
    private String title;
    private String name;
    private LocalDate dob;
    private Double salary;
    private  String address;
    private String city;
    private String province;
    private String postalCode;

    public Customer(String id, String title, String name, LocalDate dob, Double salary, String address, String city, String province, String postalCode) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.dob = dob;
        this.salary = salary;
        this.address = address;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
    }
}