package beijing.system_cucumber_tests;

import beijing.customerservice.domain.CustomerManager;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitions {

	private CustomerManager customerManager;
//	private TokenManager tokenManager;
//	private MerchantController merchantManager;
//	
//	private ICustomerRepository customerRepository;
//	private ITokenRepository tokenRepository;
//	
	public StepDefinitions() {
//		customerMananger = new CustomerManager();
	}
	
	@Given("^a registered customer with a bank account$")
	public void a_registered_customer_with_a_bank_account() throws Exception {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Given("^a registered merchant with a bank account$")
	public void a_registered_merchant_with_a_bank_account() throws Exception {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Given("^the customer has one unused token$")
	public void the_customer_has_one_unused_token() throws Exception {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^the merchant scans the token$")
	public void the_merchant_scans_the_token() throws Exception {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^requests payment for (\\d+) kroner using the token$")
	public void requests_payment_for_kroner_using_the_token(int arg1) throws Exception {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^the payment succeeds$")
	public void the_payment_succeeds() throws Exception {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^the money is transferred from the customer bank account to the merchant bank account$")
	public void the_money_is_transferred_from_the_customer_bank_account_to_the_merchant_bank_account() throws Exception {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}
}
