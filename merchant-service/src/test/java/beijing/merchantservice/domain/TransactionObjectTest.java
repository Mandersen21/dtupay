package beijing.merchantservice.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;

public class TransactionObjectTest {
	
	
	@Test
	public void constructorNotNullTest() {
		TransactionObject trOb = new TransactionObject("123987","222666", "123123", "100", 1234333l);
		assertNotNull(trOb);
	}
	
	@Test
	public void getAmountTest() {
		TransactionObject trOb = new TransactionObject("123987","222666", "123123", "100", 1234333l);
		String actual = trOb.getAmount();
		String expected = "100";
		assertEquals(expected, actual);
	}
	
	@Test
	public void getCustomerTest() {
		TransactionObject trOb = new TransactionObject("123987","222666", "123123", "100", 1234333l);
		String actual = trOb.getCustomerId();
		String expected = "222666";
		assertEquals(expected, actual);
	}
	
	
	
	@Test
	public void getMerchantIdTest() {
		TransactionObject trOb = new TransactionObject("123987","222666", "123123", "100", 123456l);
		String actual = trOb.getMerchantId();
		String expected = "123987";
		assertEquals(expected, actual);
	}
	
	@Test
	public void getTransactionIdTest() {
		TransactionObject trOb = new TransactionObject("123987","222666", "123123", "100", 123456l);
		String actual = trOb.getTrasactionId();
		String expected = "123123";
		assertEquals(expected, actual);
	}
	
	@Test
	public void getDateTest() {
		long l = System.currentTimeMillis();
		TransactionObject trOb = new TransactionObject("123987","222666", "123123", "100",l );
		long actual = trOb.getTimeOfTransaction();
		long expected = l;
		assertEquals(expected, actual);
	}
	
	@Test
	public void setAmountTest() {
		TransactionObject trOb = new TransactionObject("123987","222666", "123123", "100", 123456l);
		trOb.setAmount("200");
		String actual = trOb.getAmount();
		String expected = "200";
		assertEquals(expected, actual);
	}
	
	@Test
	public void setCustomerTest() {
		TransactionObject trOb = new TransactionObject("123987","222666", "123123", "100", 123456l);
		trOb.setCustomerId("1234321");
		String actual = trOb.getCustomerId();
		String expected = "1234321";
		assertEquals(expected, actual);
	}
	
	
	
	@Test
	public void setMerchantIdTest() {
		TransactionObject trOb = new TransactionObject("123987","222666", "123123", "100", 123456l);
		trOb.setMerchantId("000");
		String actual = trOb.getMerchantId();
		String expected = "000";
		assertEquals(expected, actual);
	}
	
	@Test
	public void setTransactionIdTest() {
		TransactionObject trOb = new TransactionObject("123987","222666", "123123", "100", 123456l);
		trOb.setTrasactionId("111");
		String actual = trOb.getTrasactionId();
		String expected = "111";
		assertEquals(expected, actual);
	}
	
	@Test
	public void setDateTest() {
		TransactionObject trOb = new TransactionObject("123987","222666", "123123", "100", 123456l);
		trOb.setTimeOfTransaction(1234333l);
		long actual = trOb.getTimeOfTransaction();
		long expected = 1234333l;
		assertEquals(expected, actual);
	}
	
}
