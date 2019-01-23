package beijing.bankservice.rest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import beijing.bankservice.domain.BankServiceManager;
import beijing.bankservice.repository.IPaymentRepository;
import beijing.bankservice.repository.PaymentRepository;

@ApplicationPath("/")
public class RestApplication extends Application {

	private static IPaymentRepository repository = new PaymentRepository();
	private BankServiceManager manager;
	
	/**
	 * instantiate the bank-service 
	 * with a predefined  BankServiceManager and repository.
	 */
	public RestApplication() {
		try {
			manager = new BankServiceManager(repository);
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}
}
