package beijing;


/**
 * Class used to hold the body and the response code of a response from DTUPay to the merchant.
 */
public class DTUPayMerchantResponse extends DTUPayResponse {

	public DTUPayMerchantResponse(int responseCode, String responseText) {
		super(responseCode, responseText);
	}
}