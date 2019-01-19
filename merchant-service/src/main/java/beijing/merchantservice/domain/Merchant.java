package beijing.merchantservice.domain;

public class Merchant {

	private String merchantId;
	private String CvrNumber;
	private String Name;

	/**
	 * 
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
	 * 
	 * @return
	 */
	public String getMerchantId() {
		return merchantId;
	}

	/**
	 * 
	 * @param merchantID
	 */
	public void setMerchantID(String merchantID) {
		this.merchantId = merchantID;
	}

	/**
	 * 
	 * @return
	 */
	public String getCvrNumber() {
		return CvrNumber;
	}

	/**
	 * 
	 * @param cvrNumber
	 */
	public void setCvrNumber(String cvrNumber) {
		CvrNumber = cvrNumber;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return Name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		Name = name;
	}

}
