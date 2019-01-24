package beijing.bankservice.repository;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.junit.Test;

import beijing.bankservice.exception.BankServiceException;
import beijing.bankservice.model.Account;
import beijing.bankservice.soap.BankService;
import beijing.bankservice.soap.BankServiceServiceLocator;

public class BalanceCheck {
	@Test
	public void getAccounts() throws BankServiceException, RemoteException, ServiceException {
		BankService bs  = new BankServiceServiceLocator().getBankServicePort();
		
		Account customer  = bs.getAccountByCprNumber("0101010101");
		Account merchant = bs.getAccountByCprNumber("0202020202");
		
		System.out.println("customer balance: "+customer.getBalance());
		System.out.println("merhcant balance: "+merchant.getBalance());
		
	}
	

}
