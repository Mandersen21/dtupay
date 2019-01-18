package beijing.tokenservice.repository;

import beijing.tokenservice.domain.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TokenRepository implements ITokenRepository {

    List<Token> tokenList;
    List<String> customerList;

    public TokenRepository() {
        tokenList = new ArrayList<Token>();
        customerList = new ArrayList<String>();
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

    public List<Token> getTokenList() {
        return tokenList;
    }

    @Override
	public boolean addCustomer(String customerId) {
		if (getCustomer(customerId) != null) {
			return false;
		} else {
			customerList.add(customerId);
			return true;
		}
	}

	@Override
	public List<String> getCustomers() {
		return customerList;
	}

	@Override
	public String getCustomer(String customerId) {
		for (String c : customerList) {
            if (c.contentEquals(customerId)) {
                return c;
            }
        }
        return null;
	}


}
