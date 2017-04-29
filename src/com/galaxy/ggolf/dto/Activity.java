package com.galaxy.ggolf.dto;

public class Activity {
	private String time;
	private String action;
	
	public Activity() {
		
	}
	
	public Activity(String time, String action) {
		this.time = time;
		this.action = action;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	
}
