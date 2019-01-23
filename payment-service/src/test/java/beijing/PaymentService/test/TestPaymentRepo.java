package beijing.PaymentService.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import beijing.bankservice.model.Account;
import beijing.bankservice.model.Transaction;
import beijing.bankservice.repository.PaymentRepository;
public class TestPaymentRepo {
	PaymentRepository pRepo= new PaymentRepository();
	Account acc = new Account();
	Transaction ta = new Transaction();
	String id = "wen";
	String cpr = "1234567890";
	@Test
	public void testCreateAccount() {
		assertEquals(pRepo.createAccount(acc),true);
	}
	
	@Test
	public void testGetAccount() {
		assertEquals(pRepo.getAccount(id),null);
	}
	
	@Test
	public void testGetCustomerAccountByCPR() {
		assertEquals(pRepo.getCustomerAccountByCPR(cpr),null);
	}
	
	@Test
	public void testStoreTransaction() {
		assertEquals(pRepo.storeTransaction(ta),null);
	}
	
	@Test
	public void testGetTransactions() {
		assertEquals(pRepo.getTransactions(id),null);
	}
	
	
	
}
