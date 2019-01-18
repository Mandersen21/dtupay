package beijing.paymentservice.repository;

public interface IBankRepository {

	public boolean verifyBankCustomer(String cardId, String pwd);
}
