package com.galaxy.ggolf.domain;

public class ClubserveLimitTime {

    private String ClubserveLimitTimeID;
    private String Name;
	private String ClubID;
	private String ClubserveID;
    private String Price;
    private String StartTime;
    private String EndTime;
    private String Count;
    private String Date;
    private String ServiceExplain;
    private String BeginStartTime;
    private String BeginEndTime;
    private String Created_TS;
    private String Updated_TS;
    
	public ClubserveLimitTime(String clubserveLimitTimeID, String name, String clubID, String clubserveID, String price, String startTime,
			String endTime, String count, String date, String serviceExplain, String beginStartTime,
			String beginEndTime, String created_TS, String updated_TS) {
		ClubserveLimitTimeID = clubserveLimitTimeID;
		Name = name;
		ClubID = clubID;
		ClubserveID = clubserveID;
		Price = price;
		StartTime = startTime;
		EndTime = endTime;
		Count = count;
		Date = date;
		ServiceExplain = serviceExplain;
		BeginStartTime = beginStartTime;
		BeginEndTime = beginEndTime;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
	}
	
	
	public String getClubserveLimitTimeID() {
		return ClubserveLimitTimeID;
	}
	public void setClubserveLimitTimeID(String clubserveLimitTimeID) {
		ClubserveLimitTimeID = clubserveLimitTimeID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
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
	public String getPrice() {
		return Price;
	}
	public void setPrice(String price) {
		Price = price;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	public String getCount() {
		return Count;
	}
	public void setCount(String count) {
		Count = count;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getServiceExplain() {
		return ServiceExplain;
	}
	public void setServiceExplain(String serviceExplain) {
		ServiceExplain = serviceExplain;
	}
	public String getBeginStartTime() {
		return BeginStartTime;
	}
	public void setBeginStartTime(String beginStartTime) {
		BeginStartTime = beginStartTime;
	}
	public String getBeginEndTime() {
		return BeginEndTime;
	}
	public void setBeginEndTime(String beginEndTime) {
		BeginEndTime = beginEndTime;
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
