package beijing.merchantservice.domain;

import java.io.Serializable;

public class Merchant implements Comparable<Merchant>, Serializable {
	private static final long serialVersionUID = 1693295470116937316L;

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

	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}

	public String getCvrNumber() {
		return CvrNumber;
	}

	public void setCvrNumber(String cvrNumber) {
		CvrNumber = cvrNumber;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	@Override
	public int compareTo(Merchant otherUser) {
		return CvrNumber.compareTo(otherUser.CvrNumber);
	}

}
