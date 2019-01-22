package beijing;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * A simulator used in the system test to simulate the behaviour of a merchant.
 */
public class MerchantSimulator {
	/**
	 * The destination URL which the simulator is connecting to.
	 */
	public static final String dtupayUrl = "http://02267-bejing.compute.dtu.dk";
	
	
	
	public MerchantSimulator() {
		
	}
	
	
	public DTUPayMerchantResponse intiateTransaction(String mechId, String tokenId, String amount) throws UnirestException {
		
		HttpResponse<String> result = Unirest.put(dtupayUrl + ":3001/merchants").header("Content-Type", "application/x-wwww-form-urlencoded")
				.header("Accept", "application/json").queryString("merchantId", mechId).field("tokenId", tokenId).field("amount", amount).asString();
		return new DTUPayMerchantResponse(result.getStatus(), result.getBody());
	}
	
}