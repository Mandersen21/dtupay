package beijing.merchantservice.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import beijing.merchantservice.domain.Merchant;
import beijing.merchantservice.domain.TokenValidation;
import beijing.merchantservice.domain.TransactionObject;
import beijing.merchantservice.exception.CorruptedTokenException;
import beijing.merchantservice.exception.DataAccessException;

public class MerchantRepository implements IMerchantRepository {

	List<Merchant> merchantList;
	List<TransactionObject> transactionsList;
	List<TokenValidation> tokenList;


	public MerchantRepository() {
		merchantList = new ArrayList<>();
		transactionsList = new ArrayList<>();
		tokenList = new ArrayList<>();
	}


	
	/**
	 * 
	 * @return operation success 
	 */
	public boolean createMerchant(Merchant uid) throws DataAccessException {
		if (merchantList.contains(uid)) {
			return false;
		}

		merchantList.add(uid);

		return true;

	}

	/**
	 * 
	 * @return operation success
	 */
	public boolean createTransaction(TransactionObject transaction) throws DataAccessException {
		try{
			transactionsList.add(transaction);
			return true;
		}catch (Exception e){
			throw new DataAccessException("could not add log transaction");
		}
	}

	/**
	 * 
	 * @return operation success
	 */
	public boolean addToken(TokenValidation t) throws CorruptedTokenException, DataAccessException {
		if(getTokenById(t.getTokenId()) != null) {
			throw new CorruptedTokenException("token already exist");
		}
		tokenList.add(t);
		return true;
	}

	/**
	 * 
	 * @return 
	 */
	public Merchant getMerchant(String uid) throws DataAccessException {
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

	/**
	 * 
	 * @return
	 */
	public List<Merchant> getMerchants(){
		return this.merchantList;		
	}
	
	/**
	 * 
	 * @return
	 */
	public List<TokenValidation> getTokenValidation(){
		return this.tokenList;		
	}
	
	/**
	 * 
	 * @return
	 */
	public List<TransactionObject> getTransactions(String merchantUid) throws DataAccessException {
		List<TransactionObject> merchantTrasactions = transactionsList.stream()
				.filter(m -> m.getMerchantId().contentEquals(merchantUid)).collect(Collectors.toList());
		return merchantTrasactions;
	}

	/**
	 * 
	 * @return
	 */
	public TokenValidation getTokenById(String tokenId) throws CorruptedTokenException, DataAccessException {
		List<TokenValidation> resultToken = tokenList.stream()
				.filter(t -> t.getTokenId().contentEquals(tokenId)).collect(Collectors.toList());

		if(resultToken.size()>1){
			throw new CorruptedTokenException("multiple tokens with same id");
		}else if(resultToken.isEmpty()){
			return null;
		}

		return resultToken.get(0);
	}
	
	
}
