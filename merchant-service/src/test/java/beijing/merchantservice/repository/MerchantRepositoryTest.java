package beijing.merchantservice.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;

import beijing.merchantservice.domain.Merchant;
import beijing.merchantservice.domain.TokenValidation;
import beijing.merchantservice.domain.TransactionObject;
import beijing.merchantservice.exception.CorruptedTokenException;
import beijing.merchantservice.exception.DataAccessException;

public class MerchantRepositoryTest {

	private IMerchantRepository repository = new MerchantRepository();
	
	@Test
	public void getMerchantByCVRTest() throws DataAccessException {
		repository.createMerchant(new Merchant("111","122","Impo"));
		String actual = repository.getMerchantByCVR("122").getCvrNumber();
		String expected = "122";
		assertEquals(expected, actual);		
	}
	@Test
	public void getMerchantByIdTest() throws DataAccessException {
		repository.createMerchant(new Merchant("111","122","Impo"));
		String actual = repository.getMerchantById("111").getMerchantId();
		String expected = "111";
		assertEquals(expected, actual);		
	}
	@Test
	public void getMerchantsTest() throws DataAccessException {
		IMerchantRepository repository2 = new MerchantRepository();
		repository2.createMerchant(new Merchant("111","122","Impo"));
		assertEquals(repository2.getMerchants().size(),1);
	}
	@Test
	public void getTokenByIdTest() throws DataAccessException, CorruptedTokenException {
		repository.addToken(new TokenValidation(true, "567", "987"));
		String actual = repository.getTokenById("567").getTokenId();
		String expected = "567";
		assertEquals(expected, actual);		
	}
	@Test
	public void getTransactionsTest() throws DataAccessException, CorruptedTokenException {
		IMerchantRepository repository2 = new MerchantRepository();
		repository2.createTransaction(new TransactionObject("123987","222666", "123123", "100", 1234333l));	
		assertEquals(repository2.getTransactions("123987").size(),1);
	}
	@Test
	public void getTokenValidationsTest() throws DataAccessException, CorruptedTokenException {
		IMerchantRepository repository2 = new MerchantRepository();
		repository2.addToken(new TokenValidation(true, "567", "987"));
		assertEquals(repository2.getTokenValidations().size(),1);	
	}
}
