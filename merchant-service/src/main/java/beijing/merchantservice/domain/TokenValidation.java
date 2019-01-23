package beijing.merchantservice.domain;


public class TokenValidation {

    private boolean valid;
    private String tokenId;
    private String customerId;

    /**
     * Token validation is a representation of the token kept in the 
     * database for the sake of validating tokens.
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
     * returns if the token is valid
     * @return token validation
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * set the validity of the token.
     * @param valid
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * gets the id of the token.
     * @return tokenId
     */
    public String getTokenId() {
        return tokenId;
    }

    /**
     * set the token id.
     * @param tokenId
     */
    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    /**
     * get the customerId of the token
     * @return customer id
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * set the customer id for the token
     * @param customerId
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
