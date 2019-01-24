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

import beijing.merchantservice.domain.*;



import junit.framework.TestCase;


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
		repository.createMerchant(new Merchant("12332","12332","toy store 2"));	
		assertNotNull(man.getAllMerhcants());
	}
	
	@Test
	public void getTransactionstest() throws DataAccessException {
		long l = System.currentTimeMillis();
		repository.createTransaction(new TransactionObject("12332", "222666", UUID.randomUUID().toString(), "100",l ));
		assertNotNull(man.getTransactionsById("12332"));
	}
	
	@Test
	public void getAllTransactionstest() throws DataAccessException {
		long l = System.currentTimeMillis();
		repository.createTransaction(new TransactionObject("12332", "222666", UUID.randomUUID().toString(), "700", l));
		assertTrue(!man.getTransactions().isEmpty());
	}
	
}
