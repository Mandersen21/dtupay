package beijing.tokenservice.repository;

public interface ICustomerRepository {

	public boolean addCustomer(String customerId);
	public String getCustomer(String customerId);
}
