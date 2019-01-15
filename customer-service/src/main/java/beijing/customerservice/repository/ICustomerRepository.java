package beijing.customerservice.repository;

import java.util.List;
import beijing.customerservice.domain.Customer;

public interface ICustomerRepository {

	public Customer getCustomerById(String customerId);
	public Customer getCustomerByName(String customerId);
	public List<Customer> createCustomer(Customer customer);
	public List<Customer> removeCustomer(Customer customer);
	public boolean customerExists(Customer customer);
}
