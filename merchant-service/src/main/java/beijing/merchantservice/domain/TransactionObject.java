package beijing.merchantservice.domain;

import java.util.Date;

public class TransactionObject {

    private String merchantId;
    private String trasactionId;
    private String amount;
    private Date timeOfTransaction;

    /**
     * 
     * @param merchantId
     * @param trasactionId
     * @param amount
     * @param timeOfTransaction
     */
    public TransactionObject(String merchantId, String trasactionId, String amount, Date timeOfTransaction) {
        this.merchantId = merchantId;
        this.trasactionId = trasactionId;
        this.amount = amount;
        this.timeOfTransaction = timeOfTransaction;
    }

    /**
     * 
     * @return
     */
    public String getMerchantId() {
        return merchantId;
    }

    /**
     * 
     * @param merchantId
     */
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * 
     * @return
     */
    public String getTrasactionId() {
        return trasactionId;
    }

    /**
     * 
     * @param trasactionId
     */
    public void setTrasactionId(String trasactionId) {
        this.trasactionId = trasactionId;
    }

    /**
     * 
     * @return
     */
    public String getAmount() {
        return amount;
    }

    /**
     * 
     * @param amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * 
     * @return
     */
    public Date getTimeOfTransaction() {
        return timeOfTransaction;
    }

    /**
     * 
     * @param timeOfTransaction
     */
    public void setTimeOfTransaction(Date timeOfTransaction) {
        this.timeOfTransaction = timeOfTransaction;
    }
}
