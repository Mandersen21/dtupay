package beijing.merchantservice.domain;

public class TokenValidation {

    private boolean valid;
    private String tokenId;
    private String customerId;


    public TokenValidation(boolean valid, String tokenId, String customerId) {
        this.valid = valid;
        this.tokenId = tokenId;
        this.customerId = customerId;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
