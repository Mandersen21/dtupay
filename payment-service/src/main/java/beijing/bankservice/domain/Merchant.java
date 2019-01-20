package beijing.bankservice.domain;

public class Merchant extends Account {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
