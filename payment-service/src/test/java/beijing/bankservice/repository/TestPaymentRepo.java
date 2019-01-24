package beijing.bankservice.repository;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.junit.Test;

import beijing.bankservice.domain.BankServiceManager;
import beijing.bankservice.exception.BankServiceException;
import beijing.bankservice.model.Account;
import beijing.bankservice.model.Transaction;
import beijing.bankservice.repository.PaymentRepository;
import beijing.bankservice.soap.BankService;
import beijing.bankservice.soap.BankServiceServiceLocator;
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
	public void testGetAccount(){
		
		//System.out.println(pRepo.accounts.size());
		assertEquals(pRepo.getAccount(id),null);
	}
	
	@Test
	public void testGetCustomerAccountByCPR() {
		assertEquals(pRepo.getCustomerAccountByCPR(cpr),null);
	}
	
	@Test
	public void testStoreTransaction() {
		assertEquals(pRepo.storeTransaction(ta),true);
	}
	
	@Test
	public void testGetTransactions() {
		assertEquals(pRepo.getTransactions(id).size(),0);
	}

	
	
}
