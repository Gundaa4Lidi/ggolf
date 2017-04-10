package com.galaxy.ggolf.domain;

public class ArticleContent {
	private String ACID;
	private String ArticleID;
	private String Content;
	private String Created_TS;
	private String Updated_TS;
	public ArticleContent(String aCID, String articleID, String content, String created_TS, String updated_TS) {
		ACID = aCID;
		ArticleID = articleID;
		Content = content;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
	}
	public String getACID() {
		return ACID;
	}
	public void setACID(String aCID) {
		ACID = aCID;
	}
	public String getArticleID() {
		return ArticleID;
	}
	public void setArticleID(String articleID) {
		ArticleID = articleID;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
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
