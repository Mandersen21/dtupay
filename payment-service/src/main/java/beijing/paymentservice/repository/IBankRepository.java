package beijing.paymentservice.repository;

import java.util.Map;

public interface IBankRepository {

	public boolean verifyBankCustomer(String cardId, String pwd);
	public Map<String, String> addBankCustomer(String cardId, String pwd);
}
