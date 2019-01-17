package beijing.customerservice.repository;

import java.util.List;
import beijing.customerservice.domain.Customer;

public interface ICustomerRepository {

	public String generateRandomCustomerId(int length);
	public String addCustomer(String name, String cpr, List<String> tokenList);
	public List<Customer> getCustomerList();
	public Customer getCustomerById(String id);
	public List<Customer> removeCustomer(Customer customer);
	public boolean customerExists(Customer customer);
	public List<String> getTokens(Customer customer);
	
}
