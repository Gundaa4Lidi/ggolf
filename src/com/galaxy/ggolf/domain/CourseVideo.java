package com.galaxy.ggolf.domain;

import java.util.Collection;

public class CourseVideo {
	private String UID;
	private String CreatorID;
	private String CourseID;
	private String RoomID;
	private String Password;
	private String RoomName;
	private Collection<User> Users;
	public CourseVideo(String uID, String creatorID, String courseID, String roomID, String password, String roomName) {
		UID = uID;
		CreatorID = creatorID;
		CourseID = courseID;
		RoomID = roomID;
		Password = password;
		RoomName = roomName;
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
	public void setCoachID(String courseID) {
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
	public Collection<User> getUsers() {
		return Users;
	}
	public void setUsers(Collection<User> users) {
		Users = users;
	}
	
	
}
