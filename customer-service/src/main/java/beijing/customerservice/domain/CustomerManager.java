package beijing.customerservice.domain;

import java.util.List;


import beijing.customerservice.domain.Customer;
import beijing.customerservice.exception.CustomerNotFoundException;
import beijing.customerservice.exception.RequestRejected;
import beijing.customerservice.repository.ICustomerRepository;

public class CustomerManager {

	private Customer customer;
	public static ICustomerRepository customerRepository;

	public CustomerManager(ICustomerRepository customer_repository) {
		customerRepository = customer_repository;
	}

	// Add customer
	public List<Customer> addCustomer(String customerId, String cpr, String name, List<String> tokenList) throws Exception {

		if (name.matches(".*\\d+.*")) {
			throw new RequestRejected("Invalid name!");
		} else {
			try {
				Integer.parseInt(cpr);
				if (cpr.length() != 10) {
					throw new RequestRejected("Invalid CPR number!\nThe CPR has the following format:\nddmmyyxxxx ");
				} else {

					if (customerRepository.customerExists(new Customer(customerId, name, cpr, tokenList))) {
						throw new RequestRejected("The customer " + name + " already exists!");
					} else {

						customer = new Customer(customerId, name, cpr, tokenList);

					}
					customerRepository.createCustomer(customer);
					System.out.println("The customer" + customer.getCpr() + " is added to the system!");
				}
			} catch (NumberFormatException e) {
				throw new RequestRejected("Invalid format for CPR!");
			}
		}
		return null;
	}
	
	// Remove customer
	public List<Customer> removeCustomer(Customer customer) {
		return customerRepository.removeCustomer(customer);
	}
	
	// Get customer by id
	public Customer getCustomerId(String customerId) throws CustomerNotFoundException {
    	Customer customer = customerRepository.getCustomerById(customerId);
    	if (customer == null) {
    		throw new CustomerNotFoundException("Customer not found");
    	}
		return customer;
	}
	
	// Get customer by name
	public Customer getCustomerName(String customerName) throws CustomerNotFoundException {
    	Customer customer = customerRepository.getCustomerByName(customerName);
    	if (customer == null) {
    		throw new CustomerNotFoundException("Customer not found");
    	}
		return customer;
	}
	
	// Get customer by name
	public List<String>  getCustomerToken(String customerToken) throws CustomerNotFoundException {
    	List<String> tokens = customerRepository.getTokens(customer);
    	if (customer == null) {
    		throw new CustomerNotFoundException("Customer not found");
    	}
		return tokens;
	}
	
}
