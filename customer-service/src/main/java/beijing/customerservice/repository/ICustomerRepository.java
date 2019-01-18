package beijing.customerservice.repository;

import java.util.List;
import beijing.customerservice.domain.Customer;

public interface ICustomerRepository {

	public boolean createCustomer(Customer customer);
	public Customer getCustomerById(String id);
	public boolean removeCustomer(Customer customer);
	public boolean customerExists(Customer customer);
	public List<String> getTokens(Customer customer);
	public List<Customer> getCustomers();
	
}
