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


public class CustomerManager {

	private final static String CUSTOMERID_TO_TOKENSERVICE_QUEUE = "customerid_to_tokenservice";
	private final static String CUSTOMER_PAYMENT_REGITRATION = "customer_payment_registration";
	private final static String PAYMENT_CUSTOMER_REGITRATION = "payment_customer_registration";
	private final static String RPC_CUSTOMER_PAYMENT_REGITRATION = "rpc_customer_payment_registration";

	private ConnectionFactory factory;
	private Connection connection;
	private Channel channel;
	private Customer customer;

	public static ICustomerRepository customerRepository;
//	public static IPaymentRepository paymentRepository;

	public CustomerManager(ICustomerRepository _repository) throws IOException, TimeoutException  {
		customerRepository =_repository;

		setupMessageQueue();

		
	}
	
	
	private void setupMessageQueue() throws IOException, TimeoutException {
		
		factory = new ConnectionFactory();
		factory.setUsername("admin");
		factory.setPassword("Banana");
		factory.setHost("02267-bejing.compute.dtu.dk");
		connection = factory.newConnection();
		
		
		channel = connection.createChannel();
		channel.queueDeclare(CUSTOMERID_TO_TOKENSERVICE_QUEUE, false, false, false, null);
		channel.queueDeclare(CUSTOMER_PAYMENT_REGITRATION, false, false, false, null);
		channel.queueDeclare(PAYMENT_CUSTOMER_REGITRATION, false, false, false, null);
		
//		Listen for payment service that creates the account for the created customer
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody());
			String[] paymentvalues = message.split(",");
			String customerId = paymentvalues[0];
			AccStatus accountStatus = paymentvalues[1] == "VERIFIED" ? AccStatus.VERIFIED : AccStatus.UNVERIFIED;
			
			Customer customer = customerRepository.getCustomerById(customerId);
			customer.setStatus(accountStatus);
			customerRepository.updateCustomer(customer);
			
			if(accountStatus.equals(AccStatus.VERIFIED)) {	
				channel.basicPublish("", CUSTOMERID_TO_TOKENSERVICE_QUEUE, null, customer.getId().getBytes());
			}
		};
		channel.basicConsume(PAYMENT_CUSTOMER_REGITRATION, true, deliverCallback, consumerTag -> {
		});		
	}
	
	

	// Add customer
	public Customer addCustomer(String name, String cpr)
			throws RequestRejected, IOException, TimeoutException, CustomerNotFoundException {
		String id = UUID.randomUUID().toString();
		
		if (customerRepository.getCustomerByCpr(cpr) != null) {
			throw new RequestRejected("The customer " + cpr + " is already in the system!");
        } else {
		
			Customer  c = new Customer(id, name, cpr, new ArrayList<String>(), AccStatus.UNVERIFIED);
			
			channel.basicPublish("", CUSTOMER_PAYMENT_REGITRATION, null, c.getCpr().getBytes());
			
//			
//					
//			final String corrId = UUID.randomUUID().toString();		
//			String replyQueueName = channel.queueDeclare().getQueue();
//			AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName)
//					.build();
//
//			String message = cpr+","+id;
//			
//			channel.basicPublish("", RPC_CUSTOMER_PAYMENT_REGITRATION, props, message.getBytes("UTF-8"));
//
//			final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);
//
//			String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
//				if (delivery.getProperties().getCorrelationId().equals(corrId)) {
//					response.offer(new String(delivery.getBody(), "UTF-8"));
//				}
//			}, consumerTag -> {
//			});
//
//			try {
//				String result = response.take();
//				
//				if(result.equals("VERIFIED"))
//				channel.basicPublish("", CUSTOMERID_TO_TOKENSERVICE_QUEUE, null, customer.getId().getBytes());
//				
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		
//			channel.basicCancel(ctag);
				
			channel.close();
			connection.close();
			
			return c;
        }
		
	}

	// Remove customer
	public boolean removeCustomer(Customer customer) throws CustomerNotFoundException {
		if (customerRepository.customerExists(customer)) {
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
