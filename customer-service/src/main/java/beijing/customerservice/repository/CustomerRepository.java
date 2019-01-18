package beijing.customerservice.repository;

import java.util.ArrayList;
import java.util.List;
import beijing.customerservice.domain.Customer;

public class CustomerRepository implements ICustomerRepository {
    private List<Customer> customerList = new ArrayList<>();

    public boolean createCustomer(Customer customer) {
    	if (customerList.contains(customer) == false) {
    		return customerList.add(customer);
    	}
		return false;
    }

    public Customer getCustomerById(String id) {
        for (Customer customer : customerList) {
            if (customer.getId().equals(id)) {
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

}