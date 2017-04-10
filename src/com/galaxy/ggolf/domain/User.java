package com.galaxy.ggolf.domain;

public class User {
	private String userID;  //默认ID
	private String phone;  //手机号码
	private String name;  //用户名
	private String age;  //年龄
	private String sex;  //性别
	private String password;  //密码
	private String head_portrait;  //头像
	private String longitude;  //经度
	private String latitude;  //纬度
	private String distance; //距离
	private String wechat;  //微信账号
	private String Created_TS;  //创建日期
	private String Updated_TS;  //修改日期
	
	public User(){
		
	}
	
	public User(String userID, String phone, String name, String age, String sex, String password, String head_portrait,
			String longitude, String latitude, String wechat, String created_TS, String updated_TS) {
		super();
		this.userID = userID;
		this.phone = phone;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.password = password;
		this.head_portrait = head_portrait;
		this.longitude = longitude;
		this.latitude = latitude;
		this.wechat = wechat;
		this.Created_TS = created_TS;
		this.Updated_TS = updated_TS;
	}

	public User(String userID, String phone, String name, String age, String sex, String head_portrait,
			String longitude, String latitude, String distance, String wechat, String created_TS) {
		this.userID = userID;
		this.phone = phone;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.head_portrait = head_portrait;
		this.longitude = longitude;
		this.latitude = latitude;
		this.distance = distance;
		this.wechat = wechat;
		this.Created_TS = created_TS;
	}

	public User(String userID, String phone, String name, String age, String sex, String head_portrait) {
		this.userID = userID;
		this.phone = phone;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.head_portrait = head_portrait;
	}

	public User(String phone, String password, String name, String head_portrait) {
		super();
		this.phone = phone;
		this.password = password;
		this.name = name;
		this.head_portrait = head_portrait;
	}
	
	public User(String wechat,String name,String head_portrait){
		super();
		this.wechat = wechat;
		this.name = name;
		this.head_portrait = head_portrait;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPhone() {
		return phone;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHead_portrait() {
		return head_portrait;
	}

	public void setHead_portrait(String head_portrait) {
		this.head_portrait = head_portrait;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
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
