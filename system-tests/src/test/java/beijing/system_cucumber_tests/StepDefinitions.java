package beijing.system_cucumber_tests;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import beijing.CreateAccountRequest;
import beijing.CreateBankClientRequest;
import beijing.CustomerSimulator;
import beijing.DTUPayResponse;
import beijing.MerchantSimulator;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import static junit.framework.TestCase.assertEquals;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitions {
	
	private static final String appUrl = "http://02267-bejing.compute.dtu.dk:3002/customers";
	
	private static final Gson jsonLib = new Gson();
	private String customerCpr = "123987";
	private String customerBankId;
	private double customerFunds = 10000.0;
	private String merchantCpr = "213412";
	private String merchantBankId;
	private String amount;
	private String token;
	
	@Before("@pay")
	public void beforeScenario() throws Exception {
		String firstName = "John";
		String lastName = "John";
		
		//Client
		CreateBankClientRequest createCustomerBankRequest = new CreateBankClientRequest(customerCpr, firstName, lastName, customerFunds);
		HttpResponse<String> createBankResponse = Unirest
				.post(appUrl + "/create_bank_client")
				.header("Content-Type", "application/json")
				.header("Accept", "text/plain")
				.body(jsonLib.toJson(createCustomerBankRequest))
				.asString();
		assertEquals(201, createBankResponse.getStatus());		
		customerBankId = createBankResponse.getBody();
		
		CreateAccountRequest createCustomerDtupayRequest = new CreateAccountRequest();
		createCustomerDtupayRequest.setCpr(customerCpr);
		createCustomerDtupayRequest.setName(firstName + " " + lastName);
		HttpResponse<String> createDtuPayResponse = Unirest
				.post(appUrl + "/account")
				.header("Content-Type", "application/json")
				.header("Accept", "text/plain")
				.body(jsonLib.toJson(createCustomerDtupayRequest))
				.asString();
		assertEquals(200, createDtuPayResponse.getStatus());
		
		//Merchant
		String merchantFirstName = "Hans";
		String merchantLastName = "Hansi";
		CreateBankClientRequest createMerchantBankRequest = new CreateBankClientRequest(merchantCpr, merchantFirstName, merchantLastName, 10000.0);
		HttpResponse<String> createMerchantBankResponse = Unirest
				.post(appUrl + "/create_bank_client")
				.header("Content-Type", "application/json")
				.header("Accept", "text/plain")
				.body(jsonLib.toJson(createMerchantBankRequest))
				.asString();
		assertEquals(201, createMerchantBankResponse.getStatus());
		merchantBankId = createMerchantBankResponse.getBody();
		
		CreateAccountRequest createMerchantDtupayRequest = new CreateAccountRequest();
		createMerchantDtupayRequest.setCpr(merchantCpr);
		createMerchantDtupayRequest.setName(merchantFirstName + " " + merchantLastName);
		HttpResponse<String> createMerchantDtuPayResponse = Unirest
				.post(appUrl + "/account")
				.header("Content-Type", "application/json")
				.header("Accept", "text/plain")
				.body(jsonLib.toJson(createMerchantDtupayRequest))
				.asString();
		assertEquals(200, createMerchantDtuPayResponse.getStatus());
	}
	
	@After("@pay")
	public void afterScenario() throws Exception {
		if (customerBankId != null && !customerBankId.isEmpty() ) {
			HttpResponse<String> removeBankResponse = Unirest
					.delete(appUrl + "/delete_bank_client")
					.header("Accept", "text/plain")
					.queryString("bank_id", customerBankId)
					.asString();
			assertEquals(200, removeBankResponse.getStatus());		
		}
		
		if (customerCpr != null && !customerCpr.isEmpty() ) {
			HttpResponse<String> removeDtuPayResponse = Unirest
					.delete(appUrl + "/delete_account")
					.queryString("cpr", customerCpr)
					.asString();
			assertEquals(200, removeDtuPayResponse.getStatus());
		}		
		
		if (merchantBankId != null && !merchantBankId.isEmpty() ) {
			HttpResponse<String> removeBankResponse = Unirest
					.delete(appUrl + "/delete_bank_client")
					.header("Accept", "text/plain")
					.queryString("bank_id", merchantBankId)
					.asString();
			assertEquals(200, removeBankResponse.getStatus());		
		}
		
		if (merchantCpr != null && !merchantCpr.isEmpty() ) {
			HttpResponse<String> removeDtuPayResponse = Unirest
					.delete(appUrl + "/delete_account")
					.queryString("cpr", merchantCpr)
					.asString();
			assertEquals(200, removeDtuPayResponse.getStatus());
		}
	}
	
	@Given("^a registered customer with a bank account$")
	public void a_registered_customer_with_a_bank_account() throws Exception {
	    
		CustomerSimulator customerSimulator = new CustomerSimulator();		
		DTUPayResponse result = customerSimulator.getToken(customerCpr);
		assertEquals(200, result.getResponseCode());
		
	}

	@Given("^a registered merchant with a bank account$")
	public void a_registered_merchant_with_a_bank_account() throws Exception {
		
		MerchantSimulator merchantSimulator = new MerchantSimulator();
		
	}

	@Given("^the customer has one unused token$")
	public void the_customer_has_one_unused_token() throws Exception {
	    
		
		
	}

	@When("^the merchant scans the token$")
	public void the_merchant_scans_the_token(String arg1) throws Exception {
	    
		
		
	}

	@When("^requests payment for (\\d+) kroner using the token$")
	public void requests_payment_for_kroner_using_the_token(String arg1) throws Exception {
	    
		amount = arg1;
		
	}

	@Then("^the payment succeeds$")
	public void the_payment_succeeds() throws Exception {
	   
		
		
	}

	@Then("^the money is transferred from the customer bank account to the merchant bank account$")
	public void the_money_is_transferred_from_the_customer_bank_account_to_the_merchant_bank_account() throws Exception {
	   
		
		
	}
}
