package beijing.paymentservice.domain;

public class Customer implements IAccount {
	private String name;
	private String cpr;
	
	public Customer(String name, String cpr) {
		this.name = name;
		this.cpr = cpr;
	}
	
	public String getCpr() {
		return cpr;
	}
	
	public String getName() {
		return cpr;
	}


}
