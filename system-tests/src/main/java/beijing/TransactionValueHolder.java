package beijing;

import java.util.Date;

public class TransactionValueHolder {
	
    private String merchantId;
    private String trasactionId;
    private String amount;
    private String customerId;
    private long timeOfTransaction;
    
    
    public TransactionValueHolder() {
    	
    }


	public String getMerchantId() {
		return merchantId;
	}


	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}


	public String getTrasactionId() {
		return trasactionId;
	}


	public void setTrasactionId(String trasactionId) {
		this.trasactionId = trasactionId;
	}


	public String getAmount() {
		return amount;
	}


	public void setAmount(String amount) {
		this.amount = amount;
	}


	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public long getTimeOfTransaction() {
		return timeOfTransaction;
	}


	public void setTimeOfTransaction(long timeOfTransaction) {
		this.timeOfTransaction = timeOfTransaction;
	}
    
    

}
