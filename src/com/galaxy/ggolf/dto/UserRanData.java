package com.galaxy.ggolf.dto;

import java.util.Collection;

public class UserRanData {
    private String userID;
    private String phoneNum;
    private String headPhoto;
    private String name;
    private String sex;
    private String birthday;
    private String score;
    private String city;
    private Collection<String> photoList;
    private Collection<String> customTag;
    
	public UserRanData(String userID, String phoneNum, String headPhoto, String name, String sex, String birthday,
			String score, String city, Collection<String> photoList, Collection<String> customTag) {
		this.userID = userID;
		this.phoneNum = phoneNum;
		this.headPhoto = headPhoto;
		this.name = name;
		this.sex = sex;
		this.birthday = birthday;
		this.score = score;
		this.city = city;
		this.photoList = photoList;
		this.customTag = customTag;
	}
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getHeadPhoto() {
		return headPhoto;
	}
	public void setHeadPhoto(String headPhoto) {
		this.headPhoto = headPhoto;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Collection<String> getPhotoList() {
		return photoList;
	}
	public void setPhotoList(Collection<String> photoList) {
		this.photoList = photoList;
	}
	public Collection<String> getCustomTag() {
		return customTag;
	}
	public void setCustomTag(Collection<String> customTag) {
		this.customTag = customTag;
	}
    
    
    
}
