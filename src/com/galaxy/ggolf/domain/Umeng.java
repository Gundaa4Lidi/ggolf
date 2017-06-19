package com.galaxy.ggolf.domain;

public class Umeng {
	private String UMID;
	private String UserID;
	private String Umeng_Token;
	private String Created_TS;
	private String Updated_TS;
	
	public Umeng() {
		
	} 
	
	public Umeng(String uMID, String userID, String umeng_Token, String created_TS, String updated_TS) {
		UMID = uMID;
		UserID = userID;
		Umeng_Token = umeng_Token;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
	}

	public String getUMID() {
		return UMID;
	}
	public void setUMID(String uMID) {
		UMID = uMID;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getUmeng_Token() {
		return Umeng_Token;
	}
	public void setUmeng_Token(String umeng_Token) {
		Umeng_Token = umeng_Token;
	}
	public String getCreated_TS() {
		return Created_TS;
	}
	public void setCreated_TS(String created_TS) {
		Created_TS = created_TS;
	}
	public String getUpdated_TS() {
		return Updated_TS;
	}
	public void setUpdated_TS(String updated_TS) {
		Updated_TS = updated_TS;
	}
	
	
}
