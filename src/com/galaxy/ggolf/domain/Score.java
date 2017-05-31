package com.galaxy.ggolf.domain;

public class Score {
   private String UserId;
   private String ScoreId;//计分板id
   private String PlayerName;//球员名字
   private String ClubName;//球场名字
   private String SiteOnePAR;//标准杆数1
   private String SiteTwoPAR;//标准杆数2
   private String ScoresOne;//前九洞杆数
   private String ScoresTwo;//后九洞杆数
   private String PuttersOne;//前九洞推杆数
   private String PuttersTwo;//后九洞推杆数
   private String UGrade;//用户自己的总杆数
   private String Grade;//总杆数(多个球员)
   private String Putter;//推杆数
   private String HIO;//一杆球
   private String Eagle;//老鹰球
   private String Bird;//小鸟球
   private String PARGrade;//标准杆
   private String Bogey;//柏忌
   private String DBogey;//双柏忌
   private String AVGThree;//３杆洞平均杆数
   private String AVGFour;//4杆洞
   private String AVGFive;//5
   private String Handicap;//参考差点
   private String Created_TS;
   private String Status;//0:未完成，1：完成未发布
   
   public Score(){
	   
   }

public Score(String userId, String scoreId, String playerName, String clubName, String siteOnePAR, String siteTwoPAR,
		String scoresOne, String scoresTwo, String puttersOne, String puttersTwo, String uGrade, String grade,
		String putter, String hIO, String eagle, String bird, String pARGrade, String bogey, String dBogey,
		String aVGThree, String aVGFour, String aVGFive, String handicap, String created_TS, String status) {
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
	UGrade = uGrade;
	Grade = grade;
	Putter = putter;
	HIO = hIO;
	Eagle = eagle;
	Bird = bird;
	PARGrade = pARGrade;
	Bogey = bogey;
	DBogey = dBogey;
	AVGThree = aVGThree;
	AVGFour = aVGFour;
	AVGFive = aVGFive;
	Handicap = handicap;
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

public String getUGrade() {
	return UGrade;
}

public void setUGrade(String uGrade) {
	UGrade = uGrade;
}

public String getGrade() {
	return Grade;
}

public void setGrade(String grade) {
	Grade = grade;
}

public String getPutter() {
	return Putter;
}

public void setPutter(String putter) {
	Putter = putter;
}

public String getHIO() {
	return HIO;
}

public void setHIO(String hIO) {
	HIO = hIO;
}

public String getEagle() {
	return Eagle;
}

public void setEagle(String eagle) {
	Eagle = eagle;
}

public String getBird() {
	return Bird;
}

public void setBird(String bird) {
	Bird = bird;
}

public String getPARGrade() {
	return PARGrade;
}

public void setPARGrade(String pARGrade) {
	PARGrade = pARGrade;
}

public String getBogey() {
	return Bogey;
}

public void setBogey(String bogey) {
	Bogey = bogey;
}

public String getDBogey() {
	return DBogey;
}

public void setDBogey(String dBogey) {
	DBogey = dBogey;
}

public String getAVGThree() {
	return AVGThree;
}

public void setAVGThree(String aVGThree) {
	AVGThree = aVGThree;
}

public String getAVGFour() {
	return AVGFour;
}

public void setAVGFour(String aVGFour) {
	AVGFour = aVGFour;
}

public String getAVGFive() {
	return AVGFive;
}

public void setAVGFive(String aVGFive) {
	AVGFive = aVGFive;
}

public String getHandicap() {
	return Handicap;
}

public void setHandicap(String handicap) {
	Handicap = handicap;
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
