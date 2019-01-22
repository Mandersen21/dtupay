package beijing;

import com.mashape.unirest.http.JsonNode;

/**
 * Class used to hold the body and the response code of a response from DTUPay to the customer.
 */

public class DTUPayTokenResponse extends DTUPayResponse {

	public DTUPayTokenResponse(int responseCode, String responseText) {
		super(responseCode, responseText);
	}
}