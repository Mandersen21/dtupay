package beijing.PaymentService.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.concurrent.TimeoutException;

import javax.xml.rpc.ServiceException;

import org.junit.Before;
import org.junit.Test;

import beijing.bankservice.domain.BankServiceManager;
import beijing.bankservice.exception.BankServiceException;
import beijing.bankservice.model.Account;
import beijing.bankservice.model.Transaction;
import beijing.bankservice.model.User;
import beijing.bankservice.repository.IPaymentRepository;
import beijing.bankservice.repository.PaymentRepository;
import beijing.bankservice.soap.BankService;
import beijing.bankservice.soap.BankServiceServiceLocator;

public class TestBank {
	BankServiceManager man;
	PaymentRepository res;
	private BankService bankService;
	
	@Before
	public void setUp() throws IOException, TimeoutException, ServiceException {
		
	}

//	@Test
//	public void testBank() throws RemoteException, ServiceException {
////		PaymentServiceManager man = new PaymentServiceManager();
////		
////		man.setupMessageQueue();
//		BankService bank = new BankServiceServiceLocator().getBankServicePort();
//		String t = bank.createAccountWithBalance(new User("123987", "John", "John"), new BigDecimal(100));
//		System.out.println("Test 0 " + t);
//		
//		Account test = bank.getAccountByCprNumber("123987");
//		System.out.println("Test 1 " + test.getId());
//		
//		System.out.println(bank.getAccountByCprNumber("123987").getBalance());
//		System.out.println(bank.getAccounts().length);
//		assertEquals(test.getBalance(), new BigDecimal(100));
//		
//		
////		bank.retireAccount(test.getId());
//		
////		Account ty = bank.getAccountByCprNumber("123987");
//		//System.out.println("Test 3 " + bank.getAccount(t));
//		System.out.println(bank.getAccounts().length);
//		
//	}
	
//	@Test
//	public void deleteTestAccounts() throws ServiceException, BankServiceException, RemoteException {
//		BankService bank = new BankServiceServiceLocator().getBankServicePort();
//		System.out.println(bank.getAccounts().length);
//		bank.retireAccount(bank.getAccountByCprNumber("123987").getId());
//		bank.retireAccount(bank.getAccountByCprNumber("789456").getId());
//		System.out.println(bank.getAccounts().length);
//	}
//	@Test
//	public void makeAccounts() throws ServiceException, BankServiceException, RemoteException {
//		BankService bank = new BankServiceServiceLocator().getBankServicePort();
//		BigDecimal balance = new BigDecimal(100);
//		String cus = bank.createAccountWithBalance(new User("123987", "John", "John"), balance);
//		String mer = bank.createAccountWithBalance(new User("789456", "Mon", "Mon"), balance);
//		System.out.println(bank.getAccounts().length);
//	}
	

	
	
	@Test
	public void rabbitMQTest() throws IOException, TimeoutException, ServiceException {
		bankService =  new BankServiceServiceLocator().getBankServicePort();
		res = new PaymentRepository();
		BigDecimal balance = new BigDecimal(100);
		Transaction[] t = null;
        Transaction[] tv = null;       
        String t1 = bankService.getAccountByCprNumber("123987").getId();
        String t2 = bankService.getAccountByCprNumber("789456").getId();
        
        
		Account cusA = new Account(balance,t1, t, new User("123987", "John", "John"));
		cusA.setDtuId("123");
		Account merA = new Account(balance,t2, tv,new User("789456", "Mon", "Mon"));
		merA.setDtuId("321");
		res.createAccount(cusA);
		res.createAccount(merA);
		
		System.out.println("Customer: " + bankService.getAccountByCprNumber("123987").getBalance());
		System.out.println("Merchant: " + bankService.getAccountByCprNumber("789456").getBalance());
		
		
		
		man = new BankServiceManager(res);
		
	}
}

