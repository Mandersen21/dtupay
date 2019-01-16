package beijing.merchantservice.repository;

import java.util.List;

import beijing.merchantservice.domain.Merchant;
import beijing.merchantservice.domain.TransactionObject;
import beijing.merchantservice.exception.DataAccessException;

public interface IMerchantRepositry {

	boolean createMerchant(Merchant merchant);
	boolean createTransaction(TransactionObject transaction) throws DataAccessException;
	Merchant getMerchant(String uid);
	List<TransactionObject> getTransactions(String merchantUid);

	
	
	
}
