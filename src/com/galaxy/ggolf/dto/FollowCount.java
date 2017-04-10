package com.galaxy.ggolf.dto;

public class FollowCount {

	private int fensNum;
	private int followNum;
	
	public FollowCount(int fensNum, int followNum) {
		this.fensNum = fensNum;
		this.followNum = followNum;
	}
	
	public int getFensNum() {
		return fensNum;
	}
	public void setFensNum(int fensNum) {
		this.fensNum = fensNum;
	}
	public int getFollowNum() {
		return followNum;
	}
	public void setFollowNum(int followNum) {
		this.followNum = followNum;
	}

	
}
