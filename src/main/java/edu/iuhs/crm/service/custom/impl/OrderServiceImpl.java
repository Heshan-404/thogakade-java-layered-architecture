package edu.iuhs.crm.service.custom.impl;

import edu.iuhs.crm.DTO.Order;
import edu.iuhs.crm.repository.RepositoryFactory;
import edu.iuhs.crm.repository.custom.OrderRepository;
import edu.iuhs.crm.service.custom.OrderService;
import edu.iuhs.crm.utils.RepositoryType;
import org.modelmapper.ModelMapper;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository = RepositoryFactory.getInstance().getRepositoryType(RepositoryType.ORDER);
    private final ModelMapper modelMapper = new ModelMapper();

}
