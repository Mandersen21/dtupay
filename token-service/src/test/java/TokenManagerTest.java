import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import beijing.tokenservice.domain.Status;
import beijing.tokenservice.domain.Token;
import beijing.tokenservice.domain.TokenManager;
import beijing.tokenservice.exception.DataAccessException;
import beijing.tokenservice.exception.RequestRejected;
import beijing.tokenservice.exception.TokenNotFoundException;
import beijing.tokenservice.repository.ITokenRepository;
import beijing.tokenservice.repository.TokenRepository;

public class TokenManagerTest {

	private static ITokenRepository repository = new TokenRepository();
	private TokenManager tokenManager;
	private Token token;

	@Before
	public void setUp() throws IOException, TimeoutException {
		tokenManager = new TokenManager(repository);
	}

	@Test()
	public void requestTokensSuccesTest() throws RequestRejected, TokenNotFoundException, DataAccessException {
		String customerId = UUID.randomUUID().toString();

		List<Token> tokens = new ArrayList<Token>();
		tokens = tokenManager.requestToken(customerId, 1);
		assertEquals(tokens.size(), 1);
	}

//	@Test()
//	public void requestTokensSuccesCustomerHaveOneActiveTokenTest()
//			throws RequestRejected, TokenNotFoundException, DataAccessException {
//		String customerId = UUID.randomUUID().toString();
//		token = new Token("123456", customerId, true, Status.ACTIVE);
//		repository.createToken(token);
//
//		List<Token> tokens = new ArrayList<Token>();
//		tokens = tokenManager.requestToken(customerId, 5);
//		assertEquals(tokens.size(), 5);
//	}
//
//	@Rule
//	public ExpectedException exception = ExpectedException.none();
//
//	@Test(expected = RequestRejected.class)
//	public void requestTokensDeniedTest() throws RequestRejected, TokenNotFoundException, DataAccessException {
//		String customerId = UUID.randomUUID().toString();
//		tokenManager.requestToken(customerId, 6);
//	}
//
//	@Test()
//	public void randomTokenGeneratorTest() {
//		String tokenNumber = tokenManager.generateRandomTokenNumber(6);
//		assertEquals(tokenNumber.length(), 6);
//	}
//
//	@Test()
//	public void isTokenValidTest() throws TokenNotFoundException {
//		token = new Token("123456", UUID.randomUUID().toString(), true, Status.ACTIVE);
//		repository.createToken(token);
//		assertEquals(tokenManager.isTokenValid(token.getTokenId()), true);
//	}
//
//	@Test()
//	public void isTokenInValidTest() throws TokenNotFoundException {
//		token = new Token("123456", UUID.randomUUID().toString(), true, Status.ACTIVE);
//		repository.createToken(token);
//		token.setValidtionStatus(false);
//		token.setStatus(Status.PAID);
//		repository.updateToken(token);
//		assertEquals(tokenManager.isTokenInvalid(token.getTokenId()), true);
//	}

}
