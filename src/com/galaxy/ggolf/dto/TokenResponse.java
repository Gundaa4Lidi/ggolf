package com.galaxy.ggolf.dto;

public class TokenResponse {
	
	public String token;
	public String userId;
	public String account;
	public String name;
	public String head;
	
	public TokenResponse(String token, String userId,String account, String name, String head) {
		this.token = token;
		this.userId = userId;
		this.account = account;
		this.name = name;
		this.head = head;
	}
	

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}

	
	
	
	

}
