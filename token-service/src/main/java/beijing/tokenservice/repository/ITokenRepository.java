package beijing.tokenservice.repository;

import java.util.List;

import beijing.tokenservice.domain.Token;

public interface ITokenRepository {

	public Token getToken(String tokenId);
	public boolean createToken(Token token);
	public boolean updateToken(Token token);
	public List<Token> getTokens();
	public List<Token> getTokensForCustomerId(String customerId);
}
