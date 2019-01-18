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

	private final static String CUSTOMERID_TO_TOKENSERVICE_QUEUE = "customerid_to_tokenservice";

	private ConnectionFactory factory;
	private Connection connection;
	private Channel channel;
	private Customer customer;

	public static ICustomerRepository customerRepository;

	public CustomerManager(ICustomerRepository _repository) throws ConnectionException, IOException, TimeoutException {
		customerRepository = _repository;

		factory = new ConnectionFactory();
		factory.setUsername("admin");
		factory.setPassword("Banana");

		factory.setHost("02267-bejing.compute.dtu.dk");
	
		connection = factory.newConnection();
		channel = connection.createChannel();
		channel.queueDeclare(CUSTOMERID_TO_TOKENSERVICE_QUEUE, false, false, false, null);
	}

	// Add customer
	public boolean addCustomer(String id, String name, String cpr, List<String> tokenList)
			throws RequestRejected, IOException, TimeoutException {

		customer = new Customer(id, name, cpr, tokenList);
		channel.basicPublish("", CUSTOMERID_TO_TOKENSERVICE_QUEUE, null, customer.getId().getBytes());
		channel.close();
		connection.close();
		
		return customerRepository.createCustomer(customer);
		
	}

	// Remove customer
	public boolean removeCustomer(Customer customer) throws CustomerNotFoundException {
		if (customerRepository.customerExists(
				new Customer(customer.getId(), customer.getName(), customer.getCpr(), customer.getTokenList()))) {
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

	public List<Customer> getAllCustomers() {
		return customerRepository.getCustomers();
	}

}
