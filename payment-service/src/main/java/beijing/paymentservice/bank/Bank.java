package beijing.paymentservice.bank;

import java.util.Map;

import beijing.paymentservice.account.Customer;
import beijing.paymentservice.repository.BankRepository;
import beijing.paymentservice.repository.IBankRepository;

public class Bank {
	private	static IBankRepository repo = new BankRepository();
	public boolean verifyBankCustomer(String cardId, String pwd) {
		
		return repo.verifyBankCustomer(cardId,pwd);
		//return false;
	}
	public Map<String, String> addBankCustomer(String cardId, String pwd) {
		return repo.addBankCustomer(cardId,pwd);
	}

}
