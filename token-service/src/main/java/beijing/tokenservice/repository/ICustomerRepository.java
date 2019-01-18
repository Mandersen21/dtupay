package beijing.tokenservice.repository;

import java.util.List;

public interface ICustomerRepository {

	public boolean addCustomer(String customerId);
	public List<String> getCustomers();
	public String getCustomer(String customerId);
}
