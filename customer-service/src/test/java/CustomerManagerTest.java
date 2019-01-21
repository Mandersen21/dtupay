import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
//
//	@Before
//	public void setUp() throws IOException, TimeoutException, ConnectionException {
//		customerManager = new CustomerManager(repository);
//	}
//
//	// Add tests
//	@Test()
//	public void addCustomerExistingTest() throws ConnectionException, IOException, TimeoutException, RequestRejected, CustomerNotFoundException {
//
//		List<Customer> customers = new ArrayList<Customer>();
//		customerManager = new CustomerManager(repository);
//		customerManager.addCustomer( "Cristian", "1423142342");
//		assertTrue(customers != null);
//		
//	}
//	
//	@Test(expected = RequestRejected.class)
//	public void addCustomerNotExistingTest() throws ConnectionException, IOException, TimeoutException, RequestRejected, CustomerNotFoundException {
//
//		List<Customer> customers = new ArrayList<Customer>();
//		customerManager = new CustomerManager(repository);
//		customerManager.addCustomer( "Mikkel", "2141235432");
//		
//	}
//	
//	// Remove tests
//	@Test()
//	public void removeCustomerExistingTest() throws ConnectionException, IOException, TimeoutException, RequestRejected, CustomerNotFoundException {
//		
//		customerManager = new CustomerManager(repository);
//		customerManager.addCustomer( "Mikkel", "2423142");
//		Customer c = customerManager.getCustomerByCpr("2423142");
//		
//		assertEquals(customerManager.removeCustomer(c), true);
//		
//	}
//	
//	@Test(expected = CustomerNotFoundException.class)
//	public void removeCustomerNotExistingTest() throws ConnectionException, IOException, TimeoutException, RequestRejected, CustomerNotFoundException {
//
//		customerManager = new CustomerManager(repository);
//		Customer c = customerManager.getCustomerByCpr("2141235432");
//		assertEquals(customerManager.removeCustomer(c), false);
//		
//	}
//	
//	// Id tests
//	@Test()
//	public void getCustomerExistingByIdTest() throws ConnectionException, IOException, TimeoutException, RequestRejected, CustomerNotFoundException {
//
//		customerManager = new CustomerManager(repository);
//		customerManager.addCustomer("Mikkel", "14232324");
//		Customer c = customerManager.getCustomerByCpr("14232324");
//		assertEquals(customerManager.getCustomerById(c.getId()) != null, true);
//		
//	}
//	
//	@Test(expected = CustomerNotFoundException.class)
//	public void getCustomerNotExistingByIdTest() throws ConnectionException, IOException, TimeoutException, RequestRejected, CustomerNotFoundException {
//
//		customerManager = new CustomerManager(repository);
//		assertEquals(customerManager.getCustomerById("12453") !=  null, false);
//		
//	}
//	
//	// Cpr tests
//	@Test()
//	public void getCustomerExistingByCprTest() throws ConnectionException, IOException, TimeoutException, RequestRejected, CustomerNotFoundException {
//
//		customerManager = new CustomerManager(repository);
//		assertEquals(customerManager.getCustomerByCpr("2141235432") != null, true);
//	}
//	
//	@Test(expected = CustomerNotFoundException.class)
//	public void getCustomerNotExistingByCprTest() throws ConnectionException, IOException, TimeoutException, RequestRejected, CustomerNotFoundException {
//
//		customerManager = new CustomerManager(repository);
//		assertEquals(customerManager.getCustomerByCpr("12453") !=  null, false);
//		
//	}
//	
//	// AllCustomer test
//	@Test()
//	public void getAllCustomersTest() throws ConnectionException, IOException, TimeoutException, RequestRejected, CustomerNotFoundException {
//
//		customerManager = new CustomerManager(repository);
//		customerManager.addCustomer( "Mikkel", "2141235432");
//		assertTrue(customerManager.getAllCustomers() != null);
//		
//	}
	
}
