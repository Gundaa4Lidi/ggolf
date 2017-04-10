package com.galaxy.ggolf.domain;

public class PhotoAlbum {
    private String PhotoID; //图片编号
    private String Type;    //图片类型
    private String Url;     //图片路径
    private String Sort;	//用于排序
    private String Created_TS;	//创建日期
    private String Updated_TS;	//修改日期

	public PhotoAlbum() {
		// TODO Auto-generated constructor stub
	}
	
	public PhotoAlbum(String photoID, String type, String url,String sort, String created_TS, String updated_TS) {
		super();
		PhotoID = photoID;
		Type = type;
		Url = url;
		Sort = sort;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
	}

	

	public String getSort() {
		return Sort;
	}

	public void setSort(String sort) {
		Sort = sort;
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

	public String getPhotoID() {
		return PhotoID;
	}

	public void setPhotoID(String photoID) {
		PhotoID = photoID;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}
	
	
}
