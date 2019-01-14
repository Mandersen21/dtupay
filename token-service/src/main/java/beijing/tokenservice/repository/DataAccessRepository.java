package beijing.tokenservice.repository;

import java.util.List;

import beijing.tokenservice.domain.Customer;
import beijing.tokenservice.domain.Token;

public interface DataAccessRepository {

	public int createCustomer(Customer cust);	
	public Customer getCustomer(String uid);
	public Token getToken(String tokenid);
	public int createToken(Token token);
	public int validateToken(String tokenid);
	public int useToken(String tokenid);
	public List<Token> getTokensForCustId(String custID);
}
