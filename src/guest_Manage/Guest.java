package guest_Manage;

import java.io.Serializable;

public class Guest implements Serializable{

	/**
	 * 
	 */
	
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNo;
	
	private static final long serialVersionUID = 1L;

	public Guest(String firstName, String lastName, String email, String phoneNo) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNo = phoneNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public void setName(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getName() {
		return (this.firstName + " " + this.lastName); 
	}
	
	public boolean isUsed(Guest obj) {
		if (this.getName().equalsIgnoreCase(obj.getName()) ||
			this.getEmail().equalsIgnoreCase(obj.getEmail()) ||
			this.getPhoneNo().equalsIgnoreCase(obj.getPhoneNo())) {
			return true;
		}
		return false;
	}
	
	public boolean isNameUsed(String fullName) {
		if (this.getName().equalsIgnoreCase(fullName)) {
			return true;
		}
		return false;
	}
	
	public boolean isEmailUsed(String email) {
		if (this.getEmail().equalsIgnoreCase(email)) {
			return true;
		}
		return false;
	}
	
	public boolean isPhoneNoUsed(String phoneNo) {
		if (this.getPhoneNo().equalsIgnoreCase(phoneNo)) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return ("Nume: " + this.lastName + " " + this.firstName + ", Email: " + this.email + ", Telefon: " + this.phoneNo);
	}
	
	
}
