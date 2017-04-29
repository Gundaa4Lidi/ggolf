package com.galaxy.ggolf.domain;

import java.util.Collection;

public class CourseVideo {
	private String UID;
	private String CreatorID;
	private String RoomID;
	private String Password;
	private String RoomName;
	private Collection<User> Users;
	/**
	 * @param uID
	 * @param creatorID
	 * @param roomID
	 * @param password
	 * @param roomName
	 */
	public CourseVideo(String uID, String creatorID, String roomID, String password, String roomName) {
		super();
		UID = uID;
		CreatorID = creatorID;
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
