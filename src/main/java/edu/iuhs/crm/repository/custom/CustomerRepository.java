package edu.iuhs.crm.repository.custom;

import edu.iuhs.crm.DTO.Customer;
import edu.iuhs.crm.entity.CustomerEntity;
import edu.iuhs.crm.repository.CrudRepository;
import edu.iuhs.crm.repository.SuperRepository;

import java.sql.SQLException;

public interface CustomerRepository extends CrudRepository<CustomerEntity, String> {

}
