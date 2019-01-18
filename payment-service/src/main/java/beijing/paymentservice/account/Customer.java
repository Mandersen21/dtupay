package beijing.paymentservice.account;

public class Customer implements Account {
	private String name;
	private String cpr;
	public Customer(String expId, String expName) {
		this.name = expId;
		this.cpr = expName;
	}
	public String getID() {
		// TODO Auto-generated method stub
		return this.cpr;
	}
}
