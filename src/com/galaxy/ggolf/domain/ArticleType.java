package com.galaxy.ggolf.domain;

import java.util.Collection;

public class ArticleType {
	private String TypeID;
	private String TypeName;
	private String TypeKey;
	private String CategoryID;
	private String Created_TS;
	private String Updated_TS;
	private Collection<ArticleSubject> articleSubject;
	private Collection<Article> article;
	public ArticleType(String typeID, String typeName, String typeKey, String categoryID, String created_TS, String updated_TS) {
		TypeID = typeID;
		TypeName = typeName;
		TypeKey = typeKey;
		CategoryID = categoryID;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
	}
	
	public ArticleType(String typeID, String typeName, String typeKey, String categoryID, String created_TS, String updated_TS,
			Collection<ArticleSubject> articleSubject, Collection<Article> article) {
		TypeID = typeID;
		TypeName = typeName;
		TypeKey = typeKey;
		CategoryID = categoryID;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
		this.articleSubject = articleSubject;
		this.article = article;
	}

	public Collection<Article> getArticle() {
		return article;
	}

	public void setArticle(Collection<Article> article) {
		this.article = article;
	}
	
	public Collection<ArticleSubject> getArticleSubject() {
		return articleSubject;
	}

	public void setArticleSubject(Collection<ArticleSubject> articleSubject) {
		this.articleSubject = articleSubject;
	}

	public String getTypeID() {
		return TypeID;
	}
	public void setTypeID(String typeID) {
		TypeID = typeID;
	}
	public String getTypeName() {
		return TypeName;
	}
	public void setTypeName(String typeName) {
		TypeName = typeName;
	}
	public String getTypeKey() {
		return TypeKey;
	}
	public void setTypeKey(String typeKey) {
		TypeKey = typeKey;
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

	
}
