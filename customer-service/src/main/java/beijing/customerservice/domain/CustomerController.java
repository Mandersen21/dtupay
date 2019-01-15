package beijing.customerservice.domain;

import java.util.List;
import beijing.customerservice.domain.Customer;
import beijing.customerservice.exception.RequestRejected;
import beijing.customerservice.repository.CustomerRepository;

public class CustomerController {

	private Customer customer;
	CustomerRepository customerRepository;

	public CustomerController() {
		customerRepository = new CustomerRepository();
	}

	// Add customer
	public boolean addCustomer(String customerId, String cpr, String name, List<String> tokenList) throws Exception {

		if (name.matches(".*\\d+.*")) {
			throw new RequestRejected("Invalid name!");
		} else {
			try {
				Integer.parseInt(cpr);
				if (cpr.length() != 10) {
					throw new RequestRejected("Invalid CPR number!\nThe CPR has the following format:\nddmmyyxxxx ");
				} else {

					if (customerRepository.customerExists(new Customer(customerId, name, cpr, tokenList))) {
						throw new RequestRejected("The customer " + name + " already exists!");
					} else {

						customer = new Customer(customerId, name, cpr, tokenList);

					}
					customerRepository.createCustomer(customer);
					System.out.println("The customer" + customer.getCpr() + " is added to the system!");

					return true;
				}
			} catch (NumberFormatException e) {
				throw new RequestRejected("Invalid format for CPR!");
			}
		}
	}
}
