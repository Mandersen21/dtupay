package beijing.tokenservice.repository;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements ICustomerRepository {

	private List<String> customerList;

	public CustomerRepository() {
		customerList = new ArrayList<String>();
	}

	/**
	 * Add customer to the customer list
	 */
	public boolean addCustomer(String customerId) {
		if (getCustomer(customerId) != null) {
			return false;
		} else {
			customerList.add(customerId);
			return true;
		}
	}

	/**
	 * Get every customers
	 */
	public List<String> getCustomers() {
		return customerList;
	}

	/**
	 * Get customer based on provided customerId, returns null if not found.
	 */
	public String getCustomer(String customerId) {
		for (String c : customerList) {
            if (c.contentEquals(customerId)) {
                return c;
            }
        }
        return null;
	}
}
