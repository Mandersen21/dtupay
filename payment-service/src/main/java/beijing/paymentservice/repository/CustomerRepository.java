package beijing.paymentservice.repository;

import java.util.List;

import beijing.paymentservice.domain.Account;
import beijing.paymentservice.domain.Customer;

public interface CustomerRepository {

	public void addCustomerAccount(Account account);
	public void getCustomerAccountDetail(Account account);
				

}
