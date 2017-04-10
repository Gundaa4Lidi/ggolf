package com.galaxy.ggolf.domain;

import java.util.Collection;

public class UserDetail {
	
	private String UID;  //默认ID
	private String UserID;  //用户ID
	private String PhoneNum;  //手机号码
	private String HeadPhoto;  //头像
	private String UserName;  //用户名
	private String Sex;  //性别
	private String Birthday;  //出生日期
	private String Age;  //年龄
	private String Score;  //得分
	private String City;  //城市
	private Collection<String> PhotoList;  //图册
	private String Signature;  //个性签名
	private String Status;  //状态
	private String CustomTag;  //自定义标签
	private String Updated_TS;  //修改日期
	
	
	
	public UserDetail(String userID, String phoneNum, String headPhoto, String userName, String sex) {
		UserID = userID;
		PhoneNum = phoneNum;
		HeadPhoto = headPhoto;
		UserName = userName;
		Sex = sex;
	}
	public UserDetail(String uid,String userID, String phoneNum, String headPhoto, String userName, String sex, String birthday,
			String age, String score, String city, Collection<String> photoList, String signature, String status,
			String customTag, String updated_TS) {
		super();
		UID = uid;
		UserID = userID;
		PhoneNum = phoneNum;
		HeadPhoto = headPhoto;
		UserName = userName;
		Sex = sex;
		Birthday = birthday;
		Age = age;
		Score = score;
		City = city;
		PhotoList = photoList;
		Signature = signature;
		Status = status;
		CustomTag = customTag;
		Updated_TS = updated_TS;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getPhoneNum() {
		return PhoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		PhoneNum = phoneNum;
	}
	public String getHeadPhoto() {
		return HeadPhoto;
	}
	public void setHeadPhoto(String headPhoto) {
		HeadPhoto = headPhoto;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	public String getBirthday() {
		return Birthday;
	}
	public void setBirthday(String birthday) {
		Birthday = birthday;
	}
	public String getAge() {
		return Age;
	}
	public void setAge(String age) {
		Age = age;
	}
	public String getScore() {
		return Score;
	}
	public void setScore(String score) {
		Score = score;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public Collection<String> getPhotoList() {
		return PhotoList;
	}
	public void setPhotoList(Collection<String> photoList) {
		PhotoList = photoList;
	}
	public String getSignature() {
		return Signature;
	}
	public void setSignature(String signature) {
		Signature = signature;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getCustomTag() {
		return CustomTag;
	}
	public void setCustomTag(String customTag) {
		CustomTag = customTag;
	}
	public String getUpdated_TS() {
		return Updated_TS;
	}
	public void setUpdated_TS(String updated_TS) {
		Updated_TS = updated_TS;
	}
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	
	
	
	
	
	
}
