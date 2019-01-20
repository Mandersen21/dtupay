package beijing.PaymentService.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.junit.Test;

import beijing.bankservice.domain.Account;
import beijing.bankservice.domain.User;
import beijing.bankservice.soap.BankService;
import beijing.bankservice.soap.BankServiceServiceLocator;

public class TestBank {

	@Test
	public void testBank() throws RemoteException, ServiceException {
//		PaymentServiceManager man = new PaymentServiceManager();
//		
//		man.setupMessageQueue();
		BankService bank = new BankServiceServiceLocator().getBankServicePort();
		String t = bank.createAccountWithBalance(new User("123987", "John", "John"), new BigDecimal(100));
		System.out.println("Test 0 " + t);
		
		Account test = bank.getAccountByCprNumber("123987");
		System.out.println("Test 1 " + test.getId());
		
		System.out.println(bank.getAccountByCprNumber("123987").getBalance());
		System.out.println(bank.getAccounts().length);
		assertEquals(test.getBalance(), new BigDecimal(100));
		bank.retireAccount(test.getId());
		
//		Account ty = bank.getAccountByCprNumber("123987");
		//System.out.println("Test 3 " + bank.getAccount(t));
		System.out.println(bank.getAccounts().length);
		
	}
}

