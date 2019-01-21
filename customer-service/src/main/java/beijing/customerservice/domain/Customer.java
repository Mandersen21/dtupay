package beijing.customerservice.domain;

import java.util.List;

public class Customer {
    private String id;
    private String name;
    private String cpr;
    private List<String> tokenList;
    private AccStatus status;

    public Customer(String id, String name, String cpr, List<String> list,AccStatus status) {
    	this.id = id;
    	this.name = name;
    	this.cpr = cpr;
    	this.tokenList = list;
    	this.status = status;
    }

    public AccStatus getStatus() {
		return status;
	}

	public void setStatus(AccStatus status) {
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