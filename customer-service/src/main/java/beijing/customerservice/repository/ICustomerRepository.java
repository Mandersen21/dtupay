package beijing.customerservice.repository;

import java.util.List;
import beijing.customerservice.domain.Customer;

/**
 * 
 * The ICustomerRepository interface allows the fulfillment of high-cohesion and low-coupling design features (making our code more like
 * open for improvements, but close for changes).
 * @author BotezatuCristian
 *
 */

public interface ICustomerRepository {

	public boolean createCustomer(Customer customer);
	public Customer getCustomerById(String id);
	public Customer getCustomerByCpr(String cpr);
	public boolean removeCustomer(Customer customer);
	public boolean customerExists(Customer customer);
	public boolean updateCustomer(Customer customer);
	public List<String> getTokens(Customer customer);
	public List<Customer> getCustomers();
	
}
