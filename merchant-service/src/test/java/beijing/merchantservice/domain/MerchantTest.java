package beijing.merchantservice.domain;

import static org.junit.Assert.*;

import org.junit.Test;


public class MerchantTest {
	

	@Test
	public void constructorNotNullTest() {
		Merchant mer = new Merchant("122987", "123456789", "Burger shop");
		assertNotNull(mer);
	}
	
	@Test
	public void getCVRTest() {
		Merchant mer = new Merchant("122987", "123456789", "Burger shop");
		String actual = mer.getCvrNumber();
		String expected = "123456789";
		assertEquals(expected, actual);
	}
	
	@Test
	public void getNameTest() {
		Merchant mer = new Merchant("122987", "123456789", "Burger shop");
		String actual = mer.getName();
		String expected = "Burger shop";
		assertEquals(expected, actual);
	}
	
	@Test
	public void getMerchantIdTest() {
		Merchant mer = new Merchant("122987", "123456789", "Burger shop");
		String actual = mer.getMerchantId();
		String expected = "122987";
		assertEquals(expected, actual);
	}
	
	@Test
	public void setCVRTest() {
		Merchant mer = new Merchant("122987", "123456789", "Burger shop");
		mer.setCvrNumber("54545");
		String actual = mer.getCvrNumber();
		String expected = "54545";
		assertEquals(expected, actual);
	}
	
	@Test
	public void setNameTest() {
		Merchant mer = new Merchant("122987", "123456789", "Burger shop");
		mer.setName("Ping");
		String actual = mer.getName();
		String expected = "Ping";
		assertEquals(expected, actual);
	}
	
	@Test
	public void setMerchantIdTest() {
		Merchant mer = new Merchant("122987", "123456789", "Burger shop");
		 mer.setMerchantID("99999");
		String actual = mer.getMerchantId();
		String expected = "99999";
		assertEquals(expected, actual);
	}
	
}
