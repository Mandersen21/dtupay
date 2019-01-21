package beijing;


/**
 * Class used to hold the body and the response code of a response from DTUPay to the customer.
 */

public class DTUPayCustomerResponse extends DTUPayResponse {

	public DTUPayCustomerResponse(int responseCode, String responseText) {
		super(responseCode, responseText);
	}

}