package beijing;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
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
	
	public DTUPayTokenResponse requestTokens(String custId, int amount) throws UnirestException {

		HttpResponse<String> result = Unirest.post(dtupayUrl + ":3000/tokens?customerId="+custId+"&tokenAmount="+amount).header("Content-Type", "application/x-wwww-form-urlencoded")
				.header("Accept", "application/json").asString();
		return new DTUPayTokenResponse(result.getStatus(), result.getBody());
	}
	
	public DTUPayTokenResponse deleteTokens() throws UnirestException {

		HttpResponse<String> result = Unirest.delete(dtupayUrl + ":3000/tokens").header("Content-Type", "application/x-wwww-form-urlencoded")
				.header("Accept", "application/json").asString();
		return new DTUPayTokenResponse(result.getStatus(), result.getBody());
	}
	
	
	
	public DTUPayJsonResponse getCustomerTokens(String custId) throws UnirestException {
		HttpResponse<JsonNode> result = Unirest.get(dtupayUrl + ":3000/tokens?customerId="+custId)
				.header("Accept", "application/json").asJson();
		return new DTUPayJsonResponse(result.getStatus(), result.getBody());
	}
	
	
	
	
	public DTUPayTokenResponse getTokenId(String tokenId) throws UnirestException {
		
		HttpResponse<String> result = Unirest.get(dtupayUrl + ":3000/tokens/" + tokenId).header("Content-Type", "application/x-wwww-form-urlencoded")
				.header("Accept", "application/json").asString();
		return new DTUPayTokenResponse(result.getStatus(), result.getBody());
	}
	
}