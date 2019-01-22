package beijing.merchantservice.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TokenValidationTest {

	@Test
	public void constructorNotNullTest() {
		TokenValidation tokenVal = new TokenValidation(true, "123", "123456");
		assertNotNull(tokenVal);
	}
	
	@Test
	public void getCustomerIdTest() {
		TokenValidation tokenVal = new TokenValidation(true, "123", "123456");
		String actual = tokenVal.getCustomerId();
		String expected = "123456";
		assertEquals(expected, actual);
	}
	
	@Test
	public void getTokenIdTest() {
		TokenValidation tokenVal = new TokenValidation(true, "123", "123456");
		String actual = tokenVal.getTokenId();
		String expected = "123";
		assertEquals(expected, actual);
	}
	
	@Test
	public void getValidTest() {
		TokenValidation tokenVal = new TokenValidation(true, "123", "123456");
		boolean actual = tokenVal.isValid();
		boolean expected = true;
		assertEquals(expected, actual);
	}
	
}
