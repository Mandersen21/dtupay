package beijing.PaymentService.test;

import org.junit.Test;
import static org.junit.Assert.*;

import beijing.paymentservice.manager.PaymentServiceManager;

public class testPaymentService {
	@Test
	public void testVerifyBankCustomer() {
		String testId = "210104198704112817";
		String pwd = "02267";
		PaymentServiceManager manager = new PaymentServiceManager();
		boolean result = manager.verifyBankCustomer(testId, pwd);
		assertFalse(result);
	}
	
	
	
	
}