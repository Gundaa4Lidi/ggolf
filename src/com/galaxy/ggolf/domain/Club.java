package com.galaxy.ggolf.domain;

import java.util.Collection;

public class Club {
    private String ClubID;  //球场ID
    private String ClubName;  //球场名称
    private String ClubPhoneNumber;  //球场联系电话
    private String ClubAddress;  //球场地址
    private String ClubType; //球场类别
    private Collection<String> ClubPhoto; //球场图片
    private String Logo; //球场Logo
    private String Province;  //省份
    private String City;  //城市
    private String Area;  //地区
    private String TotalStemNum; //总杆数
    private String TotalHole; //总洞数
    private String IsHot; //是否热门
    private String IsTop; //是否置顶轮播图
    private String Longitude;  //经度
    private String Latitude;  //纬度
    private String Distance; //距离
    private String Created_TS; //创建时间
    private String Updated_TS;//修改时间
    private Collection<ClubServe> clubServes;
    
    public Club() {
		// TODO Auto-generated constructor stub
	}
	
	public Club(String clubID, String clubName, String clubPhoneNumber, String clubAddress, String clubType,
			Collection<String> clubPhoto, String logo, String province, String city, String area, 
			String totalStemNum, String totalHole, String isHot, String isTop, String longitude,
			String latitude, String created_TS, String updated_TS) {
		ClubID = clubID;
		ClubName = clubName;
		ClubPhoneNumber = clubPhoneNumber;
		ClubAddress = clubAddress;
		ClubType = clubType;
		ClubPhoto = clubPhoto;
		Logo = logo;
		Province = province;
		City = city;
		Area = area;
		TotalStemNum = totalStemNum;
		TotalHole = totalHole;
		IsHot = isHot;
		IsTop = isTop;
		Longitude = longitude;
		Latitude = latitude;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
	}
	public String getClubID() {
		return ClubID;
	}
	public void setClubID(String clubID) {
		ClubID = clubID;
	}
	public String getClubName() {
		return ClubName;
	}
	public void setClubName(String clubName) {
		ClubName = clubName;
	}
	public String getClubPhoneNumber() {
		return ClubPhoneNumber;
	}
	public void setClubPhoneNumber(String clubPhoneNumber) {
		ClubPhoneNumber = clubPhoneNumber;
	}
	public String getClubAddress() {
		return ClubAddress;
	}
	public void setClubAddress(String clubAddress) {
		ClubAddress = clubAddress;
	}
	
	public String getClubType() {
		return ClubType;
	}
	public void setClubType(String clubType) {
		ClubType = clubType;
	}
	public Collection<String> getClubPhoto() {
		return ClubPhoto;
	}
	public void setClubPhoto(Collection<String> clubPhoto) {
		ClubPhoto = clubPhoto;
	}
	public String getLogo() {
		return Logo;
	}
	public void setLogo(String logo) {
		Logo = logo;
	}
	public String getProvince() {
		return Province;
	}
	public void setProvince(String province) {
		Province = province;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getArea() {
		return Area;
	}
	public void setArea(String area) {
		Area = area;
	}
	public String getTotalStemNum() {
		return TotalStemNum;
	}
	public void setTotalStemNum(String totalStemNum) {
		TotalStemNum = totalStemNum;
	}
	public String getTotalHole() {
		return TotalHole;
	}
	public void setTotalHole(String totalHole) {
		TotalHole = totalHole;
	}
	public String getLongitude() {
		return Longitude;
	}
	public String getIsHot() {
		return IsHot;
	}
	public void setIsHot(String isHot) {
		IsHot = isHot;
	}
	public String getIsTop() {
		return IsTop;
	}
	public void setIsTop(String isTop) {
		IsTop = isTop;
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
	public String getDistance() {
		return Distance;
	}
	public void setDistance(String distance) {
		Distance = distance;
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
	public Collection<ClubServe> getClubServes() {
		return clubServes;
	}
	public void setClubServes(Collection<ClubServe> clubServes) {
		this.clubServes = clubServes;
	}
    

	
    
    
    
	

    

	
}
