package com.galaxy.ggolf.domain;

import java.util.Collection;

import com.galaxy.ggolf.dto.Activity;

public class ClubOrder {
	private String UID;
	private String OrderID;
	private String UserID;
	private String ClubID;
	private String ClubName;
	private String ClubserveID;
	private String ClubserveName;
	private String ClubserveLimitTimeID;
	private String ClubservePriceID;
	private String State;
	private String CreateTime;
	private String Type;
	private String DownPayment;
	private String PayBillorNot;
	private String StartDate;
	private String StartTime;
	private String Names;
	private String Tel;
	private String ServiceExplain;
	private String ChargeID;
	private String Created_TS;
	private String Updated_TS;
	private Collection<Activity> Activities;
	
	
	public ClubOrder(String uID, String orderID, String userID, String clubID, String clubName, String clubserveID,
			String clubserveName, String clubserveLimitTimeID, String clubservePriceID, String state, String createTime,
			String type, String downPayment, String payBillorNot, String startDate, String startTime, String names,
			String tel, String serviceExplain, String chargeID, String created_TS, String updated_TS, Collection<Activity> activities) {
		UID = uID;
		OrderID = orderID;
		UserID = userID;
		ClubID = clubID;
		ClubName = clubName;
		ClubserveID = clubserveID;
		ClubserveName = clubserveName;
		ClubserveLimitTimeID = clubserveLimitTimeID;
		ClubservePriceID = clubservePriceID;
		State = state;
		CreateTime = createTime;
		Type = type;
		DownPayment = downPayment;
		PayBillorNot = payBillorNot;
		StartDate = startDate;
		StartTime = startTime;
		Names = names;
		Tel = tel;
		ServiceExplain = serviceExplain;
		ChargeID = chargeID;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
		Activities = activities;
	}




	public String getUID() {
		return UID;
	}


	public void setUID(String uID) {
		UID = uID;
	}


	public String getOrderID() {
		return OrderID;
	}


	public void setOrderID(String orderID) {
		OrderID = orderID;
	}


	public String getUserID() {
		return UserID;
	}


	public void setUserID(String userID) {
		UserID = userID;
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


	public String getClubserveID() {
		return ClubserveID;
	}


	public void setClubserveID(String clubserveID) {
		ClubserveID = clubserveID;
	}


	public String getClubserveName() {
		return ClubserveName;
	}


	public void setClubserveName(String clubserveName) {
		ClubserveName = clubserveName;
	}


	public String getClubserveLimitTimeID() {
		return ClubserveLimitTimeID;
	}


	public void setClubserveLimitTimeID(String clubserveLimitTimeID) {
		ClubserveLimitTimeID = clubserveLimitTimeID;
	}


	public String getClubservePriceID() {
		return ClubservePriceID;
	}


	public void setClubservePriceID(String clubservePriceID) {
		ClubservePriceID = clubservePriceID;
	}


	public String getState() {
		return State;
	}


	public void setState(String state) {
		State = state;
	}


	public String getCreateTime() {
		return CreateTime;
	}


	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}


	public String getType() {
		return Type;
	}


	public void setType(String type) {
		Type = type;
	}


	public String getDownPayment() {
		return DownPayment;
	}


	public void setDownPayment(String downPayment) {
		DownPayment = downPayment;
	}


	public String getPayBillorNot() {
		return PayBillorNot;
	}


	public void setPayBillorNot(String payBillorNot) {
		PayBillorNot = payBillorNot;
	}


	public String getStartDate() {
		return StartDate;
	}


	public void setStartDate(String startDate) {
		StartDate = startDate;
	}


	public String getStartTime() {
		return StartTime;
	}


	public void setStartTime(String startTime) {
		StartTime = startTime;
	}


	public String getNames() {
		return Names;
	}


	public void setNames(String names) {
		Names = names;
	}


	public String getTel() {
		return Tel;
	}


	public void setTel(String tel) {
		Tel = tel;
	}


	public String getServiceExplain() {
		return ServiceExplain;
	}


	public void setServiceExplain(String serviceExplain) {
		ServiceExplain = serviceExplain;
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


	public Collection<Activity> getActivities() {
		return Activities;
	}


	public void setActivities(Collection<Activity> activities) {
		Activities = activities;
	}

	public String getChargeID() {
		return ChargeID;
	}

	public void setChargeID(String chargeID) {
		ChargeID = chargeID;
	}
	
	
	
	
	
	
}
