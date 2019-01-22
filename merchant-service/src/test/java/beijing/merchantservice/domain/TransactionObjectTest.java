package beijing.merchantservice.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;

public class TransactionObjectTest {
	
	
	@Test
	public void constructorNotNullTest() {
		TransactionObject trOb = new TransactionObject("123987", "123123", "100", new Date());
		assertNotNull(trOb);
	}
	
	@Test
	public void getAmountTest() {
		TransactionObject trOb = new TransactionObject("123987", "123123", "100", new Date());
		String actual = trOb.getAmount();
		String expected = "100";
		assertEquals(expected, actual);
	}
	
	@Test
	public void getMerchantIdTest() {
		TransactionObject trOb = new TransactionObject("123987", "123123", "100", new Date());
		String actual = trOb.getMerchantId();
		String expected = "123987";
		assertEquals(expected, actual);
	}
	
	@Test
	public void getTransactionIdTest() {
		TransactionObject trOb = new TransactionObject("123987", "123123", "100", new Date());
		String actual = trOb.getTrasactionId();
		String expected = "123123";
		assertEquals(expected, actual);
	}
	
	@Test
	public void getDateTest() {
		TransactionObject trOb = new TransactionObject("123987", "123123", "100", new Date(1));
		Date actual = trOb.getTimeOfTransaction();
		Date expected = new Date(1);
		assertEquals(expected, actual);
	}
}
