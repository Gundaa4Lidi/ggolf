package com.galaxy.ggolf.domain;

import java.util.Collection;

public class Club {
    private String ClubID;  //球场ID
    private String ClubName;  //球场名称
    private String ClubPhoneNumber;  //球场联系电话
    private String ClubAddress;  //球场地址
    private String ClubType; //球场类别
    private Collection<String> ClubPhoto; //球场图片
    private String Province;  //省份
    private String City;  //城市
    private String Area;  //地区
    private String Price; //价格
    private String DiscountPrice; //优惠价
    private String TotalStemNum; //总杆数
    private String TotalHole; //总洞数
    private String Longitude;  //经度
    private String Latitude;  //纬度
    private String Created_TS; //创建时间
    
    
	public Club(String clubID, String clubName, String clubPhoneNumber, String clubAddress,String clubType,
			Collection<String> clubPhoto, String province, String city, String area, String price, String discountPrice,
			String totalStemNum, String totalHole, String longitude, String latitude, String created_TS) {
		super();
		ClubID = clubID;
		ClubName = clubName;
		ClubPhoneNumber = clubPhoneNumber;
		ClubAddress = clubAddress;
		ClubType = clubType;
		ClubPhoto = clubPhoto;
		Province = province;
		City = city;
		Area = area;
		Price = price;
		DiscountPrice = discountPrice;
		TotalStemNum = totalStemNum;
		TotalHole = totalHole;
		Longitude = longitude;
		Latitude = latitude;
		Created_TS = created_TS;
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
	public String getPrice() {
		return Price;
	}
	public void setPrice(String price) {
		Price = price;
	}
	public String getDiscountPrice() {
		return DiscountPrice;
	}
	public void setDiscountPrice(String discountPrice) {
		DiscountPrice = discountPrice;
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
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getCreated_TS() {
		return Created_TS;
	}
	public void setCreated_TS(String created_TS) {
		Created_TS = created_TS;
	}
    

	
    
    
    
	

    

	
}
