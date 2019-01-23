package beijing.merchantservice.repository;

import java.util.List;

import beijing.merchantservice.domain.Merchant;
import beijing.merchantservice.domain.TokenValidation;
import beijing.merchantservice.domain.TransactionObject;
import beijing.merchantservice.exception.CorruptedTokenException;
import beijing.merchantservice.exception.DataAccessException;

public interface IMerchantRepository {

	/**
	 * stores a new merchant in the repository
	 * @param merchant
	 * @return
	 * @throws DataAccessException
	 */
	boolean createMerchant(Merchant merchant) throws DataAccessException;
	
	/**
	 * stores a new transactionObject in the repository.
	 * @param transaction
	 * @return
	 * @throws DataAccessException
	 */
	boolean createTransaction(TransactionObject transaction) throws DataAccessException;
	
	/**
	 * stores a new token in the repository.
	 * @param t
	 * @return
	 * @throws CorruptedTokenException
	 * @throws DataAccessException
	 */
	boolean addToken(TokenValidation t) throws CorruptedTokenException, DataAccessException;
	
	/**
	 * gets a merchant from the repository with a given id
	 * @param uid
	 * @return
	 * @throws DataAccessException
	 */
	Merchant getMerchantById(String uid)throws DataAccessException;
	
	/**
	 * gets a merchant from the repository with the given cvr number
	 * @param cvrNumber
	 * @return
	 * @throws DataAccessException
	 */
	Merchant getMerchantByCVR(String cvrNumber)throws DataAccessException;
	
	/**
	 * gets a token from the repository with the given token id.
	 * @param tokenId
	 * @return
	 * @throws CorruptedTokenException
	 * @throws DataAccessException
	 */
	TokenValidation getTokenById(String tokenId) throws CorruptedTokenException, DataAccessException;
	
	/**
	 * gets a list of all merchants stored in the repository.
	 * @return
	 * @throws DataAccessException
	 */
	List<Merchant> getMerchants()throws DataAccessException;
	
	/**
	 * gets all transactions requested by the merchant with the given id
	 * @param merchantUid
	 * @return
	 * @throws DataAccessException
	 */
	List<TransactionObject> getTransactions(String merchantUid)throws DataAccessException;
	
	/**
	 * get the complete list of tokens in the repository
	 * @return
	 * @throws DataAccessException
	 */
	List<TokenValidation> getTokenValidations() throws DataAccessException;
	
	
}
