package beijing.customerservice.domain;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.*;

import beijing.customerservice.exception.ConnectionException;
import beijing.customerservice.exception.CustomerNotFoundException;
import beijing.customerservice.exception.RequestRejected;
import beijing.customerservice.repository.ICustomerRepository;

public class CustomerManager {
	
	private ConnectionFactory factory;
 	private Connection connection;
 	private Channel channel;
 	private Customer customer;

 	private final static String CUSTOMERID_TO_TOKENSERVICE_QUEUE = "customerid_to_tokenservice";
	public static ICustomerRepository customerRepository;

	public CustomerManager(ICustomerRepository _repository) throws ConnectionException, IOException, TimeoutException {
		customerRepository = _repository;

		try {
			factory = new ConnectionFactory();
			factory.setUsername("admin");
			factory.setPassword("Banana");
			
			factory.setHost("02267-bejing.compute.dtu.dk");

			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(CUSTOMERID_TO_TOKENSERVICE_QUEUE, false, false, false, null);
		} catch(Exception e) {
			throw new ConnectionException("Connection can't be esteblished");
		}
		
	}
	
	// Add customer
	public boolean addCustomer(String id, String name, String cpr, List<String> tokenList) throws RequestRejected, IOException {

//		if (name.matches(".*\\d+.*")) {
//			throw new RequestRejected("Invalid name!");
//		} else {
//			try {
//				Integer.parseInt(cpr);
//				if (cpr.length() != 10) {
//					throw new RequestRejected("Invalid CPR number!\nThe CPR has the following format:\nddmmyyxxxx ");
//				} else {
//
//					if (customerRepository.customerExists(new Customer(id, name, cpr, tokenList))) {
//						throw new RequestRejected("The customer with id " + id + " already exists!");
//					} else {
//
//						customer = new Customer(id, name, cpr, tokenList);
//
//					}
//					System.out.println("The customer" + customer.getCpr() + " is being added to the system!");
//					channel.basicPublish("", CUSTOMERID_TO_TOKENSERVICE_QUEUE, null, customer.getId().getBytes());
//					return customerRepository.createCustomer(customer);
//				}
//			} catch (NumberFormatException e) {
//				throw new RequestRejected("Invalid format for CPR!");
//			}
//		}
		
		customer = new Customer(id, name, cpr, tokenList);
		return customerRepository.createCustomer(customer);
		
	}
	
	// Remove customer
	public boolean removeCustomer(Customer customer) throws CustomerNotFoundException {
		if (customerRepository.customerExists(new Customer(customer.getId(), customer.getName(), customer.getCpr(), customer.getTokenList()))) {
			customerRepository.removeCustomer(customer);
			return true;
		} else {
	
			throw new CustomerNotFoundException("The customer " + customer.getName() + " is not in the system!");
	
		}
	}
	
	// Get customer by id
	public Customer getCustomerById(String customerId) throws CustomerNotFoundException {
    	Customer customer = customerRepository.getCustomerById(customerId);
    	if (customer == null) {
    		throw new CustomerNotFoundException("Customer not found");
    	}
		return customer;
	}
	
	
	// Get customer by token
	public List<String>  getCustomerToken(String customerToken) throws CustomerNotFoundException {
    	List<String> tokens = customerRepository.getTokens(customer);
    	if (customer == null) {
    		throw new CustomerNotFoundException("Customer not found");
    	}
		return tokens;
	}
	
}
