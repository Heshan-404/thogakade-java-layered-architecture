package edu.iuhs.crm.service.custom;

import edu.iuhs.crm.DTO.Customer;
import edu.iuhs.crm.service.SuperService;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface CustomerService extends SuperService {
    boolean addCustomer(Customer newCustomer) throws SQLException;

    boolean deleteCustomer(String customerID);

    boolean updateCustomer(Customer customer);

    Customer searchCustomer(String customerID);

    ObservableList<Customer> getAllCustomers();

    ObservableList<String> getAllCustomerIDs();
}
