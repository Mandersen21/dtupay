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
import beijing.tokenservice.domain.TokenRepresentation;
import beijing.tokenservice.exception.CustomerNotFoundException;
import beijing.tokenservice.exception.DataAccessException;
import beijing.tokenservice.exception.RequestRejected;
import beijing.tokenservice.exception.TokenNotFoundException;
import beijing.tokenservice.repository.CustomerRepository;
import beijing.tokenservice.repository.ICustomerRepository;
import beijing.tokenservice.repository.ITokenRepository;
import beijing.tokenservice.repository.TokenRepository;

public class TokenManagerTest {

	private static ITokenRepository tRepository = new TokenRepository();
	private static ICustomerRepository cRepository = new CustomerRepository();
	
	private TokenManager tokenManager;
	private Token token;

	@Before
	public void setUp() throws IOException, TimeoutException {
		tokenManager = new TokenManager(tRepository, cRepository);
	}

	@Test()
	public void requestTokensSuccesTest() throws RequestRejected, TokenNotFoundException, DataAccessException, IOException, TimeoutException, CustomerNotFoundException {
		String customerId = UUID.randomUUID().toString();
		cRepository.addCustomer(customerId);
		List<TokenRepresentation> tokens = new ArrayList<TokenRepresentation>();
		tokens = tokenManager.requestToken(customerId, 1);
		assertEquals(tokens.size(), 1);
	}

	@Test()
	public void requestTokensSuccesCustomerHaveOneActiveTokenTest()
			throws RequestRejected, TokenNotFoundException, DataAccessException, IOException, TimeoutException, CustomerNotFoundException {
		String customerId = UUID.randomUUID().toString();
		cRepository.addCustomer(customerId);
		token = new Token("123456", customerId, true, Status.ACTIVE, "");
		tRepository.createToken(token);

		List<TokenRepresentation> tokens = new ArrayList<TokenRepresentation>();
		tokens = tokenManager.requestToken(customerId, 5);
		assertEquals(tokens.size(), 5);
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();
	

	@Test(expected = RequestRejected.class)
	public void requestTokensDeniedTest() throws RequestRejected, TokenNotFoundException, DataAccessException, IOException, TimeoutException, CustomerNotFoundException {
		String customerId = UUID.randomUUID().toString();
		tokenManager.requestToken(customerId, 6);
	}

	@Test()
	public void randomTokenGeneratorTest() {
		String tokenNumber = tokenManager.generateRandomTokenNumber(6);
		assertEquals(tokenNumber.length(), 6);
	}

	@Test()
	public void isTokenValidTest() throws TokenNotFoundException {
		token = new Token("123456", UUID.randomUUID().toString(), true, Status.ACTIVE, "");
		tRepository.createToken(token);
		assertEquals(tokenManager.isTokenValid(token.getTokenId()), true);
	}

	@Test()
	public void isTokenInValidTest() throws TokenNotFoundException {
		token = new Token("123456", UUID.randomUUID().toString(), true, Status.ACTIVE, "");
		tRepository.createToken(token);
		token.setValidtionStatus(false);
		token.setStatus(Status.PAID);
		tRepository.updateToken(token);
		assertEquals(tokenManager.isTokenInvalid(token.getTokenId()), true);
	}
	
	@Rule
	public ExpectedException exception1 = ExpectedException.none();
	
	@Test(expected = TokenNotFoundException.class)
	public void tokenNotValidTest() throws TokenNotFoundException {
		assertEquals(tokenManager.isTokenValid("NotValid"), true);
	}
	
	@Test(expected = TokenNotFoundException.class)
	public void tokenIsValidTest() throws TokenNotFoundException {
		assertEquals(tokenManager.isTokenValid("NotValid"), true);
	}
	
	@Test()
	public void deleteBasedOnCusId() {
		tokenManager.deleteTokens(null);
		token = new Token("848484", "test", true, Status.ACTIVE, "");
		tRepository.createToken(token);
		token = new Token("858585", UUID.randomUUID().toString(), true, Status.ACTIVE, "");
		tRepository.createToken(token);
		token = new Token("868686", UUID.randomUUID().toString(), true, Status.ACTIVE, "");
		tRepository.createToken(token);
		assertEquals(tokenManager.getAllTokens(null).size(), 3);
		assertEquals(tokenManager.getAllTokens("test").size(), 1);
		tokenManager.deleteTokens("test");
		assertEquals(tokenManager.getAllTokens(null).size(), 2);
		tokenManager.deleteTokens(null);
		assertEquals(tokenManager.getAllTokens(null).size(), 0);
	}
	
	@Test()
	public void updateTokenTest() throws TokenNotFoundException {
		tokenManager.deleteTokens(null);
		token = new Token("848484", "test", true, Status.ACTIVE, "");
		tRepository.createToken(token);
		token = new Token("858585", "test1", true, Status.ACTIVE, "");
		tRepository.createToken(token);
		assertEquals(tokenManager.getAllTokens(null).size(), 2);
		assertEquals(true, tokenManager.updateToken("848484", false, Status.INVALID));
		Token token = tokenManager.getToken("848484");
		assertEquals(token.getStatus(), Status.INVALID);
		assertEquals(token.getValidationStatus(), false);
	}
	

}
