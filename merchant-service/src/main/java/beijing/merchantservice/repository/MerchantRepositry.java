package beijing.merchantservice.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import beijing.merchantservice.domain.Merchant;
import beijing.merchantservice.domain.TransactionObject;
import beijing.merchantservice.exception.DataAccessException;

public class MerchantRepositry implements IMerchantRepositry {

	List<Merchant> merchantList;
	List<TransactionObject> transactionsList;

	public MerchantRepositry() {
		merchantList = new ArrayList<>();
		transactionsList = new ArrayList<>();
	}

	@Override
	public boolean createMerchant(Merchant uid) {
		if (merchantList.contains(uid)) {
			return false;
		}

		merchantList.add(uid);

		return true;

	}

	@Override
	public boolean createTransaction(TransactionObject transaction) throws DataAccessException {
		try{
			transactionsList.add(transaction);
			return true;
		}catch (Exception e){
			throw new DataAccessException("could not add log transaction");
		}
	}

	@Override
	public Merchant getMerchant(String uid) {
		if (uid == null) {
			return null;
		}
		for (Merchant m : merchantList) {
			if (m.getMerchantID().equals(uid)) {
				return m;
			}

		}
		return null;
	}

	@Override
	public List<TransactionObject> getTransactions(String merchantUid) {
		List<TransactionObject> merchantTrasactions = transactionsList.stream()
				.filter(m -> m.getMerchantId().contentEquals(merchantUid)).collect(Collectors.toList());
		return merchantTrasactions;
	}
}
