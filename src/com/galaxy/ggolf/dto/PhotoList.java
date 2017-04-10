package com.galaxy.ggolf.dto;

public class PhotoList {
	private String Image;
	private String ID;
	private String Name;
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	public PhotoList(String image, String iD, String name) {
		Image = image;
		ID = iD;
		Name = name;
	}
	
	
}
