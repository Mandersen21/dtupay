package beijing.customerservice.repository;

import java.util.ArrayList;
import java.util.List;
import beijing.customerservice.domain.Customer;

public class CustomerRepository implements ICustomerRepository {
    private List<Customer> customerList;

    public CustomerRepository() {
    	customerList = new ArrayList<Customer>();
    }
    
    public boolean createCustomer(Customer customer) {
    	if (getCustomerById(customer.getId()) != null) {
    		return false;
    	} else {
    		customerList.add(customer);
    		return true;
    	}
    }

    public Customer getCustomerById(String id) {
        for (Customer customer : customerList) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }

        return null;
    }
    
    public Customer getCustomerByCpr(String cpr) {
        for (Customer customer : customerList) {
            if (customer.getCpr().equals(cpr)) {
                return customer;
            }
        }

        return null;
    }
    
    public boolean removeCustomer(Customer customer) {
        if (getCustomerById(customer.getId()) != null) {
        	return customerList.remove(customer);
        } 
        return false; 
    }

	public boolean customerExists(Customer customer) {
		if (getCustomerById(customer.getId()) != null) {
			return true;
		}
		return false;
	}
	
	public List<String> getTokens(Customer customer) {
		for (Customer c : customerList) {
			return c.getTokenList();
        }
        return null;
    }

	@Override
	public List<Customer> getCustomers() {
		return customerList;
	}

}