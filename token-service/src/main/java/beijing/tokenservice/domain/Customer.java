package beijing.tokenservice.domain;

public class Customer {

	private String name;
	private String uniqueId;
	
	public Customer(String name, String custid) {
		this.name = name;
		this.uniqueId = custid;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUniqueId() {
		return uniqueId;
	}
	
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	
}
