package beijing.customerservice.domain;

import java.util.List;

/**
 * Customer class contains mostly the getters and setters for customer. 
 * The feature of interest are id, name, cpr, tokenList assigned to the customer and the status of the customer.
 * @author BotezatuCristian
 *
 */
public class Customer {
    private String id;
    private String name;
    private String cpr;
    private List<String> tokenList;
    private AccountStatus status;

    public Customer(String id, String name, String cpr, List<String> list,AccountStatus status) {
    	this.id = id;
    	this.name = name;
    	this.cpr = cpr;
    	this.tokenList = list;
    	this.status = status;
    }

    public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public void setTokenList(List<String> tokenList) {
        this.tokenList = tokenList;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpr() {
        return cpr;
    }

    public List<String> getTokenList() {
        return tokenList;
    }
    
    
}