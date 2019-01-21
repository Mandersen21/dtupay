package beijing.customerservice.domain;

import java.io.IOException;

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


public class CustomerManager {

	private final static String CUSTOMERID_TO_TOKENSERVICE_QUEUE = "customerid_to_tokenservice";
	private final static String CUSTOMERID_TO_PAYMENTSERVICE_QUEUE = "customerid_to_paymentservice";
	private final static String RPC_CUSTOMER_PAYMENT_REGITRATION = "rpc_customer_payment_registration";

	private ConnectionFactory factory;
	private Connection connection;
	private Channel channel;
	private Customer customer;

	public static ICustomerRepository customerRepository;
//	public static IPaymentRepository paymentRepository;

	public CustomerManager(ICustomerRepository _repository) throws ConnectionException, IOException, TimeoutException {
		customerRepository =_repository;

		factory = new ConnectionFactory();
		factory.setUsername("admin");
		factory.setPassword("Banana");

		factory.setHost("02267-bejing.compute.dtu.dk");
	
		connection = factory.newConnection();
		channel = connection.createChannel();
		channel.queueDeclare(CUSTOMERID_TO_TOKENSERVICE_QUEUE, false, false, false, null);
		channel.queueDeclare(RPC_CUSTOMER_PAYMENT_REGITRATION, false, false, false, null);
		
		// Listen for payment service that creates the account for the created customer
//		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//			String cpr = new String(delivery.getBody(), "UTF-8");
//			System.out.println("Message received: " + cpr);
//			paymentRepository.takeAccount(cpr);
//		};
//		channel.basicConsume(ACCOUNT_TO_CUSTOMERSERVICE_QUEUE, true, deliverCallback, consumerTag -> {
//		});
	}

	// Add customer
	public boolean addCustomer(String id, String name, String cpr, List<String> tokenList)
			throws RequestRejected, IOException, TimeoutException, CustomerNotFoundException {
		
		if (customerRepository.getCustomerByCpr(cpr) != null) {
			throw new RequestRejected("The customer " + cpr + " is already in the system!");
        } else {
		
			customer = new Customer(id, name, cpr, tokenList);
			
//			channel.basicPublish("", CUSTOMERID_TO_PAYMENTSERVICE_QUEUE, null, customer.getCpr().getBytes());
			
			
			
			final String corrId = UUID.randomUUID().toString();		
			String replyQueueName = channel.queueDeclare().getQueue();
			AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName)
					.build();

			String message = cpr+","+id;
			
			channel.basicPublish("", RPC_CUSTOMER_PAYMENT_REGITRATION, props, message.getBytes("UTF-8"));

			final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

			String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
				if (delivery.getProperties().getCorrelationId().equals(corrId)) {
					response.offer(new String(delivery.getBody(), "UTF-8"));
				}
			}, consumerTag -> {
			});

			try {
				String result = response.take();
				
				if(result.equals("VERIFIED"))
				channel.basicPublish("", CUSTOMERID_TO_TOKENSERVICE_QUEUE, null, customer.getId().getBytes());
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			channel.basicCancel(ctag);
				
			channel.close();
			connection.close();
			
			return customerRepository.createCustomer(customer);
        }
		
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
	
	// Get customer by cpr
	public Customer getCustomerByCpr(String cpr) throws CustomerNotFoundException {
		Customer customer = customerRepository.getCustomerByCpr(cpr);
		if (customer == null) {
			throw new CustomerNotFoundException("Customer not found");
		}
		return customer;
	}

	// Get all customers
	public List<Customer> getAllCustomers() {
		return customerRepository.getCustomers();
	}

}
