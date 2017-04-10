package com.galaxy.ggolf.domain;

public class Staff {
	private String UID;  //默认ID
	private String StaffName;  //员工名称
	private String Phone;  //手机号码
	private String StaffID;  //员工账号ID
	private String Password;  //密码
	private String Head;  //头像
	private String Created_TS;  //创建日期
	private String Updated_TS;  //修改日期
	private String Status;  //状态
	private String Position;  //职位
	private String WorkPlace;  //工作地点
	
	
	public Staff(String uid, String staffName, String phone, String staffID, String password, String head, String created_TS,
			String updated_TS, String status, String position, String workPlace) {
		super();
		UID = uid;
		StaffName = staffName;
		Phone = phone;
		StaffID = staffID;
		Password = password;
		Head = head;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
		Status = status;
		Position = position;
		WorkPlace = workPlace;
	}
	public String getUID() {
		return UID;
	}
	public void setUID(String uid) {
		UID = uid;
	}
	public String getStaffName() {
		return StaffName;
	}
	public void setStaffName(String staffName) {
		StaffName = staffName;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getStaffID() {
		return StaffID;
	}
	public void setStaffID(String staffID) {
		StaffID = staffID;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getHead() {
		return Head;
	}
	public void setHead(String head) {
		Head = head;
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
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getPosition() {
		return Position;
	}
	public void setPosition(String position) {
		Position = position;
	}
	public String getWorkPlace() {
		return WorkPlace;
	}
	public void setWorkPlace(String workPlace) {
		WorkPlace = workPlace;
	}
	@Override
	public String toString() {
		return "Staff [UID=" + UID + ", StaffName=" + StaffName + ", Phone=" + Phone + ", StaffID=" + StaffID
				+ ", Password=" + Password + ", Created_TS=" + Created_TS + ", Updated_TS=" + Updated_TS + ", Status="
				+ Status + ", Position=" + Position + ", WorkPlace=" + WorkPlace + "]";
	}
	
	
	
	
}
