package com.mwd.picking.model;

import java.io.Serializable;

public class User implements Serializable {

	  private static final long serialVersionUID = 1L;
	  
	  private String userId;
	  public String getUserId() {
		return userId;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userPasswd=" + password + ", userType="
				+ userType + ", storeCode=" + storeCode + "]";
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public void setPassword(String userPasswd) {
		this.password = userPasswd;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	private String userName;
	  private String password;
	  private String userType;
	  private String storeCode;
}
