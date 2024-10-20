package edu.iuhs.crm.repository;

import edu.iuhs.crm.repository.custom.impl.CustomerRepositoryImpl;
import edu.iuhs.crm.repository.custom.impl.ItemRepositoryImpl;
import edu.iuhs.crm.utils.RepositoryType;

public class RepositoryFactory {
    private static RepositoryFactory repositoryFactory;

    private RepositoryFactory() {

    }

    public static RepositoryFactory getInstance() {
        return repositoryFactory != null ? repositoryFactory : (repositoryFactory = new RepositoryFactory());
    }

    public <T extends SuperRepository> T getRepositoryType(RepositoryType repositoryType) {
        switch (repositoryType) {
            case CUSTOMER -> {
                return (T) new CustomerRepositoryImpl();
            }
            case ITEM -> {
                return (T) new ItemRepositoryImpl();
            }
        }
        return null;
    }

}
