package beijing.customerservice.domain;

import java.util.List;
import javax.swing.JOptionPane;
import beijing.customerservice.domain.Customer;
import beijing.customerservice.repository.CustomerRepository;

public class CustomerController {

	private Customer customer;
	CustomerRepository customerRepository;

	public CustomerController() {
		customerRepository = new CustomerRepository();
	}

	// Add customer
	public boolean addCustomer(String customerId, String cpr, String name, List<Token> tokenList) {

		if (name.matches(".*\\d+.*")) {
			JOptionPane.showMessageDialog(null, "Invalid name!", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				Integer.parseInt(cpr);
				if (cpr.length() != 10) {
					JOptionPane.showMessageDialog(null,
							"Invalid CPR number!\nThe CPR has the following format:\nddmmyyxxxx ", "Error",
							JOptionPane.ERROR_MESSAGE);

				} else {

					if (customerRepository.customerExists(new Customer(customerId, name, cpr, tokenList))) {
						JOptionPane.showMessageDialog(null, "The customer " + name + " already exists!", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {

						customer = new Customer(customerId, name, cpr, tokenList);

					}
					customerRepository.createCustomer(customer);
					JOptionPane.showMessageDialog(null, "The customer" + customer.getCpr() + " is added to the system!",
							"Customer added", JOptionPane.PLAIN_MESSAGE);

					return true;

				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Invalid format for CPR!", "Number Format Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		return false;
	}

}
