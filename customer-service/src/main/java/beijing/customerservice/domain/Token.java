package beijing.customerservice.domain;

/**
 * 
 * For further implementation, the token class is made in order to easily check the tokens assigned to a specific customer.
 * @author BotezatuCristian
 *
 */

public class Token {

	private String tokenId;
	
	public Token(String tokenId) {
		this.tokenId = tokenId;
	}
	
	public String getTokenid() {
		return tokenId;
	}
}