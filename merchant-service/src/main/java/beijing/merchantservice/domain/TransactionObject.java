package beijing.merchantservice.domain;

import java.util.Date;

public class TransactionObject {

    private String merchantId;
    private String trasactionId;
    private String amount;
    private String customerId;
    private Date timeOfTransaction;

    /**
     * creates a transaction object the contains the values related to 
     * a transaction for a merchant.
     * @param merchantId
     * @param customerId
     * @param trasactionId
     * @param amount
     * @param timeOfTransaction
     */
    public TransactionObject(String merchantId, String customerId, String trasactionId, String amount, Date timeOfTransaction) {
        this.merchantId = merchantId;
        this.customerId = customerId;
        this.trasactionId = trasactionId;
        this.amount = amount;
        this.timeOfTransaction = timeOfTransaction;
    }
    
    
    /**
     * gets the customerId
     * @return customerId
     */
    public String getCustomerId() {
		return customerId;
	}


    /**
     * sets the customer Id
     * @param customerId
     */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}



	/**
     * gets the merchant id
     * @return merchant id
     */
    public String getMerchantId() {
        return merchantId;
    }

    /**
     * sets the merchant id
     * @param merchantId
     */
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * gets the transaction id
     * @returntransaction id
     */
    public String getTrasactionId() {
        return trasactionId;
    }

    /**
     * sets the transaction id
     * @param trasactionId
     */
    public void setTrasactionId(String trasactionId) {
        this.trasactionId = trasactionId;
    }

    /**
     * gets the amount transferred 
     * @return amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * sets the amount transferred
     * @param amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * gets the time of transaction
     * @return
     */
    public Date getTimeOfTransaction() {
        return timeOfTransaction;
    }

    /**
     * sets the time of transaction
     * @param timeOfTransaction
     */
    public void setTimeOfTransaction(Date timeOfTransaction) {
        this.timeOfTransaction = timeOfTransaction;
    }
}
