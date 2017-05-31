package com.galaxy.ggolf.domain;

public class Rank {
   private int TrackCount;//足迹数量
   private String TrankRank;//足迹数量超过好友百分比
   private int ClubCount;//球场数量
   private String ClubRank;//球场数量超过好友百分比
   private String Handicap;//参考差点
   private String BestScore;//最好成绩
   private String Best;//一杆进洞
   private String FriendRank;//好友排名
public int getTrackCount() {
    return TrackCount;
}
public void setTrackCount(int trackCount) {
    TrackCount = trackCount;
}
public String getTrankRank() {
    return TrankRank;
}
public void setTrankRank(String trankRank) {
    TrankRank = trankRank;
}
public int getClubCount() {
    return ClubCount;
}
public void setClubCount(int clubCount) {
    ClubCount = clubCount;
}
public String getClubRank() {
    return ClubRank;
}
public void setClubRank(String clubRank) {
    ClubRank = clubRank;
}
public String getHandicap() {
    return Handicap;
}
public void setHandicap(String handicap) {
    Handicap = handicap;
}
public String getBestScore() {
    return BestScore;
}
public void setBestScore(String bestScore) {
    BestScore = bestScore;
}
public String getBest() {
    return Best;
}
public void setBest(String best) {
    Best = best;
}
public String getFriendRank() {
    return FriendRank;
}
public void setFriendRank(String friendRank) {
    FriendRank = friendRank;
}

}
