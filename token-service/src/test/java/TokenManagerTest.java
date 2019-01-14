//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//
//import beijing.tokenservice.domain.Customer;
//import beijing.tokenservice.domain.Token;
//import beijing.tokenservice.domain.TokenManager;
//import beijing.tokenservice.exception.DataAccessException;
//import beijing.tokenservice.exception.RequestRejected;
//import beijing.tokenservice.exception.TokenNotFoundException;
//
//public class TokenManagerTest {
//
//	private TokenManager barcodeGenerator;
//	private Token token;
//
//	@Before
//	public void setUp() {
//		barcodeGenerator = new TokenManager();
//	}
//	
//	@Rule public ExpectedException exception = ExpectedException.none();
//
//	@Test()
//	public void requestTokensTest() {
//		try {
//			List<Token> tokens = new ArrayList<Token>();
//			tokens = barcodeGenerator.requestToken(null, "Mikkel", 1);
//			assertEquals(tokens.size(), 1);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//		
//	@Test(expected = RequestRejected.class)
//	public void requestTokensWhenCustomerHasManyTokensTest() throws RequestRejected, TokenNotFoundException, DataAccessException {
//		String custId = UUID.randomUUID().toString();
//
//		Token token1 = new Token("123456785", custId, true);
//		Token token2 = new Token("123456789", custId, true);
//
//		barcodeGenerator.createToken(token1);
//		barcodeGenerator.createToken(token2);
//
//		barcodeGenerator.dataAccess.createCustomer(new Customer("Mikkel", custId));
//		List<Token> tokens = new ArrayList<Token>();
//		barcodeGenerator.requestToken(custId, "Mikkel", 5);
//	}
//
//	@Test()
//	public void randomTokenGeneratorTest() {
//		String tokenNumber = barcodeGenerator.generateRandomTokenNumber(6);
//		assertEquals(tokenNumber.length(), 6);
//	}
//
//	@Test()
//	public void isTokenValidTest() throws TokenNotFoundException {
//		token = new Token("123456789", UUID.randomUUID().toString(), true);
//		barcodeGenerator.createToken(token);
//		assertEquals(barcodeGenerator.isTokenValid(token.getTokenid()), true);
//	}
//
//	@Test()
//	public void isTokenInValidTest() throws TokenNotFoundException {
//		token = new Token("123456789", UUID.randomUUID().toString(), true);
//		barcodeGenerator.createToken(token);
//		barcodeGenerator.useToken(token.getTokenid());
//		assertEquals(barcodeGenerator.isTokenInvalid(token.getTokenid()), true);
//	}
//}
