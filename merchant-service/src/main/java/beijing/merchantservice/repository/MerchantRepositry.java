package beijing.merchantservice.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import beijing.merchantservice.domain.Merchant;
import beijing.merchantservice.domain.TokenValidation;
import beijing.merchantservice.domain.TransactionObject;
import beijing.merchantservice.exception.CorruptedTokenException;
import beijing.merchantservice.exception.DataAccessException;

public class MerchantRepositry implements IMerchantRepositry {

	List<Merchant> merchantList;
	List<TransactionObject> transactionsList;
	List<TokenValidation> tokenList;

	public MerchantRepositry() {
		merchantList = new ArrayList<>();
		transactionsList = new ArrayList<>();
		tokenList = new ArrayList<>();
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
	public boolean addToken(TokenValidation t) throws CorruptedTokenException {
		if(getTokenById(t.getTokenId()) != null) {
			throw new CorruptedTokenException("token already exist");
		}
		tokenList.add(t);
		return true;
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

	@Override
	public TokenValidation getTokenById(String tokenId) throws CorruptedTokenException {
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
