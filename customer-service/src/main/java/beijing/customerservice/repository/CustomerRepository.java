package beijing.customerservice.repository;

import java.util.ArrayList;
import java.util.List;
import beijing.customerservice.domain.Customer;

/**
 * 
 * CustomerRepository class contains the logic that permits the manipulation with the customerRepository.
 * @author BotezatuCristian
 *
 */

public class CustomerRepository implements ICustomerRepository {
    private List<Customer> customerList;

    /**
     * Customers are stored as list of Customer objects.
     */
    
    public CustomerRepository() {
    	customerList = new ArrayList<Customer>();
    }
    
    /**
     * The createCustomer method adds a customer to the Cutomer list.
     */
    
    public boolean createCustomer(Customer customer) {
    	if (getCustomerById(customer.getId()) != null) {
    		return false;
    	} else {
    		customerList.add(customer);
    		return true;
    	}
    }

    /**
     * The getCustomerById method gets the Customer object by its id. 
     */
    
    public Customer getCustomerById(String id) {
        for (Customer customer : customerList) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }

        return null;
    }
    
    /**
     * The getCustomerByCpr method gets the Customer object by its cpr. 
     */
    
    public Customer getCustomerByCpr(String cpr) {
        for (Customer customer : customerList) {
            if (customer.getCpr().equals(cpr)) {
                return customer;
            }
        }

        return null;
    }
    
    /**
     * The removeCustomer method find the Customer object by its id and removes it from the Customer list.
     */
    
    public boolean removeCustomer(Customer customer) {
        if (getCustomerById(customer.getId()) != null) {
        	return customerList.remove(customer);
        } 
        return false; 
    }

    /**
     * The customerExists method checks whether the customer exists in the Customer list, returning a boolean.
     */
    
	public boolean customerExists(Customer customer) {
		if (getCustomerById(customer.getId()) != null) {
			return true;
		}
		return false;
	}
	
	/**
     * The getTokens method gets the tokens assigned to a specific Customer.
     */
	
	public List<String> getTokens(Customer customer) {
		for (Customer c : customerList) {
			return c.getTokenList();
        }
        return null;
    }

	/**
	 * The getTokens method retrieves all the customer from the Customer list.
	 */
	
	@Override
	public List<Customer> getCustomers() {
		return customerList;
	}

	/**
	 * The updateCustomer method updates the customer (being meant mainly for changing its status once being verified if it has an account)
	 */
	
	@Override
	public boolean updateCustomer(Customer customer) {
		for(Customer c : customerList) {
			if(c.getId().equals(customer.getId())) {
				c = customer;
			}
		}
		return true;
	}

}