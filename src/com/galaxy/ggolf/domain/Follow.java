package com.galaxy.ggolf.domain;

public class Follow {
	private String UID;        //默认编号
	private String UserID;	   //用户编号
	private String FenID;	   //关注人的编号
	private String Relation;   //关系(已关注,互相关注,黑名单)
	private String Remark;	   //好友备注
	private String Status;	   //状态(0:提醒有新的好友,1:已查看)
	private String Created_TS; //创建日期
	private String Updated_TS; //修改日期
	public Follow(String uID, String userID, String fenID, String relation, String remark, String status,
			String created_TS, String updated_TS) {
		UID = uID;
		UserID = userID;
		FenID = fenID;
		Relation = relation;
		Remark = remark;
		Status = status;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
	}

	public Follow(String userID, String fenID, String relation, String status) {
		UserID = userID;
		FenID = fenID;
		Relation = relation;
		Status = status;
	}

	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getFenID() {
		return FenID;
	}
	public void setFenID(String fenID) {
		FenID = fenID;
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
