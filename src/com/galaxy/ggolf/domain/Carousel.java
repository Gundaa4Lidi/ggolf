package com.galaxy.ggolf.domain;

public class Carousel {
	private String UID;
	private String Title;
	private String Type;
	private String TypeID;
	private String Image;
	private String Url;
	private String Created_TS;
	private String Updated_TS;
	public Carousel(String uID, String title, String type, String typeID, String image, String url, String created_TS,
			String updated_TS) {
		UID = uID;
		Title = title;
		Type = type;
		TypeID = typeID;
		Image = image;
		Url = url;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
	}
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getTypeID() {
		return TypeID;
	}
	public void setTypeID(String typeID) {
		TypeID = typeID;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	public String getCreated_TS() {
		return Created_TS;
	}
	public void setCreated_TS(String created_TS) {
		Created_TS = created_TS;
	}
	public String getUpdated_TS() {
		return Updated_TS;
	}
	public void setUpdated_TS(String updated_TS) {
		Updated_TS = updated_TS;
	}
	
}
