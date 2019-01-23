package beijing.merchantservice.domain;

public class Merchant {

	private String merchantId;
	private String CvrNumber;
	private String Name;

	/**
	 * Merchant is a model class that contains the values accociated with 
	 * a merchant
	 * @param merchantID
	 * @param CvrNumber
	 * @param Name
	 */
	public Merchant(String merchantID, String CvrNumber, String Name) {
		this.merchantId = merchantID;
		this.CvrNumber = CvrNumber;
		this.Name = Name;
	}

	/**
	 * gets the merchants id
	 * @return merchantId
	 */
	public String getMerchantId() {
		return merchantId;
	}

	/**
	 * sets the merchants id
	 * @param merchantID
	 */
	public void setMerchantID(String merchantID) {
		this.merchantId = merchantID;
	}

	/**
	 * gets the merchants CVR number
	 * @return merchantCVR
	 */
	public String getCvrNumber() {
		return CvrNumber;
	}

	/**
	 * sets the merhcant CVR number
	 * @param cvrNumber
	 */
	public void setCvrNumber(String cvrNumber) {
		CvrNumber = cvrNumber;
	}

	/**
	 * gets the merchant name
	 * @return bane of merchant
	 */
	public String getName() {
		return Name;
	}

	/**
	 * sets the name of the merchant
	 * @param name
	 */
	public void setName(String name) {
		Name = name;
	}

}
