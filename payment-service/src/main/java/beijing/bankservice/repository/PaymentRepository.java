package beijing.bankservice.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

import beijing.bankservice.model.Account;
import beijing.bankservice.model.Transaction;


public class PaymentRepository implements IPaymentRepository{
	
	List<Account> accounts;
	List<Transaction> transacions;
	
	public PaymentRepository() {
		accounts = new ArrayList<Account>();
		transacions = new ArrayList<Transaction>();
	}

	@Override
	public boolean createAccount(Account account) {
		accounts.add(account);
		return true;
	}


	@Override
	public Account getAccount(String id) {
		for(Account a : accounts) {
			if(a.getDtuId().equals(id)) {
				return a;
			}
		}
		return null;
	}

	@Override
	public Account getCustomerAccountByCPR(String cpr) {
		for(Account a : accounts) {
			if(a.getUser().getCprNumber().equals(cpr)) {
				return a;
			}	
		}
		return null;
	}

	@Override
	public boolean storeTransaction(Transaction t) {
		transacions.add(t);
		return true;
	}

//	@Override
//	public Transaction getTrasaction(String tid) {
//		for(Transaction t : transacions) {
//			if(t.transactionId.equals(tid)) {
//				return t;
//			}
//		}
//		return null;
//	}

	public List<Transaction> getTransactions(String ownerId) {
		List<Transaction> merchantTrasactions = transacions.stream()
				.filter(t -> t.getCreditor().contentEquals(ownerId)||t.getDebtor().contentEquals(ownerId)).collect(Collectors.toList());
		return merchantTrasactions;
	}


}
