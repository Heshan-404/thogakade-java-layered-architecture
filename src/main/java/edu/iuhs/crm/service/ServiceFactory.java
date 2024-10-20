package edu.iuhs.crm.service;

import edu.iuhs.crm.service.custom.impl.CustomerServiceImpl;
import edu.iuhs.crm.service.custom.impl.ItemSerivceImpl;
import edu.iuhs.crm.service.custom.impl.OrderServiceImpl;
import edu.iuhs.crm.utils.ServiceType;

public class ServiceFactory {

    private static ServiceFactory serviceFactory;

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return serviceFactory != null ? serviceFactory : (serviceFactory = new ServiceFactory());
    }

    public <T extends SuperService> T getService(ServiceType serviceType) {
        switch (serviceType) {
            case CUSTOMER -> {
                return (T) new CustomerServiceImpl();
            }
            case ITEM -> {
                return (T) new ItemSerivceImpl();
            }
            case ORDER -> {
                return (T) new OrderServiceImpl();
            }
        }
        return null;
    }
}
