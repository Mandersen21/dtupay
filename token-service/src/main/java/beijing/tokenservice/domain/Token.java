package beijing.tokenservice.domain;

public class Token {

	private String tokenId;
	private String customerId;
	private boolean isValid;
	private Status status;
	
	public Token(String tokenId, String custId, boolean isValid, Status status) {
		this.tokenId = tokenId;
		this.customerId = custId;
		this.isValid = isValid;
		this.status = status;
	}
	
	public String getTokenId() {
		return tokenId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public boolean getValidationStatus() {
		return isValid;
	}

	public void setValidtionStatus(boolean s) {
		isValid = s;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
