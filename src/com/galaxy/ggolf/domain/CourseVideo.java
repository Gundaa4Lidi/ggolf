package com.galaxy.ggolf.domain;

import java.util.Collection;

public class CourseVideo {
	private String UID;
	private String CreatorID;
	private String CourseID;
	private String RoomID;
	private String Password;
	private String RoomName;
	private String Created_TS;
	private String Updated_TS;
	private Collection<User> Users;
	
	public CourseVideo(String uID, String creatorID, String courseID, String roomID, String password, String roomName,
			String created_TS, String updated_TS) {
		UID = uID;
		CreatorID = creatorID;
		CourseID = courseID;
		RoomID = roomID;
		Password = password;
		RoomName = roomName;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
	}
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	public String getCreatorID() {
		return CreatorID;
	}
	public void setCreatorID(String creatorID) {
		CreatorID = creatorID;
	}
	public String getCourseID() {
		return CourseID;
	}
	public void setCourseID(String courseID) {
		CourseID = courseID;
	}
	public String getRoomID() {
		return RoomID;
	}
	public void setRoomID(String roomID) {
		RoomID = roomID;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getRoomName() {
		return RoomName;
	}
	public void setRoomName(String roomName) {
		RoomName = roomName;
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
	public Collection<User> getUsers() {
		return Users;
	}
	public void setUsers(Collection<User> users) {
		Users = users;
	}
	
	
	
	
}
