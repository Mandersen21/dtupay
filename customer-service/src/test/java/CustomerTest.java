import static org.junit.Assert.*;

import org.junit.Test;
import beijing.customerservice.domain.Customer;

public class CustomerTest {
	Customer Stephen = new Customer("Stephen", "Hawking", "1231027895");
	
	@Test
	public void getSetUserDetails() {
		
		Stephen.setPhonenumber(1234);
		assertEquals(Stephen.getPhonenumber(), 1234);
		
	}
}
