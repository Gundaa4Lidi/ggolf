package com.galaxy.ggolf.domain;

public class Article {
    private String ArticleID;
    private String CategoryID;
    private String Title;
    private String Cover;
    private String Content;
    private String RootIN;
    private String ReleaseName;
    private String ReleaseID;
    private String Released_TS;
    private String TypeID;
    private String TypeName;
    private String SubjectID;
    private String ReleaseOrNot;
    private String Created_TS;
    private String Updated_TS;
	public Article(String articleID, String categoryID, String title, String cover, String content, String rootIN,
			String releaseName, String releaseID, String released_TS, String typeID, String typeName, String subjectID,
			String releaseOrNot, String created_TS, String updated_TS) {
		ArticleID = articleID;
		CategoryID = categoryID;
		Title = title;
		Cover = cover;
		Content = content;
		RootIN = rootIN;
		ReleaseName = releaseName;
		ReleaseID = releaseID;
		Released_TS = released_TS;
		TypeID = typeID;
		TypeName = typeName;
		SubjectID = subjectID;
		ReleaseOrNot = releaseOrNot;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
	}
	public String getArticleID() {
		return ArticleID;
	}
	public void setArticleID(String articleID) {
		ArticleID = articleID;
	}
	public String getCategoryID() {
		return CategoryID;
	}
	public void setCategoryID(String categoryID) {
		CategoryID = categoryID;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getCover() {
		return Cover;
	}
	public void setCover(String cover) {
		Cover = cover;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getRootIN() {
		return RootIN;
	}
	public void setRootIN(String rootIN) {
		RootIN = rootIN;
	}
	public String getReleaseName() {
		return ReleaseName;
	}
	public void setReleaseName(String releaseName) {
		ReleaseName = releaseName;
	}
	public String getReleaseID() {
		return ReleaseID;
	}
	public void setReleaseID(String releaseID) {
		ReleaseID = releaseID;
	}
	public String getReleased_TS() {
		return Released_TS;
	}
	public void setReleased_TS(String released_TS) {
		Released_TS = released_TS;
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
	public String getSubjectID() {
		return SubjectID;
	}
	public void setSubjectID(String subjectID) {
		SubjectID = subjectID;
	}
	public String getReleaseOrNot() {
		return ReleaseOrNot;
	}
	public void setReleaseOrNot(String releaseOrNot) {
		ReleaseOrNot = releaseOrNot;
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
