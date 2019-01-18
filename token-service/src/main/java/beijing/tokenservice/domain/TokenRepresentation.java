package beijing.tokenservice.domain;

public class TokenRepresentation {

	private String tokenId;
	private String path;
	
	public TokenRepresentation(String tokenId, String tokenPath) {
		this.tokenId = tokenId;
		this.path = tokenPath;
	}

	public String getTokenPath() {
		return path;
	}

	public void setTokenPath(String tokenPath) {
		this.path = tokenPath;
	}
	
	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String id) {
		this.tokenId = id;
	}
	
}
