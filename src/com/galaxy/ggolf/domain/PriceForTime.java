package com.galaxy.ggolf.domain;

public class PriceForTime {
    private String ClubservePriceID;
    private String ClubserveID;
    private String ClubID;
    private String Week;
    private String Time;
    private String DownPayment;
    private String OtherPrice;
    private String Type;
    private String IsPrivilege;
    private String IsDeposit;
    private String IsValid;
    private String DateTime;
    private String Created_TS;
    private String Updated_TS;
    
	public PriceForTime(String clubservePriceID, String clubserveID, String clubID, String week, String time,
			String downPayment, String otherPrice, String type, String isPrivilege, String isDeposit,
			String isValid,String dateTime, String created_TS,String updated_TS) {
		ClubservePriceID = clubservePriceID;
		ClubserveID = clubserveID;
		ClubID = clubID;
		Week = week;
		Time = time;
		DownPayment = downPayment;
		OtherPrice = otherPrice;
		Type = type;
		IsPrivilege = isPrivilege;
		IsDeposit = isDeposit;
		IsValid = isValid;
		DateTime = dateTime;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
	}
	
	public String getClubservePriceID() {
		return ClubservePriceID;
	}
	public void setClubservePriceID(String clubservePriceID) {
		ClubservePriceID = clubservePriceID;
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
	public String getWeek() {
		return Week;
	}
	public void setWeek(String week) {
		Week = week;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getDownPayment() {
		return DownPayment;
	}
	public void setDownPayment(String downPayment) {
		DownPayment = downPayment;
	}
	public String getOtherPrice() {
		return OtherPrice;
	}
	public void setOtherPrice(String otherPrice) {
		OtherPrice = otherPrice;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getIsPrivilege() {
		return IsPrivilege;
	}
	public void setIsPrivilege(String isPrivilege) {
		IsPrivilege = isPrivilege;
	}
	public String getIsDeposit() {
		return IsDeposit;
	}
	public void setIsDeposit(String isDeposit) {
		IsDeposit = isDeposit;
	}
	public String getIsValid() {
		return IsValid;
	}
	public void setIsValid(String isValid) {
		IsValid = isValid;
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
