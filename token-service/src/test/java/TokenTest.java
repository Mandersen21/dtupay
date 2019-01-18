import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import beijing.tokenservice.domain.Status;
import beijing.tokenservice.domain.Token;

public class TokenTest {
	private Token token;
	private String tokenId = "123456789";
	private String customerId = UUID.randomUUID().toString();
	
	@Before
	public void setUp() {
		token = new Token(tokenId, customerId, true, Status.ACTIVE, "");
	}
	
	@Test
	public void setAndGetTokenIdTest() {
		assertEquals(token.getTokenId(),tokenId);
	}
	
	@Test()
	public void setAndGetCustomerIdTest() {
		assertEquals(token.getCustomerId(), customerId);
	}
	
	@Test()
	public void setValidationStatusTest() {
		token.setValidtionStatus(false);
		assertEquals(token.getValidationStatus(), false);
	}
	
	@Test() 
	public void setStatusTest() {
		token.setStatus(Status.PENDING);
		assertEquals(token.getStatus(), Status.PENDING);
	}
}
