package beijing.paymentservice.repository;

public class Transaction {
	
	protected String transactionId;
	protected String amount;
	protected String date;
	protected IAccount receiver;
	protected IAccount sender;
	
	
	public String getAmount() {
		return amount;
	}
	
	public String getDate() {
		return date;
	}
	
	public IAccount getReceiver() {
		return receiver;
	}

	public IAccount getSender() {
		return sender;
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void setReceiver(IAccount receiver) {
		this.receiver = receiver;
	}
	
	public void setSender(IAccount sender) {
		this.sender = sender;
	}
	
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
}
