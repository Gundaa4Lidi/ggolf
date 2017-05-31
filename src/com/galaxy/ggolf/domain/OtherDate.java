package com.galaxy.ggolf.domain;

public class OtherDate {
	private String UID;
	private String ClubID;
	private String ClubserveID;
	private String DateTime;
	private String Created_TS;
	private String Updated_TS;
	public OtherDate(String uID, String clubID, String clubserveID, String dateTime, String created_TS,
			String updated_TS) {
		UID = uID;
		ClubID = clubID;
		ClubserveID = clubserveID;
		DateTime = dateTime;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
	}
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	public String getClubID() {
		return ClubID;
	}
	public void setClubID(String clubID) {
		ClubID = clubID;
	}
	public String getClubserveID() {
		return ClubserveID;
	}
	public void setClubserveID(String clubserveID) {
		ClubserveID = clubserveID;
	}
	public String getDateTime() {
		return DateTime;
	}
	public void setDateTime(String dateTime) {
		DateTime = dateTime;
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
