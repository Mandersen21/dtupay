package beijing.tokenservice.repository;

import java.util.ArrayList;
import java.util.List;

import beijing.tokenservice.domain.Token;

public class CustomerRepository implements ICustomerRepository {

	private List<String> customerList;

	public CustomerRepository() {
		customerList = new ArrayList<String>();
	}

	@Override
	public boolean addCustomer(String customerId) {
		if (getCustomer(customerId) != null) {
			return false;
		} else {
			customerList.add(customerId);
			return true;
		}
	}

	@Override
	public List<String> getCustomers() {
		return customerList;
	}

	@Override
	public String getCustomer(String customerId) {
		for (String c : customerList) {
            if (c.contentEquals(customerId)) {
                return c;
            }
        }
        return null;
	}
}
