package beijing.bankservice.domain;

import beijing.bankservice.repository.IPaymentRepository;
import beijing.bankservice.repository.PaymentRepository;

public class StartUp {
	static BankServiceManager bankServiceManager;
	private static IPaymentRepository repository = new PaymentRepository();

	public static void main(String[] args) throws Exception {
		new StartUp().startUp();
	}

	private void startUp() throws Exception {
		if(bankServiceManager == null) {
			bankServiceManager = new BankServiceManager(repository);
		}
	}
}