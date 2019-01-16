package beijing.merchantservice.repository;

import java.util.List;

import beijing.merchantservice.domain.Merchant;
import beijing.merchantservice.domain.TokenValidation;
import beijing.merchantservice.domain.TransactionObject;
import beijing.merchantservice.exception.CorruptedTokenException;
import beijing.merchantservice.exception.DataAccessException;

public interface IMerchantRepositry {

	boolean createMerchant(Merchant merchant);
	boolean createTransaction(TransactionObject transaction) throws DataAccessException;
	boolean addToken(TokenValidation t) throws CorruptedTokenException;
	Merchant getMerchant(String uid);
	List<TransactionObject> getTransactions(String merchantUid);
	TokenValidation getTokenById(String tokenId) throws CorruptedTokenException;
}
