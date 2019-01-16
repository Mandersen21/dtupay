package beijing.merchantservice.domain;

import java.util.Date;

public class TransactionObject {

    private String merchantId;
    private String trasactionId;
    private String amount;
    private Date timeOfTransaction;

    public TransactionObject(String merchantId, String trasactionId, String amount, Date timeOfTransaction) {
        this.merchantId = merchantId;
        this.trasactionId = trasactionId;
        this.amount = amount;
        this.timeOfTransaction = timeOfTransaction;
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

    public Date getTimeOfTransaction() {
        return timeOfTransaction;
    }

    public void setTimeOfTransaction(Date timeOfTransaction) {
        this.timeOfTransaction = timeOfTransaction;
    }
}
