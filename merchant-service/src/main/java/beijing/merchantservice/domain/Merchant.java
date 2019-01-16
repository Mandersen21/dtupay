package beijing.merchantservice.domain;

import java.io.Serializable;

public class Merchant {

	private String merchantID;
	private String CvrNumber;
	private String Name;

	public Merchant(String merchantID, String CvrNumber, String Name) {
		this.merchantID = merchantID;
		this.CvrNumber = CvrNumber;
		this.Name = Name;
	}

	public String getMerchantID() {
		return merchantID;
	}

	private void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}

	private String getCvrNumber() {
		return CvrNumber;
	}

	private void setCvrNumber(String cvrNumber) {
		CvrNumber = cvrNumber;
	}

	private String getName() {
		return Name;
	}

	private void setName(String name) {
		Name = name;
	}

}
