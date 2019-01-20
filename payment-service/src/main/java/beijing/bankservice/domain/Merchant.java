package beijing.bankservice.domain;

public class Merchant implements IAccount {
	
	private String name;
	private String cvr;
	
	public Merchant(String name, String cvr) {
		this.name = name;
		this.cvr = cvr;
	}

	public String getCpr() {
		return cvr;
	}
	
	public String getName() {
		return name;
	}

}
