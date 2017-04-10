package com.galaxy.ggolf.domain;

import java.util.Collection;

public class ArticleSubject {
	private String SubjectID;
	private String SubjectName;
	private String Attr;
	private String TypeID;
	private String Image;
	private String CategoryID;
	private String Created_TS;
	private String Updated_TS;
	private Collection<Article> articles;
	public ArticleSubject(String subjectID, String subjectName, String attr, String typeID, String image, String categoryID,
			String created_TS, String updated_TS, Collection<Article> articles) {
		SubjectID = subjectID;
		SubjectName = subjectName;
		Attr = attr;
		TypeID = typeID;
		Image = image;
		CategoryID = categoryID;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
		this.articles = articles;
	}
	public String getSubjectID() {
		return SubjectID;
	}
	public void setSubjectID(String subjectID) {
		SubjectID = subjectID;
	}
	public String getSubjectName() {
		return SubjectName;
	}
	public void setSubjectName(String subjectName) {
		SubjectName = subjectName;
	}
	public String getAttr() {
		return Attr;
	}
	public void setAttr(String attr) {
		Attr = attr;
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
	public String getCategoryID() {
		return CategoryID;
	}
	public void setCategoryID(String categoryID) {
		CategoryID = categoryID;
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
	public Collection<Article> getArticles() {
		return articles;
	}
	public void setArticles(Collection<Article> articles) {
		this.articles = articles;
	}
	
	
	
	

}
