

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import beijing.CreateAccountRequest;
import beijing.CreateBankClientRequest;
import beijing.CustomerSimulator;
import beijing.CustomerValueHolder;
import beijing.DTUPayJsonResponse;
import beijing.DTUPayResponse;
import beijing.DTUPayTokenResponse;
import beijing.MerchantSimulator;
import beijing.TokenSimulator;
import beijing.TokenValueHolder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dtu.ws.fastmoney.*;

public class tokenStepDefinition {
	
	private static final String appUrl = "http://02267-bejing.compute.dtu.dk:3002/customers";
	
	private static final Gson jsonLib = new Gson();
	
	private String customerCpr = "0101010101";
	private String merchantCVR = "0202020202";
	private String tokenId = "123";
	private String merchantId = "98765";
	private String customerId = "123456";
	private BigDecimal customerBalance;
	private BigDecimal merchantBalance;
	private Account customerAccount;
	private Account merchantAccount;
	private BigDecimal amount;
	private List<TokenValueHolder> tokenList;
	private Type type =  new TypeToken<List<TokenValueHolder>>() {}.getType();
	private TokenSimulator ts = new TokenSimulator();
	private String responseText;
	private int responseCode;
	
	@Given("^a registered customer$")
	public void a_registered_customer() throws Exception {
		ts.deleteTokens();
		CustomerSimulator customerSimulator = new CustomerSimulator();		
		DTUPayResponse result = customerSimulator.getCustomer(customerId);
		CustomerValueHolder customer = jsonLib.fromJson(result.getResponseText(), CustomerValueHolder.class);
		assertEquals(customer.getName(),"john john");
		assertEquals(customer.getCpr(),"0101010101");
	}
	
	
	@Given("^the customer has no more than one valid token$")
	public void the_customer_has_no_more_than_one_valid_token() throws Exception {	
		List<TokenValueHolder> result = jsonLib.fromJson(ts.getCustomerTokens(customerId).getResponseText(),type);
		assertTrue(result.size() <= 1);
	}
	
	@When("^the customer requests (\\d+) tokens from the token service$")
	public void the_customer_requests_tokens_from_the_token_service(int arg1) throws Exception {
		DTUPayTokenResponse response = ts.requestTokens(customerId, arg1);
		responseText = response.getResponseText();
		responseCode = response.getResponseCode();
	}



	@Then("^(\\d+) tokens are generated by the token service$")
	public void tokens_are_generated_by_the_token_service(int arg1) throws Exception {		
		String stuff = ts.getCustomerTokens(customerId).getResponseText();
		tokenList = jsonLib.fromJson(stuff,type);
		assertTrue(tokenList.size() >=5);
	}

	@Then("^the token service assigns (\\d+) tokens to the registered customer$")
	public void the_token_service_assigns_tokens_to_the_registered_customer(int arg1) throws Exception {
		for(TokenValueHolder t : tokenList) {
			assertEquals(customerId,t.getCustomerId());
		}
	}
	
	
	
	@Given("^the customer has more than one valid token$")
	public void the_customer_has_more_than_one_valid_token() throws Exception {
		List<TokenValueHolder> result = jsonLib.fromJson(ts.getCustomerTokens(customerId).getResponseText(),type);
		assertTrue(result.size() > 1);
	    
	}

	@Then("^the request is denied$")
	public void the_request_is_denied() throws Exception {
		System.out.println("result for tokenRequest: "+responseCode);
		assertEquals(406,responseCode);
	}
	
	
	
	
	
}
