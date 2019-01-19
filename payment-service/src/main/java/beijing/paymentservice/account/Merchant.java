package beijing.paymentservice.account;

public class Merchant implements Account {
	private String name;
	private String cvr;
	public Merchant(String expId, String expName) {
		this.name = expId;
		this.cvr = expName;
	}

	@Override
	public String getID() {
		return this.cvr;
	}

}
