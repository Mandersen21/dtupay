//package beijing.merchantservice.domain;
//
//import static org.junit.Assert.*;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeoutException;
//
//import org.junit.Test;
//import beijing.merchantservice.domain.Merchant;
//import beijing.merchantservice.domain.MerchantController;
//import beijing.merchantservice.domain.TokenValidation;
//import beijing.merchantservice.exception.CorruptedTokenException;
//import beijing.merchantservice.exception.DataAccessException;
//import beijing.merchantservice.exception.RequestRejected;
//import beijing.merchantservice.repository.MerchantRepository;
//
//
//
//public class MerchantTest {
//	Merchant Stephen = new Merchant("123456", "123456", "BurgerBar");
//	
//
//	//Test if merchant can receive a payment
//	
//	@Test()
//	public void requestTokensSuccesTest() throws IOException, TimeoutException, RequestRejected, DataAccessException, CorruptedTokenException  {
//		MerchantController con = new MerchantController(new MerchantRepository());
//		List<TokenValidation> t = new ArrayList<>();
//		con.receiveNewTokens("123,rtjioe");
//		
//		con.requestTransaction("111", "123", "100");
//		
//		
//		//assertEquals(tokens.size(), 1);
//	}
//}
