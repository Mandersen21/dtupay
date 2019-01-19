package beijing.paymentservice.repository;

import beijing.paymentservice.account.Account;

public interface MerchantRepository {

	public void addMerchantAccount(Account account);
	public void getMerchantAccountDetail(Account account);

}
