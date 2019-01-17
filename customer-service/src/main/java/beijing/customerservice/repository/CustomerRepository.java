package beijing.customerservice.repository;

import beijing.customerservice.domain.Customer;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements ICustomerRepository {

    List<Customer> customerList;

    public CustomerRepository() {
        customerList = new ArrayList<Customer>();
    }

    public Customer getCustomerById(String customerId) {
        for (Customer c : customerList) {
            if (c.getId().contentEquals(customerId)) {
                return c;
            }
        }
        return null;
    }
    
    public Customer getCustomerByName(String customerName) {
        for (Customer c : customerList) {
            if (c.getName().contentEquals(customerName)) {
                return c;
            }
        }
        return null;
    }

    public List<Customer> createCustomer(Customer customer) {
    	
        if (getCustomerById(customer.getId()) == null) {
        	customerList.add(customer);
        }
		return customerList; 
    }
    
    public List<Customer> removeCustomer(Customer customer) {
        if (getCustomerById(customer.getId()) != null) {
        	customerList.remove(customer);
        } 
        return customerList; 
    }

	public boolean customerExists(Customer customer) {
		if (getCustomerById(customer.getId()) != null) {
			return true;
		}
		return false;
	}
	
	public List<String> getTokens(Customer customer) {
		for (Customer c : customerList) {
			return c.getTokens();
        }
        return null;
    }
    
}
