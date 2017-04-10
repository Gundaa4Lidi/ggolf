package com.galaxy.ggolf.domain;

public class NotifyList {
	private String NotifyID;
	private String MsgID;
	private String UserID;
	private String UserName;
	private String HeadPhoto;
	private String ReadOrNot;
	public NotifyList(String notifyID, String msgID, String userID, String userName, String headPhoto,
			String readOrNot) {
		NotifyID = notifyID;
		MsgID = msgID;
		UserID = userID;
		UserName = userName;
		HeadPhoto = headPhoto;
		ReadOrNot = readOrNot;
	}
	public String getNotifyID() {
		return NotifyID;
	}
	public void setNotifyID(String notifyID) {
		NotifyID = notifyID;
	}
	public String getMsgID() {
		return MsgID;
	}
	public void setMsgID(String msgID) {
		MsgID = msgID;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getHeadPhoto() {
		return HeadPhoto;
	}
	public void setHeadPhoto(String headPhoto) {
		HeadPhoto = headPhoto;
	}
	public String getReadOrNot() {
		return ReadOrNot;
	}
	public void setReadOrNot(String readOrNot) {
		ReadOrNot = readOrNot;
	}
	
	
	
}
