import static org.junit.Assert.*;

import org.junit.Test;
import beijing.customerservice.domain.AccountStatus;
import beijing.customerservice.domain.Customer;

public class CustomerTest {
	Customer Stephen = new Customer("123", "Stephen", "123102", null, AccountStatus.UNVERIFIED);
	
	/**
	 * Test the getters and setters for the Customer class.
	 */
	
	@Test
	public void getSetUserDetails() {
		
		Stephen.setName("Stephan");
		assertEquals(Stephen.getName(), "Stephan");
		
		Stephen.setCpr("123102");
		assertEquals(Stephen.getCpr(), "123102");
		
		Stephen.setId("123");
		assertEquals(Stephen.getId(), "123");
		
		Stephen.setTokenList(null);
		assertEquals(Stephen.getTokenList(), null);
		
		Stephen.setStatus(AccountStatus.VERIFIED);
		assertEquals(Stephen.getStatus(), AccountStatus.VERIFIED);
		
	}
}
