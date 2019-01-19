package beijing.paymentservice.repository;

public class CustomerAccount extends IAccount{
	
	private String cprNumber;
	
	
	public String getAccountViaCprNumber() {
		return cprNumber;
	}

}
