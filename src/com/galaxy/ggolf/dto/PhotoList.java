package com.galaxy.ggolf.dto;

public class PhotoList {
	private String Image;
	private String Intro;
	
	public PhotoList() {
		// TODO Auto-generated constructor stub
	}
	public PhotoList(String image, String intro) {
		Image = image;
		Intro = intro;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	public String getIntro() {
		return Intro;
	}
	public void setIntro(String intro) {
		Intro = intro;
	}
	
	
}
