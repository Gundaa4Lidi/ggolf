package com.galaxy.ggolf.domain;

import java.util.Collection;

public class ArticleCategory {
	private String CategoryID;
	private String CategoryName;
	private String SubOrNot;
	private String Created_TS;
	private String Updated_TS;
	private Collection<ArticleType> articleType;
	
	public ArticleCategory(String categoryID, String categoryName, String subOrNot, String created_TS, String updated_TS) {
		CategoryID = categoryID;
		CategoryName = categoryName;
		SubOrNot = subOrNot;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
	}
	
	public ArticleCategory(String categoryID, String categoryName, String subOrNot, String created_TS, String updated_TS,
			Collection<ArticleType> articleType) {
		CategoryID = categoryID;
		CategoryName = categoryName;
		SubOrNot = subOrNot;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
		this.articleType = articleType;
	}
	
	public String getSubOrNot() {
		return SubOrNot;
	}

	public void setSubOrNot(String subOrNot) {
		SubOrNot = subOrNot;
	}

	public Collection<ArticleType> getArticleType() {
		return articleType;
	}

	public void setArticleType(Collection<ArticleType> articleType) {
		this.articleType = articleType;
	}

	public String getCategoryID() {
		return CategoryID;
	}
	public void setCategoryID(String categoryID) {
		CategoryID = categoryID;
	}
	public String getCategoryName() {
		return CategoryName;
	}
	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
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
