package beijing.bankservice.repository;

public abstract class IAccount {
	
	protected String accountNr;
	protected String ownerId;
	
	
	public String getAccountNr() {
		return accountNr;
	}
	
	public String getOwnerId() {
		return ownerId;
	}
	
	public void setAccountNr(String accountNr) {
		this.accountNr = accountNr;
	}
	
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

}
