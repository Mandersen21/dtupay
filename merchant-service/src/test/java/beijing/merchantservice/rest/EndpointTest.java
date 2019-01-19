package beijing.merchantservice.rest;



import javax.ws.rs.core.Response;

import org.junit.Test;

import beijing.merchantservice.domain.Merchant;
import beijing.merchantservice.exception.DataAccessException;
import beijing.merchantservice.exception.RequestRejected;

public class EndpointTest {
	
	
	@Test
	public void getMerchanttest() throws DataAccessException, RequestRejected{
		MerchantsEndpoint endpoint = new MerchantsEndpoint();
		Merchant m = endpoint.controller.createMerchant("12345678", "testCompany");
		
		Response r = endpoint.getMerchant(m.getMerchantID());
			
	}
	

}
