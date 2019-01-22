package beijing.tokenservice.repository;

import beijing.tokenservice.domain.Status;
import beijing.tokenservice.domain.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TokenRepository implements ITokenRepository {

    List<Token> tokenList;

    public TokenRepository() {
        tokenList = new ArrayList<Token>();
    }

    /**
     * Get token
     */
    public Token getToken(String tokenId) {
        for (Token c : tokenList) {
            if (c.getTokenId().contentEquals(tokenId)) {
                return c;
            }
        }
        return null;
    }
    
    /**
     * Create token based on provided token, returns null if token was already created.
     */
    public boolean createToken(Token token) {
        if (getToken(token.getTokenId()) != null) {
            return false;
        } else {
            tokenList.add(token);
            return true;
        }
    }

    /**
     * Updates token based on provided token
     */
    public boolean updateToken(Token token) {
        for (Token c : tokenList) {
            if (c.getTokenId().contentEquals(token.getTokenId())) {
                c.setStatus(token.getStatus());
                c.setValidtionStatus(token.getValidationStatus());
                return true;
            }
        }
        return false;
    }

    /**
     * Get every tokens created
     */
    public List<Token> getTokens() {
        return tokenList;
    }
    
    /**
     * Get tokens based on customerId as a list.
     */
    public List<Token> getTokensForCustomerId(String customerId) {
        return tokenList.stream().filter(t -> t.getCustomerId().contentEquals(customerId) && t.getValidationStatus() && t.getStatus().equals(Status.ACTIVE)).collect(Collectors.toList());
    }
    
    /**
     * Delete tokens based on customer id, will delete every tokens on the customer.
     */
	public boolean deleteTokens(String customerId) {
		if (customerId != null) {
			tokenList = tokenList.stream().filter(t -> !t.getCustomerId().contentEquals(customerId)).collect(Collectors.toList());
			return true;
		}
		else {
			tokenList = new ArrayList<Token>();
			return true;
		}
	}

}
