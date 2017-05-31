package com.galaxy.ggolf.domain;

import java.util.Collection;

public class ClubFairway {

	private String UID;
	private String ClubID;
	private String HoleNum;
	private String FairwayName;
	private String Updated_TS;
	private Collection<String> Par;
	private Collection<String> Photo;	//根据球洞数创建图片,没有图片插入n个空字符
	public ClubFairway(String uID, String clubID, String holeNum, String fairwayName, String updated_TS, Collection<String> par,
			Collection<String> photo) {
		super();
		UID = uID;
		ClubID = clubID;
		HoleNum = holeNum;
		FairwayName = fairwayName;
		Updated_TS = updated_TS;
		Par = par;
		Photo = photo;
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
	public String getHoleNum() {
		return HoleNum;
	}
	public void setHoleNum(String holeNum) {
		HoleNum = holeNum;
	}
	public String getFairwayName() {
		return FairwayName;
	}
	public void setFairwayName(String fairwayName) {
		FairwayName = fairwayName;
	}
	public String getUpdated_TS() {
		return Updated_TS;
	}
	public void setUpdated_TS(String updated_TS) {
		Updated_TS = updated_TS;
	}
	public Collection<String> getPar() {
		return Par;
	}
	public void setPar(Collection<String> par) {
		Par = par;
	}
	public Collection<String> getPhoto() {
		return Photo;
	}
	public void setPhoto(Collection<String> photo) {
		Photo = photo;
	}
	
	
	
	

}
