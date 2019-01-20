package beijing.merchantservice.domain;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import beijing.merchantservice.domain.Merchant;
import beijing.merchantservice.domain.TokenValidation;
import beijing.merchantservice.exception.CorruptedTokenException;
import beijing.merchantservice.exception.DataAccessException;
import beijing.merchantservice.exception.RequestRejected;
import beijing.merchantservice.repository.MerchantRepository;



public class MerchantTest {
	Merchant Stephen = new Merchant("123456", "123456", "BurgerBar");
	

	//Test if merchant can receive a payment
	@Test()
	public void requestTokensSuccesTest() throws IOException, TimeoutException, RequestRejected, DataAccessException, CorruptedTokenException  {
		MerchantManager con = new MerchantManager(new MerchantRepository());
		List<TokenValidation> t = new ArrayList<>();
		con.receiveNewTokens("123,rtjioe");
		
		con.requestTransaction("123","123","100","descrption");
		
		
		//assertEquals(tokens.size(), 1);
	}
}
