package edu.iuhs.crm.service.custom.impl;

import edu.iuhs.crm.DTO.Customer;
import edu.iuhs.crm.entity.CustomerEntity;
import edu.iuhs.crm.repository.RepositoryFactory;
import edu.iuhs.crm.repository.custom.CustomerRepository;
import edu.iuhs.crm.service.custom.CustomerService;
import edu.iuhs.crm.utils.CrudUtil;
import edu.iuhs.crm.utils.RepositoryType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private final ModelMapper mapper = new ModelMapper();

    private final CustomerRepository customerRepository = RepositoryFactory.getInstance().getRepositoryType(RepositoryType.CUSTOMER);

    @Override
    public boolean addCustomer(Customer newCustomer) throws SQLException {
        ModelMapper mapper = new ModelMapper();
        CustomerEntity entity = mapper.map(newCustomer, CustomerEntity.class);
        return customerRepository.permit(entity);
    }

    @Override
    public boolean deleteCustomer(String customerID) {
        return customerRepository.delete(customerID);
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return customerRepository.update(mapper.map(customer, CustomerEntity.class));
    }

    @Override
    public Customer searchCustomer(String customerID) {
        CustomerEntity customer = customerRepository.search(customerID);
        if (customer == null) return null;
        return mapper.map(customer, Customer.class);
    }

    @Override
    public ObservableList<Customer> getAllCustomers() {

        List<CustomerEntity> customerEntities = customerRepository.getAll();

        ObservableList<Customer> customerDTOList = FXCollections.observableArrayList();
        customerEntities.forEach(customerEntity -> {
            Customer customerDTO = mapper.map(customerEntity, Customer.class);
            customerDTOList.add(customerDTO);
        });

        return customerDTOList;
    }

    @Override
    public ObservableList<String> getAllCustomerIDs() {
        return customerRepository.getAllId();
    }


}
