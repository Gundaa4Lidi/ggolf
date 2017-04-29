package com.galaxy.ggolf.domain;

public class ClubServe {
	private String ClubserveID;
	private String ClubID;
	private String ClubName;
	private String Name;
	private String CancelExplain;
	private String ReserveExplain;
	private String ServiceExplain;
	private String MaxDay;
	private String Created_TS;
	private String Updated_TS;
	
	public ClubServe(String clubserveID, String clubID, String clubName, String name, String cancelExplain,
			String reserveExplain, String serviceExplain, String maxDay, String created_TS, String updated_TS) {
		ClubserveID = clubserveID;
		ClubID = clubID;
		ClubName = clubName;
		Name = name;
		CancelExplain = cancelExplain;
		ReserveExplain = reserveExplain;
		ServiceExplain = serviceExplain;
		MaxDay = maxDay;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
	}
	
	public String getClubserveID() {
		return ClubserveID;
	}
	public void setClubserveID(String clubserveID) {
		ClubserveID = clubserveID;
	}
	public String getClubID() {
		return ClubID;
	}
	public void setClubID(String clubID) {
		ClubID = clubID;
	}
	public String getClubName() {
		return ClubName;
	}
	public void setClubName(String clubName) {
		ClubName = clubName;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getCancelExplain() {
		return CancelExplain;
	}
	public void setCancelExplain(String cancelExplain) {
		CancelExplain = cancelExplain;
	}
	public String getReserveExplain() {
		return ReserveExplain;
	}
	public void setReserveExplain(String reserveExplain) {
		ReserveExplain = reserveExplain;
	}
	public String getServiceExplain() {
		return ServiceExplain;
	}
	public void setServiceExplain(String serviceExplain) {
		ServiceExplain = serviceExplain;
	}
	public String getMaxDay() {
		return MaxDay;
	}
	public void setMaxDay(String maxDay) {
		MaxDay = maxDay;
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
