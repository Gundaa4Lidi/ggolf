package com.galaxy.ggolf.dto;

import java.util.Collection;

import com.galaxy.ggolf.domain.Staff;

public class StaffSessionData {
	private String token;
	private String StaffId;
	private String Position;
	private String Staffname;
	private String StaffHead;
	private String OnlineCount;
	private Collection<Staff> OnlineStaff;
	public StaffSessionData(String token, String staffId, String position, String staffname, String staffHead,
			String onlineCount, Collection<Staff> onlineStaff) {
		this.token = token;
		StaffId = staffId;
		Position = position;
		Staffname = staffname;
		StaffHead = staffHead;
		OnlineCount = onlineCount;
		OnlineStaff = onlineStaff;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getStaffId() {
		return StaffId;
	}
	public void setStaffId(String staffId) {
		StaffId = staffId;
	}
	public String getPosition() {
		return Position;
	}
	public void setPosition(String position) {
		Position = position;
	}
	public String getStaffname() {
		return Staffname;
	}
	public void setStaffname(String staffname) {
		Staffname = staffname;
	}
	public String getStaffHead() {
		return StaffHead;
	}
	public void setStaffHead(String staffHead) {
		StaffHead = staffHead;
	}
	public String getOnlineCount() {
		return OnlineCount;
	}
	public void setOnlineCount(String onlineCount) {
		OnlineCount = onlineCount;
	}
	public Collection<Staff> getOnlineStaff() {
		return OnlineStaff;
	}
	public void setOnlineStaff(Collection<Staff> onlineStaff) {
		OnlineStaff = onlineStaff;
	} 
	
	

}
