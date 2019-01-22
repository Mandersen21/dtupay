package beijing.customerservice.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.*;

import beijing.customerservice.exception.ConnectionException;
import beijing.customerservice.exception.CustomerNotFoundException;
import beijing.customerservice.exception.RequestRejected;
import beijing.customerservice.repository.ICustomerRepository;

/**
 * 
 * CustomerManager cotains all the logic that allows us to manipulate with the customer.
 * @author BotezatuCristian
 *
 */
public class CustomerManager {

	private final static String CUSTOMERID_TO_TOKENSERVICE_QUEUE = "customerid_to_tokenservice";

	private ConnectionFactory factory;
	private Connection connection;
	private Channel channel;
	private Customer customer;

	public static ICustomerRepository customerRepository;

	/**
	 * 
	 * Define a method that takes as input the customerRepository, making it easier to use further on.
	 * @param _repository
	 * @throws IOException
	 * @throws TimeoutException
	 * 
	 */
	
	public CustomerManager(ICustomerRepository _repository) throws IOException, TimeoutException  {
		customerRepository =_repository;

		setupMessageQueue();
		
		// Cucumber user story 1
		List<String> tokenList = new ArrayList<String>();
		tokenList.add("123");
		Customer c = new Customer("123456", "john john", "0101010101", tokenList, AccountStatus.VERIFIED);
		customerRepository.createCustomer(c);
	}
	
	/**
	 * 
	 * The MQs on the channels CUSTOMERID_TO_TOKENSERVICE_QUEUE, CUSTOMER_PAYMENT_REGITRATION and PAYMENT_CUSTOMER_REGITRATION are set.
	 * Thus, once creating a customer via REST we notify the TokenService that assigns tokens for the created customer.
	 *  
	 * @throws IOException
	 * @throws TimeoutException
	 * 
	 */
	
	private void setupMessageQueue() throws IOException, TimeoutException {
		
		factory = new ConnectionFactory();
		factory.setUsername("admin");
		factory.setPassword("Banana");
		factory.setHost("02267-bejing.compute.dtu.dk");
		connection = factory.newConnection();
		
		channel = connection.createChannel();
		channel.queueDeclare(CUSTOMERID_TO_TOKENSERVICE_QUEUE, false, false, false, null);
		
	}
	
	/**
	 * 
	 * The method adds a customer with a name and a cpr to the customerRepository.
	 * @param name
	 * @param cpr
	 * @return Customer
	 * @throws RequestRejected
	 * @throws IOException
	 * @throws TimeoutException
	 * @throws CustomerNotFoundException
	 * 
	 */
	
	public Customer addCustomer(String name, String cpr)
			throws RequestRejected, IOException, TimeoutException, CustomerNotFoundException {
		String id = UUID.randomUUID().toString();
		
		if (customerRepository.getCustomerByCpr(cpr) != null) {
			throw new RequestRejected("The customer " + cpr + " is already in the system!");
        } else {
		
			Customer  c = new Customer(id, name, cpr, new ArrayList<String>(), AccountStatus.UNVERIFIED);
			String message = c.getId()+","+c.getCpr();
			
			customerRepository.createCustomer(c);
			channel.basicPublish("", CUSTOMERID_TO_TOKENSERVICE_QUEUE, null, c.getId().getBytes());
				
			channel.close();
			connection.close();
			return c;
        }
	}

	/**
	 * 
	 * The method removes the customer from the customerRepository.
	 * @param customer
	 * @return boolean
	 * @throws CustomerNotFoundException
	 * 
	 */
	public boolean removeCustomer(Customer customer) throws CustomerNotFoundException {
		if (customerRepository.customerExists(customer)) {
			customerRepository.removeCustomer(customer);
			return true;
		} else {
			throw new CustomerNotFoundException("The customer " + customer.getName() + " is not in the system!");
		}
	}

	/**
	 * 
	 * The method gets a customer from the customerRepository by its id.
	 * @param customerId
	 * @return Customer
	 * @throws CustomerNotFoundException
	 * 
	 */
	
	public Customer getCustomerById(String customerId) throws CustomerNotFoundException {
		Customer customer = customerRepository.getCustomerById(customerId);
		if (customer == null) {
			throw new CustomerNotFoundException("Customer not found");
		}
		return customer;
	}
	
	/**
	 * 
	 * The method gets a customer from the customerRepository by its cpr.
	 * @param cpr
	 * @return Customer
	 * @throws CustomerNotFoundException
	 * 
	 */
	
	public Customer getCustomerByCpr(String cpr) throws CustomerNotFoundException {
		Customer customer = customerRepository.getCustomerByCpr(cpr);
		if (customer == null) {
			throw new CustomerNotFoundException("Customer not found");
		}
		return customer;
	}

	/**
	 * 
	 * The method gets all the customers from the customerRepository by its cpr.
	 * @return List<Customer>
	 * 
	 */
	
	public List<Customer> getAllCustomers() {
		return customerRepository.getCustomers();
	}

}
