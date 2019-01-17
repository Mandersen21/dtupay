package beijing.customerservice.repository;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;

import beijing.customerservice.domain.Customer;

public class CustomerRepository implements ICustomerRepository {
    private List<Customer> customerList = new ArrayList<>();

    private static CustomerRepository ourInstance = new CustomerRepository();

    public static CustomerRepository getInstance() {
        return ourInstance;
    }

    public String generateRandomCustomerId(int length) {
		int m = (int) Math.pow(10, length - 1);
		return Integer.toString(m + new Random().nextInt(9 * m));
	}
    
    public String addCustomer(String name, String cpr, List<String> tokenList) {
    	String id = generateRandomCustomerId(3);
        Customer customer = new Customer(id, name, cpr, tokenList);
  
        customerList.add(customer);
        return id;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public Customer getCustomerById(String id) {
        for (Customer customer : customerList) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }

        return null;
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
			return c.getTokenList();
        }
        return null;
    }
}