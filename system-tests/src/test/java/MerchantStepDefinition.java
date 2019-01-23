

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beijing.MerchantSimulator;
import beijing.TokenValueHolder;
import beijing.TransactionValueHolder;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dtu.ws.fastmoney.*;

public class MerchantStepDefinition {
	MerchantSimulator ms = new MerchantSimulator();
	private Type type =  new TypeToken<List<TransactionValueHolder>>() {}.getType();
	private Gson jsonLib = new Gson();
	private List<TransactionValueHolder> customerTransactionList;
	
	private String custcpr = "0101010101";
	private String custId = "123456";
	private String merchId = "98765";
	private String merchCVR = "0202020202";
	
	
	@Given("^that the customer has perfomed transactions$")
	public void that_the_customer_has_perfomed_transactions() throws Exception {
		List<TransactionValueHolder> resultList = jsonLib.fromJson(ms.getAllTransactions().getResponseText(),type);
		boolean result = false;
		for(TransactionValueHolder tvh : resultList ) {
			if(tvh.getCustomerId().equals(custId)) {
				result = true;
			}
		}
		assertTrue(result);
	}

	@When("^the customer request for a list of transactions$")
	public void the_customer_request_for_a_list_of_transactions() throws Exception {
		customerTransactionList = jsonLib.fromJson(ms.getCustomerTransactions(custId).getResponseText(),type);
	    assertTrue(!customerTransactionList.isEmpty());
	}
	
	
	

	@Then("^DTUPay replies with a list of transactions$")
	public void dtupay_replies_with_a_list_of_transactions() throws Exception {
		assertTrue(customerTransactionList != null);
	}
	
	
	@Given("^that the merchant has perfomed transactions$")
	public void that_the_merchant_has_perfomed_transactions() throws Exception {
		List<TransactionValueHolder> resultList = jsonLib.fromJson(ms.getAllTransactions().getResponseText(),type);
		boolean result = false;
		for(TransactionValueHolder tvh : resultList ) {
			if(tvh.getMerchantId().equals(merchId)) {
				result = true;
			}
		}
		assertTrue(result);
	}

	@When("^the merchant request for a list of transactions$")
	public void the_merchant_request_for_a_list_of_transactions() throws Exception {
		customerTransactionList = jsonLib.fromJson(ms.getCustomerTransactions(merchId).getResponseText(),type);
	    assertTrue(!customerTransactionList.isEmpty());
	}
	
	
	
	
	

}
