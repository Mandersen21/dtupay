package beijing.system_cucumber_tests;

import static org.junit.Assert.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitionPayment {

	@Given("^a registered customer with a bank account$")
	public void a_registered_customer_with_a_bank_account() throws Exception {
	    
		
	}
	
	@Given("^a registered merchant with a bank account$")
	public void a_registered_merchant_with_a_bank_account() throws Exception {
	    
	}
	
	@Given("^the customer has one unused token$")
	public void the_customer_has_one_unused_token() throws Exception {
	    
	}
	
	@When("^the merchant scans the token$")
	public void the_merchant_scans_the_token() throws Exception {
	    
	}
	
	@When("^requests payment for (\\d+) kroner using the token$")
	public void requests_payment_for_kroner_using_the_token(int arg1) throws Exception {
	    
	}
	
	@Then("^the payment succeeds$")
	public void the_payment_succeeds() throws Exception {
	    
	}
	
	@Then("^the money is transferred from the customer bank account to the merchant bank account$")
	public void the_money_is_transferred_from_the_customer_bank_account_to_the_merchant_bank_account() throws Exception {
	    
	}

}
