package com.galaxy.ggolf.domain;

import java.util.Collection;

public class ClubDetail {
	private String UID;  //默认id
	private String ClubID;	//球场ID
	private String ClubName;  //球场名称
	private String Mode;  //球场模式
	private String TotalHole;  //球场总洞数
	private String TotalStemNum;  //总杆数
	private String PhoneNum;  //电话号码
	private String CreateTime;  //球场创建时间
	private String Stylist;  //设计师
	private String Square;  //球场面积
	private String Length;  //球场长度
	private String PuttingSeed;  //果岭草种
	private String FairwaySeed;  //球道草种
	private String Address;  //球场地址
	private String Intro;  //球场简介
	private String Updated_TS;  //修改时间
	private Collection<String> PhotoList;  //球场图片
	private Collection<String> MapImg;  //地图图片
	private String Facility; //球场设施
	
	public ClubDetail(String clubID, String clubName, String totalHole, String totalStemNum, String phoneNum,
			String address, Collection<String> photoList) {
		ClubID = clubID;
		ClubName = clubName;
		TotalHole = totalHole;
		TotalStemNum = totalStemNum;
		PhoneNum = phoneNum;
		Address = address;
		PhotoList = photoList;
	}



	public ClubDetail(String uID, String clubID, String clubName, String mode, String totalHole, String totalStemNum,
			String phoneNum, String createTime, String stylist, String square, String length, String puttingSeed,
			String fairwaySeed, String address, String intro, String updated_TS, Collection<String> photoList,
			Collection<String> mapImg, String facility) {
		super();
		UID = uID;
		ClubID = clubID;
		ClubName = clubName;
		Mode = mode;
		TotalHole = totalHole;
		TotalStemNum = totalStemNum;
		PhoneNum = phoneNum;
		CreateTime = createTime;
		Stylist = stylist;
		Square = square;
		Length = length;
		PuttingSeed = puttingSeed;
		FairwaySeed = fairwaySeed;
		Address = address;
		Intro = intro;
		Updated_TS = updated_TS;
		PhotoList = photoList;
		MapImg = mapImg;
		Facility = facility;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
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

	public String getMode() {
		return Mode;
	}

	public void setMode(String mode) {
		Mode = mode;
	}

	public String getTotalHole() {
		return TotalHole;
	}

	public void setTotalHole(String totalHole) {
		TotalHole = totalHole;
	}

	public String getTotalStemNum() {
		return TotalStemNum;
	}

	public void setTotalStemNum(String totalStemNum) {
		TotalStemNum = totalStemNum;
	}

	public String getPhoneNum() {
		return PhoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		PhoneNum = phoneNum;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getStylist() {
		return Stylist;
	}

	public void setStylist(String stylist) {
		Stylist = stylist;
	}

	public String getSquare() {
		return Square;
	}

	public void setSquare(String square) {
		Square = square;
	}

	public String getLength() {
		return Length;
	}

	public void setLength(String length) {
		Length = length;
	}

	public String getPuttingSeed() {
		return PuttingSeed;
	}

	public void setPuttingSeed(String puttingSeed) {
		PuttingSeed = puttingSeed;
	}

	public String getFairwaySeed() {
		return FairwaySeed;
	}

	public void setFairwaySeed(String fairwaySeed) {
		FairwaySeed = fairwaySeed;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getIntro() {
		return Intro;
	}

	public void setIntro(String intro) {
		Intro = intro;
	}

	public String getUpdated_TS() {
		return Updated_TS;
	}

	public void setUpdated_TS(String updated_TS) {
		Updated_TS = updated_TS;
	}

	public Collection<String> getPhotoList() {
		return PhotoList;
	}

	public void setPhotoList(Collection<String> photoList) {
		PhotoList = photoList;
	}

	public Collection<String> getMapImg() {
		return MapImg;
	}

	public void setMapImg(Collection<String> mapImg) {
		MapImg = mapImg;
	}

	public String getFacility() {
		return Facility;
	}

	public void setFacility(String facility) {
		Facility = facility;
	}
	
	
	
	

}
