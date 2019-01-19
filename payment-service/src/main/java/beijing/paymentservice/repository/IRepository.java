package beijing.paymentservice.repository;

import java.util.ArrayList;
import java.util.List;

import beijing.paymentservice.domain.Account;
import beijing.paymentservice.domain.Customer;
import beijing.paymentservice.domain.Merchant;

public class IRepository implements CustomerRepository, MerchantRepository {
	List<Customer> cList = new ArrayList<>();
	List<Merchant> mList = new ArrayList<>();

	@Override
	public void addCustomerAccount(Account acc) {
		cList.add((Customer) acc);
	}

	@Override
	public void getCustomerAccountDetail(Account account) {
		for (Customer c : cList) {
			if (c.getID() == account.getID()) {
				break;
			}
		}
		System.out.println("reached");
	}
	
	@Override
	public void addMerchantAccount(Account acc) {
		mList.add((Merchant) acc);
	}

	@Override
	public void getMerchantAccountDetail(Account account) {
		for (Merchant m : mList) {
			if (m.getID() == account.getID()) {
				break;
			}
		}
		System.out.println("reached");
	}
}
