package beijing.tokenservice.repository;

import beijing.tokenservice.domain.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TokenRepository implements ITokenRepository {

    List<Token> tokenList;

    public TokenRepository() {
        tokenList = new ArrayList<Token>();
    }

    public Token getToken(String tokenId) {
        for (Token c : tokenList) {
            if (c.getTokenId().contentEquals(tokenId)) {
                return c;
            }
        }
        return null;
    }

    public boolean createToken(Token token) {
        if (getToken(token.getTokenId()) != null) {
            return false;
        } else {
            tokenList.add(token);
            return true;
        }
    }

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

    public List<Token> getTokens() {
        return tokenList;
    }

    public List<Token> getTokensForCustomerId(String customerId) {
        return tokenList.stream().filter(t -> t.getCustomerId() == customerId && t.getValidationStatus() == true && t.getStatus().equals("ACTIVE")).collect(Collectors.toList());
    }

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
