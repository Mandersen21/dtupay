package beijing.customerservice.domain;

import java.util.ArrayList;
import java.util.List;

public class Customer {

	// Attributes of user
	public String name;
	List<Token> tokenList;

	String cpr;
	String id;
	
	// Constructor
	public Customer(String customerId, String cpr, String name, List<Token> tokenList) {
		this.name = name;
		this.setCpr(cpr);
		this.setId(customerId);
		tokenList = new ArrayList<Token>();
	}

	// First name
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	// Cpr
	public String getCpr() {
		return cpr;
	}

	public void setCpr(String cpr) {
		this.cpr = cpr;
	}
	
	// Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
