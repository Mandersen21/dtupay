package beijing.paymentservice.repository;

import java.util.List;

public interface IPaymentRepository {
	
	boolean createCustomer(IAccount customer);
	boolean createMerchant(IAccount merchant);
	IAccount getAccount(String id);
	IAccount getCustomerAccountByCPR(String cpr);
	boolean storeTransaction(Transaction t);
	Transaction getTrasaction(String tid);
	List<Transaction> getTransactions(String ownerId);
	
	
	

}
