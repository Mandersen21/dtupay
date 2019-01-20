package beijing.bankservice.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import beijing.bankservice.domain.BankServiceManager;
import beijing.bankservice.repository.PaymentRepository;

@ApplicationPath("/")
public class RestApplication extends Application {
	static BankServiceManager bankServiceManager;
	
	public RestApplication() {
		if(bankServiceManager == null) {
			bankServiceManager = new BankServiceManager(new PaymentRepository());
		}
	}
}
