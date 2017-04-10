package com.galaxy.ggolf.domain;

import java.util.Collection;

import com.galaxy.ggolf.dto.CommentData;
import com.galaxy.ggolf.dto.LikeData;

public class Message {
	private String MsgID;
	private String SenderID;
	private String SenderName;
	private String SenderPhoto;
	private String Title;
	private String Details;
	private Collection<String> PhotoList;
	private String Video;
	private String Period;
	private String Status;
	private String Type;
	private String Club;
	private String Longitude;
	private String Latitude;
	private String Radius;
	private String Site;
	private String Distance;
	private String ReleaseOrNot;
	private String Created_TS;
	private String Updated_TS;
	private Collection<User> UserList;
	private CommentData commentData;
	private LikeData likeData;
	
	public Message(String msgID, String senderID, String senderName, String senderPhoto, String title, String details,
			Collection<String> photoList, String video, String period, String status, String type, String club, String longitude,
			String latitude, String radius, String site, String releaseOrNot, String created_TS, String updated_TS,
			Collection<User> userList) {
		MsgID = msgID;
		SenderID = senderID;
		SenderName = senderName;
		SenderPhoto = senderPhoto;
		Title = title;
		Details = details;
		PhotoList = photoList;
		Video = video;
		Period = period;
		Status = status;
		Type = type;
		Club = club;
		Longitude = longitude;
		Latitude = latitude;
		Radius = radius;
		Site = site;
		ReleaseOrNot = releaseOrNot;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
		UserList = userList;
	}
	public String getMsgID() {
		return MsgID;
	}
	public void setMsgID(String msgID) {
		MsgID = msgID;
	}
	public String getSenderID() {
		return SenderID;
	}
	public void setSenderID(String senderID) {
		SenderID = senderID;
	}
	public String getSenderName() {
		return SenderName;
	}
	public void setSenderName(String senderName) {
		SenderName = senderName;
	}
	public String getSenderPhoto() {
		return SenderPhoto;
	}
	public void setSenderPhoto(String senderPhoto) {
		SenderPhoto = senderPhoto;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDetails() {
		return Details;
	}
	public void setDetails(String details) {
		Details = details;
	}
	public Collection<String> getPhotoList() {
		return PhotoList;
	}
	public void setPhotoList(Collection<String> photoList) {
		PhotoList = photoList;
	}
	public String getVideo() {
		return Video;
	}
	public void setVideo(String video) {
		Video = video;
	}
	public String getPeriod() {
		return Period;
	}
	public void setPeriod(String period) {
		Period = period;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getClub() {
		return Club;
	}
	public void setClub(String club) {
		Club = club;
	}
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getSite() {
		return Site;
	}
	public void setSite(String site) {
		this.Site = site;
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
	public Collection<User> getUserList() {
		return UserList;
	}
	public void setUserList(Collection<User> userList) {
		UserList = userList;
	}
	public String getRadius() {
		return Radius;
	}
	public void setRadius(String radius) {
		Radius = radius;
	}
	public String getReleaseOrNot() {
		return ReleaseOrNot;
	}
	public void setReleaseOrNot(String releaseOrNot) {
		ReleaseOrNot = releaseOrNot;
	}
	public String getDistance() {
		return Distance;
	}
	public void setDistance(String distance) {
		Distance = distance;
	}
	public CommentData getCommentData() {
		return commentData;
	}
	public void setCommentData(CommentData commentData) {
		this.commentData = commentData;
	}
	public LikeData getLikeData() {
		return likeData;
	}
	public void setLikeData(LikeData likeData) {
		this.likeData = likeData;
	}
	
	

}
