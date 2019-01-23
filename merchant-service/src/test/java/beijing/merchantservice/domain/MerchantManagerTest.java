package beijing.merchantservice.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import beijing.merchantservice.exception.CorruptedTokenException;
import beijing.merchantservice.exception.DataAccessException;
import beijing.merchantservice.exception.RequestRejected;
import beijing.merchantservice.repository.IMerchantRepository;
import beijing.merchantservice.repository.MerchantRepository;
import junit.framework.TestCase;

@RunWith(JUnit4.class)
public class MerchantManagerTest {

	
	private IMerchantRepository repository = new MerchantRepository();
	private MerchantManager man;
	
	@Before
	public void setUp() throws IOException, TimeoutException {
		man = new MerchantManager(repository);
	}
	
	@Test
	public void createMerchantTest() throws DataAccessException, RequestRejected {
		Merchant mer = man.createMerchant("123987", "Pizza Place");
		assertNotNull(mer);
	}
	
	@Test(expected = RequestRejected.class)
	public void createMerchantThatExistsTest() throws DataAccessException, RequestRejected {
		repository.createMerchant(new Merchant("123","123","123"));
		Merchant mer = man.createMerchant("123","123");
	}
	
	@Test
	public void receiveNewTokensTest() throws CorruptedTokenException, DataAccessException {
		man.receiveNewTokens("123456,789465");
		String actual = repository.getTokenById("123456").getCustomerId();
		String expected = "789465";
		assertEquals(expected, actual);		
	}

//	@Rule
//	public ExpectedException exception = ExpectedException.none();
//	
//	@Test(expected = CorruptedTokenException.class)
//	public void receiveNewTokensThatAlreadyExistsTest() throws CorruptedTokenException, RequestRejected, DataAccessException, IOException, TimeoutException {
//		man.receiveNewTokens("123456,789465");
//		man.receiveNewTokens("123456,789465");		
//	}
	
	@Test
	public void getMerchantByIdTest() throws DataAccessException {
		repository.createMerchant(new Merchant("1233","1233","toy store"));
		String actual = man.getMerchantById("1233").getName();
		String expected = "toy store";
		assertEquals(expected, actual);	
	}
	
	@Test(expected = DataAccessException.class)
	public void getMerchantByIdThatDoesNotExistsTest() throws DataAccessException {
		String actual = man.getMerchantById("12343").getName();
	}
	
	@Test
	public void getAllMerhcantsTest() throws DataAccessException {
		IMerchantRepository repository2 = new MerchantRepository();
		MerchantManager man2 = new MerchantManager(repository2);
		assertEquals(man.getAllMerhcants().size(),1);
	}
	
	@Test
	public void getAllTransactionstest() throws DataAccessException {
		repository.createTransaction(new TransactionObject("12332", "222666", UUID.randomUUID().toString(), "100", new Date(System.currentTimeMillis())));
		assertEquals(man.getTransactions().size(),1);
	}
	
	@Test(expected = RequestRejected.class)
	public void requestTransactionTest() throws CorruptedTokenException, DataAccessException, RequestRejected, IOException {
		man.receiveNewTokens("123456,789465");
		repository.getTokenById("123456").setValid(false);
		man.requestTransaction("123", "123456", "100", "Pay");	
	}
	@Test
	public void getTransactionsByIdTest() throws DataAccessException {
		repository.createTransaction(new TransactionObject("123", "1234", "12345", "100", new Date(1)));
		assertEquals(man.getTransactionsById("123").size(),1);
		
	}
	
}
