package beijing.bankservice.repository;

import java.util.List;
import java.util.stream.Collectors;

import beijing.bankservice.model.Account;
import beijing.bankservice.model.Customer;
import beijing.bankservice.model.Transaction;


public class PaymentRepository implements IPaymentRepository{
	
	List<Account> accounts;
	List<Transaction> transacions;

	@Override
	public boolean createCustomer(Account customer) {
		accounts.add(customer);
		return true;
	}

	@Override
	public boolean createMerchant(Account merchant) {
		accounts.add(merchant);
		return false;
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
			if(a instanceof Account) {
				Customer c = (Customer)a;
				if(c.getCpr().equals(cpr)) {
					return c;
				}
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
