package com.galaxy.ggolf.domain;

public class HistoryRank {
 private String Time;
 private String head_portrait;
 private String Name;
 
 
public HistoryRank(String time, String head_portrait, String name) {
    super();
    Time = time;
    this.head_portrait = head_portrait;
    Name = name;
}
public String getTime() {
	return Time;
}
public void setTime(String time) {
	Time = time;
}
public String getHead_portrait() {
	return head_portrait;
}
public void setHead_portrait(String head_portrait) {
	this.head_portrait = head_portrait;
}
public String getName() {
	return Name;
}
public void setName(String name) {
	Name = name;
}
 
}
