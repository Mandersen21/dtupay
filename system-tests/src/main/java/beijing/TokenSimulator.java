package beijing;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * A simulator used in the system test to simulate the behaviour of a merchant.
 */
public class TokenSimulator {
	/**
	 * The destination URL which the simulator is connecting to.
	 */
	public static final String dtupayUrl = "http://02267-bejing.compute.dtu.dk";
	
	
	
	public TokenSimulator() {
		
	}
	
	public DTUPayCustomerResponse getToken(String custId) throws UnirestException {

		HttpResponse<String> result = Unirest.post(dtupayUrl + ":3000/tokens").header("Content-Type", "application/x-wwww-form-urlencoded")
				.header("Accept", "application/json").queryString("name", custId).field("amount", 1).asString();
		return new DTUPayCustomerResponse(result.getStatus(), result.getBody());
		
	}
	
	
	public DTUPayTokenResponse getTokenId(String tokenId) throws UnirestException {
		
		HttpResponse<String> result = Unirest.get(dtupayUrl + ":3000/tokens/" + tokenId).header("Content-Type", "application/x-wwww-form-urlencoded")
				.header("Accept", "application/json").asString();
		return new DTUPayTokenResponse(result.getStatus(), result.getBody());
	}
	
}