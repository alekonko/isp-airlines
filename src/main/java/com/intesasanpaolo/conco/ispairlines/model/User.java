package com.intesasanpaolo.conco.ispairlines.model;

import java.io.Serializable;
import java.util.Date;

public class User {

	private Serializable id;
	private int userid;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private Date dob;
	private String email;
	private boolean isAdmin;
	
	
	public User(){
		
	}
	
	public User(Serializable id, int userid, String userName, String password, String firstName, String lastName,
			Date dob, String email, boolean isAdmin) {
		super();
		this.id = id;
		this.userid = userid;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.email = email;
		this.isAdmin = isAdmin;
	}
	
	
	
	
	public User(String summary, String description) {
		// TODO Auto-generated constructor stub
	}

	public Serializable getId() {
		return id;
	}


	public void setId(Serializable id) {
		this.id = id;
	}


	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	
	@Override
	public String toString() {
		   return "User {\"userid\"=\"" + userid + "\",\"userName\"=\"" + userName + "\",\"password=\"" + password + "\", \"firstName=\"" + firstName
					+ "\", \"lastName=\"" + lastName + "\", \"dob=\"" + dob + "\", \"email\"=\"" + email + "\", \"isAdmin\"=\"" + isAdmin + "\"}";
	}

	public String getSummary() {
		// TODO Auto-generated method stub
		return "User {\"userid\"=\"" + userid + "\",\"userName\"=\"" + userName + "\",\"password=\"" + password + "\", \"firstName=\"" + firstName
				+ "\", \"lastName=\"" + lastName + "\", \"dob=\"" + dob + "\", \"email\"=\"" + email + "\", \"isAdmin\"=\"" + isAdmin + "\"}";
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return "User {\"userid\"=\"" + userid + "\",\"userName\"=\"" + userName + "\",\"password=\"" + password + "\", \"firstName=\"" + firstName
				+ "\", \"lastName=\"" + lastName + "\", \"dob=\"" + dob + "\", \"email\"=\"" + email + "\", \"isAdmin\"=\"" + isAdmin + "\"}";
	}
	
	
 
    
	
}
