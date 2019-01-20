package beijing.bankservice.repository;

import java.util.List;
import java.util.stream.Collectors;


public class PaymentRepository implements IPaymentRepository{
	
	List<IAccount> accounts;
	List<Transaction> transacions;

	@Override
	public boolean createCustomer(IAccount customer) {
		accounts.add(customer);
		return true;
	}

	@Override
	public boolean createMerchant(IAccount merchant) {
		accounts.add(merchant);
		return false;
	}

	@Override
	public IAccount getAccount(String id) {
		for(IAccount a : accounts) {
			if(a.ownerId.equals(id)) {
				return a;
			}
		}
		return null;
	}

	@Override
	public IAccount getCustomerAccountByCPR(String cpr) {
		for(IAccount a : accounts) {
			if(a instanceof CustomerAccount) {
				CustomerAccount c = (CustomerAccount)a;
				if(c.getAccountViaCprNumber().equals(cpr)) {
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

	@Override
	public Transaction getTrasaction(String tid) {
		for(Transaction t : transacions) {
			if(t.transactionId.equals(tid)) {
				return t;
			}
		}
		return null;
	}

	@Override
	public List<Transaction> getTransactions(String ownerId) {
		List<Transaction> merchantTrasactions = transacions.stream()
				.filter(t -> t.getTransactionId().contentEquals(ownerId)).collect(Collectors.toList());
		return merchantTrasactions;
	}

	@Override
	public IAccount takeAccount(String cpr) {
		for(IAccount a : accounts) {
			if(a.getOwnerId().equals(cpr)) {
				return a;
			}
		}
		return null;
	}

}
