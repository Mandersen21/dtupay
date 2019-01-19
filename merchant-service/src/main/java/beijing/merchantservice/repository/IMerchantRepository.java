package beijing.merchantservice.repository;

import java.util.List;

import beijing.merchantservice.domain.Merchant;
import beijing.merchantservice.domain.TokenValidation;
import beijing.merchantservice.domain.TransactionObject;
import beijing.merchantservice.exception.CorruptedTokenException;
import beijing.merchantservice.exception.DataAccessException;

public interface IMerchantRepository {

	boolean createMerchant(Merchant merchant) throws DataAccessException;
	boolean createTransaction(TransactionObject transaction) throws DataAccessException;
	boolean addToken(TokenValidation t) throws CorruptedTokenException, DataAccessException;
	Merchant getMerchantById(String uid)throws DataAccessException;
	Merchant getMerchantByCVR(String cvrNumber)throws DataAccessException;
	TokenValidation getTokenById(String tokenId) throws CorruptedTokenException, DataAccessException;
	List<Merchant> getMerchants()throws DataAccessException;
	List<TransactionObject> getTransactions(String merchantUid)throws DataAccessException;
	List<TokenValidation> getTokenValidations() throws DataAccessException;
	List<Merchant> getMerchantList() throws DataAccessException;
	
}
