package com.inautix.market;

public class User {
	String username;
	String password;
	UserDetail details;
	protected int uid;
	public String getUsername() {
		return username;
	}
	public User() {
		details=new UserDetail();
	}

	public User(String username2, String password2) {
		username=username2;
		password= password2;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserDetail getDetails() {
		return details;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public void setEmail(String email) {
		details.setEmail(email);
	}
	public void setPhone(String phone) {
		details.setPhone(phone);
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", details=" + details + "]";
	}
}
