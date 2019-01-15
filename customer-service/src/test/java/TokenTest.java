import static org.junit.Assert.*;

import org.junit.Test;
import beijing.customerservice.domain.Token;

public class TokenTest {
	Token token1 = new Token("123102");
	
	@Test
	public void getTokenDetails() {
		assertEquals(token1.getTokenid(), "123102");
			
	}
}
