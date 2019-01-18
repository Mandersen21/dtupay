package beijing.paymentservice.manager;

import beijing.paymentservice.bank.Bank;

public class PaymentServiceManager {
	public boolean verifyBankCustomer(String cardId, String pwd) {
		Bank bank = new Bank();
		return bank.verifyBankCustomer(cardId, pwd);
	}
	
	
}
