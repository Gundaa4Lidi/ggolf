package com.galaxy.ggolf.domain;

public class Score {
   private String UserId;
   private String ScoreId;//计分板id
   private String PlayerName;
   private String ClubName;
   private String SiteOnePAR;//标准杆数1
   private String SiteTwoPAR;//标准杆数2
   private String ScoresOne;//前九洞杆数
   private String ScoresTwo;//后九洞杆数
   private String PuttersOne;//前九洞推杆数
   private String PuttersTwo;//后九洞推杆数
   private String Created_TS;
   private String Status;//0:未完成，1：完成未发布
   
   public Score(){
	   
   }

public Score(String userId, String scoreId, String playerName, String clubName, String siteOnePAR, String siteTwoPAR,
		String scoresOne, String scoresTwo, String puttersOne, String puttersTwo, String created_TS, String status) {
	super();
	UserId = userId;
	ScoreId = scoreId;
	PlayerName = playerName;
	ClubName = clubName;
	SiteOnePAR = siteOnePAR;
	SiteTwoPAR = siteTwoPAR;
	ScoresOne = scoresOne;
	ScoresTwo = scoresTwo;
	PuttersOne = puttersOne;
	PuttersTwo = puttersTwo;
	Created_TS = created_TS;
	Status = status;
}

public String getUserId() {
	return UserId;
}

public void setUserId(String userId) {
	UserId = userId;
}

public String getScoreId() {
	return ScoreId;
}

public void setScoreId(String scoreId) {
	ScoreId = scoreId;
}

public String getPlayerName() {
	return PlayerName;
}

public void setPlayerName(String playerName) {
	PlayerName = playerName;
}

public String getClubName() {
	return ClubName;
}

public void setClubName(String clubName) {
	ClubName = clubName;
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

public String getCreated_TS() {
	return Created_TS;
}

public void setCreated_TS(String created_TS) {
	Created_TS = created_TS;
}

public String getStatus() {
	return Status;
}

public void setStatus(String status) {
	Status = status;
}

}
