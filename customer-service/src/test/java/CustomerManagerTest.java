import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Test;

import beijing.customerservice.domain.Customer;
import beijing.customerservice.domain.CustomerManager;
import beijing.customerservice.exception.ConnectionException;
import beijing.customerservice.exception.CustomerNotFoundException;
import beijing.customerservice.exception.RequestRejected;
import beijing.customerservice.repository.CustomerRepository;
import beijing.customerservice.repository.ICustomerRepository;

public class CustomerManagerTest {
	
	private static ICustomerRepository repository = new CustomerRepository();
	private CustomerManager customerManager;
	private Customer customer;

	@Before
	public void setUp() throws IOException, TimeoutException, ConnectionException {
		customerManager = new CustomerManager(repository);
	}

	@Test()
	public void addCustomerSuccesTest() throws ConnectionException, IOException, TimeoutException, RequestRejected {

		List<Customer> customers = new ArrayList<Customer>();
		customerManager = new CustomerManager(repository);
		customerManager.addCustomer("123", "Mikkel", "2141235432", null);
		assertTrue(customers != null);
		
	}
	
	@Test(expected = CustomerNotFoundException.class)
	public void removeCustomerSuccesTest() throws ConnectionException, IOException, TimeoutException, RequestRejected, CustomerNotFoundException {

		customerManager = new CustomerManager(repository);
		customerManager.removeCustomer(new Customer("123", "Mikkel", "2141235432", null));
		assertEquals(customerManager.getCustomerById("123"), false);
		
	}
	
	@Test(expected = AssertionError.class)
	public void removeCustomerTest() throws ConnectionException, IOException, TimeoutException, RequestRejected, CustomerNotFoundException {

		customerManager = new CustomerManager(repository);
		customerManager.addCustomer("123", "Mikkel", "2141235432", null);
		customerManager.removeCustomer(new Customer("123", "Mikkel", "2141235432", null));
		assertEquals(customerManager.getCustomerById("123"), false);
		
	}
	
	@Test(expected = CustomerNotFoundException.class)
	public void getCustomerByIdTest() throws ConnectionException, IOException, TimeoutException, RequestRejected, CustomerNotFoundException {

		customerManager = new CustomerManager(repository);
		assertEquals(customerManager.getCustomerById("12453"), false);
		
	}
	
	@Test()
	public void getAllCustomersTest() throws ConnectionException, IOException, TimeoutException, RequestRejected, CustomerNotFoundException {

		customerManager = new CustomerManager(repository);
		customerManager.addCustomer("123", "Mikkel", "2141235432", null);
		assertTrue(customerManager.getAllCustomers() != null);
		
	}
	
}
