package beijing.tokenservice.domain;

public class Token {

	private String tokenId;
	private String customerId;
	private boolean isValid;
	
	public Token(String tokenId, String custId, boolean isValid) {
		this.tokenId = tokenId;
		this.customerId = custId;
		this.isValid = isValid;
	}
	
	public String getTokenid() {
		return tokenId;
	}
	public String getCustId() {
		return customerId;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean b) {
		isValid = b;
	}
	
}
