package beijing.tokenservice.domain;

import java.util.List;

import beijing.tokenservice.repository.ICustomerRepository;
import beijing.tokenservice.repository.ITokenRepository;

public class CustomerManager {

	public static ICustomerRepository repository;
	
	public CustomerManager(ICustomerRepository _repository) {
		repository = _repository;
	}
	
	public List<String> getCustomers() {
		return repository.getCustomers();
	}
}
