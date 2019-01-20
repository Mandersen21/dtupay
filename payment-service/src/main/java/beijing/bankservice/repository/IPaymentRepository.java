package beijing.bankservice.repository;

import java.util.List;

import beijing.bankservice.model.Account;
import beijing.bankservice.model.Transaction;

public interface IPaymentRepository {
	
	boolean createCustomer(Account customer);
	boolean createMerchant(Account merchant);
	Account getAccount(String id);
	Account getCustomerAccountByCPR(String cpr);
	boolean storeTransaction(Transaction t);
//	Transaction getTrasaction(String tid);
	List<Transaction> getTransactions(String ownerId);

}
