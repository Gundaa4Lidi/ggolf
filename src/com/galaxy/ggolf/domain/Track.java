package com.galaxy.ggolf.domain;

import java.util.Collection;

public class Track {
  private String UserId;//用户id
  private String TrackId;//足迹id
  private String Grade; //杆数
  private String Created_TS;//发布时间
  private Collection<String> photoList;//图片
  private String Content; //足迹内容
  private String ClubName;//球场名字
  private String PlayerName;//运动员名字
  private String SiteOnePAR;//标准杆数1
  private String SiteTwoPAR;//标准杆数2
  private String ScoresOne;//前九洞杆数
  private String ScoresTwo;//后九洞杆数
  private String PuttersOne;//前九洞推杆数
  private String PuttersTwo;//后九洞推杆数
  public Track(){
	  
  }
public Track(String userId, String trackId, String grade, String created_TS, Collection<String> photoList,
		String content, String clubName, String playerName, String siteOnePAR, String siteTwoPAR, String scoresOne,
		String scoresTwo, String puttersOne, String puttersTwo) {
	super();
	UserId = userId;
	TrackId = trackId;
	Grade = grade;
	Created_TS = created_TS;
	this.photoList = photoList;
	Content = content;
	ClubName = clubName;
	PlayerName = playerName;
	SiteOnePAR = siteOnePAR;
	SiteTwoPAR = siteTwoPAR;
	ScoresOne = scoresOne;
	ScoresTwo = scoresTwo;
	PuttersOne = puttersOne;
	PuttersTwo = puttersTwo;
}
public String getUserId() {
	return UserId;
}
public void setUserId(String userId) {
	UserId = userId;
}
public String getTrackId() {
	return TrackId;
}
public void setTrackId(String trackId) {
	TrackId = trackId;
}
public String getGrade() {
	return Grade;
}
public void setGrade(String grade) {
	Grade = grade;
}
public String getCreated_TS() {
	return Created_TS;
}
public void setCreated_TS(String created_TS) {
	Created_TS = created_TS;
}
public Collection<String> getPhotoList() {
	return photoList;
}
public void setPhotoList(Collection<String> photoList) {
	this.photoList = photoList;
}
public String getContent() {
	return Content;
}
public void setContent(String content) {
	Content = content;
}
public String getClubName() {
	return ClubName;
}
public void setClubName(String clubName) {
	ClubName = clubName;
}
public String getPlayerName() {
	return PlayerName;
}
public void setPlayerName(String playerName) {
	PlayerName = playerName;
}
public String getSiteOnePAR() {
	return SiteOnePAR;
}
public void setSiteOnePAR(String siteOnePAR) {
	SiteOnePAR = siteOnePAR;
}
public String getSiteTwoPAR() {
	return SiteTwoPAR;
}
public void setSiteTwoPAR(String siteTwoPAR) {
	SiteTwoPAR = siteTwoPAR;
}
public String getScoresOne() {
	return ScoresOne;
}
public void setScoresOne(String scoresOne) {
	ScoresOne = scoresOne;
}
public String getScoresTwo() {
	return ScoresTwo;
}
public void setScoresTwo(String scoresTwo) {
	ScoresTwo = scoresTwo;
}
public String getPuttersOne() {
	return PuttersOne;
}
public void setPuttersOne(String puttersOne) {
	PuttersOne = puttersOne;
}
public String getPuttersTwo() {
	return PuttersTwo;
}
public void setPuttersTwo(String puttersTwo) {
	PuttersTwo = puttersTwo;
}

}
