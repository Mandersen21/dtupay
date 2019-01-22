import static org.junit.Assert.*;

import org.junit.Test;
import beijing.customerservice.domain.Token;

public class TokenTest {
	Token token1 = new Token("123102");
	
	/**
	 * Test the getter for the token class (found in the customer-service).
	 */
	
	@Test
	public void getTokenDetails() {
		assertEquals(token1.getTokenid(), "123102");
			
	}
}
