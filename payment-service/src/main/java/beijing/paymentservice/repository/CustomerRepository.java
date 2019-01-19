package beijing.paymentservice.repository;

import java.util.List;

import beijing.paymentservice.account.Account;
import beijing.paymentservice.account.Customer;

public interface CustomerRepository {

	public void addCustomerAccount(Account account);
	public void getCustomerAccountDetail(Account account);
				

}
