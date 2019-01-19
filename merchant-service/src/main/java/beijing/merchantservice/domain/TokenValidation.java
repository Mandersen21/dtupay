package beijing.merchantservice.domain;

public class TokenValidation {

    private boolean valid;
    private String tokenId;
    private String customerId;

    /**
     * 
     * @param valid
     * @param tokenId
     * @param customerId
     */
    public TokenValidation(boolean valid, String tokenId, String customerId) {
        this.valid = valid;
        this.tokenId = tokenId;
        this.customerId = customerId;
    }

    /**
     * 
     * @return
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * 
     * @param valid
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * 
     * @return
     */
    public String getTokenId() {
        return tokenId;
    }

    /**
     * 
     * @param tokenId
     */
    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    /**
     * 
     * @return
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * 
     * @param customerId
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
