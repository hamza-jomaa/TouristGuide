package model;

public class User {
	private String phoneNumber;
	private String password;
	private String name;
	private boolean isBlocked;
	private String role;

	public User() {

	}

	public User(String phoneNumber, String password, String name, boolean isBlocked, String role) {
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.name = name;
		this.isBlocked = isBlocked;
		this.role = role;
	}

	public User(String phoneNumber, boolean isBlocked) {
		this.phoneNumber = phoneNumber;
		this.isBlocked = isBlocked;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean blocked) {
		isBlocked = blocked;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
