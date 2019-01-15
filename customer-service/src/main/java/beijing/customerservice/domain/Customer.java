package beijing.customerservice.domain;

import java.io.Serializable;

public class Customer implements Comparable<Customer>, Serializable {
	private static final long serialVersionUID = 1693295470116937316L;

	// Attributes of user
	public String firstName;
	public String lastName;

	String email;
	String cpr;
	String address;
	String birthdate;
	public String password;
	int phoneNumber;
	
	// Constructor
	public Customer(String firstName, String lastName, String cpr) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.setCpr(cpr);
//		setPassword();
	}

	// First name
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	// Last name
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	// Email
	public String getEmail() {
		return email;
	}

	// Cpr
	public String getCpr() {
		return cpr;
	}

	public void setCpr(String cpr) {
		this.cpr = cpr;
//		cprToBirthdate();
	}

	// Phone no
	public int getPhonenumber() {
		return (phoneNumber);
	}

	public void setPhonenumber(int phoneNo) {
		this.phoneNumber = phoneNo;
	}

//	// Password
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword() {
//		this.password = firstName;
//	}
//
//	// Birthdate
//	public void cprToBirthdate() {
//		int decade = Character.getNumericValue(cpr.charAt(4));
//		if (decade > 3) {
//			this.birthdate = cpr.substring(0, 2) + "." + cpr.substring(2, 4) + ".19" + cpr.substring(4, 6);
//		} else {
//			this.birthdate = cpr.substring(0, 2) + "." + cpr.substring(2, 4) + ".20" + cpr.substring(4, 6);
//		}
//	}
//
//	public String getBirthdate() {
//		return birthdate;
//	}

	// Overwriting compareable
	@Override
	public int compareTo(Customer otherUser) {
		return cpr.compareTo(otherUser.cpr);
	}

}
