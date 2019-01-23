package beijing.bankservice.repository;

import java.util.List;

import beijing.bankservice.model.Account;
import beijing.bankservice.model.Transaction;

public interface IPaymentRepository {
	
	/**
	 * stores a new account in the repository
	 * @param account
	 * @return
	 */
	boolean createAccount(Account account);
	
	/**
	 * retrieves the account with the id given by the 
	 * DTUPay service.
	 * @param id
	 * @return
	 */
	Account getAccount(String id);
	
	/**
	 * retrieves the account with the given cpr number
	 * @param cpr
	 * @return
	 */
	Account getCustomerAccountByCPR(String cpr);
	
	/**
	 * stores a transaction in the repository
	 * @param t
	 * @return
	 */
	boolean storeTransaction(Transaction t);
//	Transaction getTrasaction(String tid);
	List<Transaction> getTransactions(String ownerId);

}
