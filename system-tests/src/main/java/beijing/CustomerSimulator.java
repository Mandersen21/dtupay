package beijing;

import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * A simulator used in the system test to simulate the behaviour of a customer.
 */
public class CustomerSimulator {
	/**
	 * The destination URL which the simulator is connecting to.
	 */
	public static final String dtupayUrl = "http://02267-bejing.compute.dtu.dk";

	/**
	 * JSON library
	 */

	public CustomerSimulator() {
		
	}


	public DTUPayCustomerResponse createDtuPayAccount(String cpr, String name) throws UnirestException {

		HttpResponse<String> result = Unirest.post(dtupayUrl + ":3002/customers").header("Content-Type", "application/x-wwww-form-urlencoded")
				.header("Accept", "application/json").queryString("name", name).field("cpr", cpr).asString();
		return new DTUPayCustomerResponse(result.getStatus(), result.getBody());
		
	}
	
	public DTUPayCustomerResponse getCustomer(String custId) throws UnirestException {
		HttpResponse<String> result = Unirest.get(dtupayUrl + ":3002/customers/"+custId)
				.header("Accept", "application/json").asString();
		return new DTUPayCustomerResponse(result.getStatus(), result.getBody());
		
	}
	
	




}