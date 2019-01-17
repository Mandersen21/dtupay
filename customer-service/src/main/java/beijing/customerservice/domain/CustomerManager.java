package beijing.customerservice.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.*;

import beijing.customerservice.repository.CustomerRepository;

public class CustomerManager {
	private CustomerRepository dataService = CustomerRepository.getInstance();
	private Customer customer;

	// When the customer is created, send a message queue to the tokenService
	private final static String CUSTOMERID_TO_TOKEN_QUEUE = "customerid_to_token";
	private ConnectionFactory factory;
 	private Connection connection;
 	private Channel channel;

	public void CustomerController() throws IOException, TimeoutException {
  		factory = new ConnectionFactory();
 		factory.setHost("localhost");

  		connection = factory.newConnection();
 		channel = connection.createChannel();

  		channel.queueDeclare(CUSTOMERID_TO_TOKEN_QUEUE, false, false, false, null);
  		String message = dataService.addCustomer(customer.getName(), customer.getCpr(), customer.getTokenList()); 
  		channel.basicPublish("", "products_queue", null, message.getBytes());
  		
  		channel.close();
  		connection.close();
	
	}	
}
