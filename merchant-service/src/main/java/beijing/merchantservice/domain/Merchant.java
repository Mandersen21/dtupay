package beijing.merchantservice.domain;

import java.io.Serializable;

public class Merchant {

	private String merchantID;
	private String CvrNumber;
	private String Name;

	/**
	 * 
	 * @param merchantID
	 * @param CvrNumber
	 * @param Name
	 */
	public Merchant(String merchantID, String CvrNumber, String Name) {
		this.merchantID = merchantID;
		this.CvrNumber = CvrNumber;
		this.Name = Name;
	}

	/**
	 * 
	 * @return
	 */
	public String getMerchantID() {
		return merchantID;
	}

	/**
	 * 
	 * @param merchantID
	 */
	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
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
