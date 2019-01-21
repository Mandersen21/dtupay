import static org.junit.Assert.*;

import org.junit.Test;

import beijing.customerservice.domain.AccStatus;
import beijing.customerservice.domain.Customer;

public class CustomerTest {
	Customer Stephen = new Customer("123", "Stephen", "123102", null, AccStatus.UNVERIFIED);
	
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
		
	}
}
