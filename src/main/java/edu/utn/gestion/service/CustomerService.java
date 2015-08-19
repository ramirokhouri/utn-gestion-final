package edu.utn.gestion.service;

import edu.utn.gestion.dao.CustomerDAO;
import edu.utn.gestion.exception.GestionAppException;
import edu.utn.gestion.model.Customer;
import edu.utn.gestion.service.generic.GenericService;
import java.util.List;

public class CustomerService extends GenericService<Customer, Long> {
    private static final CustomerService INSTANCE = new CustomerService();
    private final CustomerDAO customerDAO;
    
    private CustomerService() {
        super(CustomerDAO.getInstance());
        this.customerDAO = (CustomerDAO) this.genericDAO;
    }
    
    public static CustomerService getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Customer> findBooksBySearch(String searchString) throws GestionAppException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
