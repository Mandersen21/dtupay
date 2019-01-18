package beijing.paymentservice.repository;

import java.util.HashMap;
import java.util.Map;

public class BankRepository implements IBankRepository {
	Map<String,String> cardMap = new HashMap<String, String>();
	
	@Override
	public Map<String, String> addBankCustomer(String cardId, String pwd) {
		cardMap.put(cardId,pwd);
		return cardMap;
	}
	
	@Override
	public boolean verifyBankCustomer(String cardId, String pwd) {
		String key = cardId;
		String v = pwd;
		boolean result = false;
		for (Map.Entry<String, String> entry : cardMap.entrySet()) {
			if (entry.getKey() == key) {
				if (entry.getValue() == v) {
					result = true;
					break;
				}
			} else {
				System.out.println("Card not exist");
				result = false;
				break;
			}
		}
		return result;
	}
}
