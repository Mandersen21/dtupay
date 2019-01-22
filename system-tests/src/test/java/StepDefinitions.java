

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import beijing.CreateAccountRequest;
import beijing.CreateBankClientRequest;
import beijing.CustomerSimulator;
import beijing.DTUPayResponse;
import beijing.MerchantSimulator;
import beijing.TokenSimulator;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import static junit.framework.TestCase.assertEquals;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dtu.ws.fastmoney.*;

public class StepDefinitions {
	
	private static final String appUrl = "http://02267-bejing.compute.dtu.dk:3002/customers";
	
	private static final Gson jsonLib = new Gson();
	
	private String customerCpr = "0101010101";
	private String merchantCVR = "0202020202";
	private String tokeId = "123";
	private String merchantId = "98765";
	private String customerId = "123456";
	
	
	@Given("^a registered customer with a bank account$")
	public void a_registered_customer_with_a_bank_account() throws Exception {
		
		BankService bs =  new BankServiceServiceLocator().getBankServicePort();
		Account a = bs.getAccountByCprNumber(customerCpr);
	    
		CustomerSimulator customerSimulator = new CustomerSimulator();		
		DTUPayResponse result = customerSimulator.getCustomer(customerCpr);
		assertEquals(a.getUser().getCprNumber(), customerCpr);
		
	}

	@Given("^a registered merchant with a bank account$")
	public void a_registered_merchant_with_a_bank_account() throws Exception {
		
		BankService bs =  new BankServiceServiceLocator().getBankServicePort();
		Account a = bs.getAccountByCprNumber(merchantCVR);
	    
		CustomerSimulator customerSimulator = new CustomerSimulator();		
		DTUPayResponse result = customerSimulator.getCustomer(merchantCVR);
		assertEquals(a.getUser().getCprNumber(), merchantCVR);
		
	}

	@Given("^the customer has one unused token$")
	public void the_customer_has_one_unused_token() throws Exception {
			
		TokenSimulator tokenSimulator = new TokenSimulator();
		DTUPayResponse result = tokenSimulator.getTokenId(tokeId);
		assertEquals(200, result.getResponseCode());
		
	}

	@When("^the merchant scans the token and requests payment for (\\d+) kroner using the token$")
	public void requests_payment_for_kroner_using_the_token(String amount) throws Exception {
	    
		MerchantSimulator merchantSimulator = new MerchantSimulator();
		DTUPayResponse result = merchantSimulator.intiateTransaction(merchantId, tokeId, amount);
		assertEquals(200, result.getResponseCode());
		
	}

	@Then("^the payment succeeds and the money is transferred from the customer bank account to the merchant bank account$")
	public void the_money_is_transferred_from_the_customer_bank_account_to_the_merchant_bank_account() throws Exception {
	   
		
		
	}
}
