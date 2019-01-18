package beijing.paymentservice.bank;

import beijing.paymentservice.repository.BankRepository;
import beijing.paymentservice.repository.IBankRepository;

public class Bank {
	private	static IBankRepository repo = new BankRepository();
	public boolean verifyBankCustomer(String cardId, String pwd) {
		
		return repo.verifyBankCustomer(cardId,pwd);
		//return false;
	}

}
