package beijing.customerservice.domain;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.*;

import beijing.customerservice.domain.Customer;
import beijing.customerservice.exception.RequestRejected;
import beijing.customerservice.repository.CustomerRepository;

public class CustomerController {

	private final static String CUSTOMERID_TO_TOKEN_QUEUE = "customerid_to_token";
	private final static String ACCUNT_STATUS_TO_CUSTOMER_QUEUE = "customerid_to_token";

	private Customer customer;
	private CustomerRepository customerRepository;

	private ConnectionFactory factory;
	private Connection connection;
	private Channel channel;

	public CustomerController() throws IOException, TimeoutException {
		customerRepository = new CustomerRepository();

		factory = new ConnectionFactory();
		factory.setHost("localhost");

		connection = factory.newConnection();
		channel = connection.createChannel();

		channel.queueDeclare(CUSTOMERID_TO_TOKEN_QUEUE, false, false, false, null);

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				
				// Do something to the accunt status you received from payment service.
			}
		};
		channel.basicConsume(ACCUNT_STATUS_TO_CUSTOMER_QUEUE, true, consumer);
	}

	
	
	// Add customer
	public boolean addCustomer(String customerId, String cpr, String name, List<String> tokenList) throws Exception {

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

					return true;
				}
			} catch (NumberFormatException e) {
				throw new RequestRejected("Invalid format for CPR!");
			}
		}
	}
}
