package beijing.paymentservice.repository;

import beijing.paymentservice.domain.Account;

public interface MerchantRepository {

	public void addMerchantAccount(Account account);
	public void getMerchantAccountDetail(Account account);

}
