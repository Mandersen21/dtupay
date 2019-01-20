package beijing.bankservice.model;

public class Customer extends Account {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
