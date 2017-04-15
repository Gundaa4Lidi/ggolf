package com.galaxy.ggolf.domain;

public class FollowUser {
	private String UserID;  //默认ID
	private String Name;  //用户名
	private String Age;  //年龄
	private String Sex;  //性别
	private String Head;  //头像
	private String Relation;   //关系(已关注,互相关注,黑名单)
	private String Remark;	   //好友备注
	private String Status;	   //状态(0:提醒有新的好友,1:已查看)
	private String UID;
	
	public FollowUser(String userID, String name, String age, String sex, String head, String relation, String remark,
			String status,String uID) {
		UserID = userID;
		Name = name;
		Age = age;
		Sex = sex;
		Head = head;
		Relation = relation;
		Remark = remark;
		Status = status;
		UID = uID;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getAge() {
		return Age;
	}
	public void setAge(String age) {
		Age = age;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	public String getHead() {
		return Head;
	}
	public void setHead(String head) {
		Head = head;
	}
	public String getRelation() {
		return Relation;
	}
	public void setRelation(String relation) {
		Relation = relation;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	
}
