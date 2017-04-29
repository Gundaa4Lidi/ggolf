package com.galaxy.ggolf.domain;


import com.galaxy.ggolf.dto.CommentData;

public class Comment {
    private String CommentID;//评论编号
    private String Action;//评论或回复的标识(comment,reply)
    private String RootType;//评论的根对象类型
    private String RootID;//评论的根对象编号
    private String UserName;//评论人的名称
    private String UserHead;//评论人的头像
    private String UserID;//评论人编号
    private String ParentType;//评论的父项类型(第一级是根对象,第二级是用户,回复action为comment的评论生成二级,例如:第一级动态,第二级用户)
    private String ParentID;//评论的父项编号(第一级是根对象编号,第二级是用户的评论编号)
    private String ParentUserID;//评论对象的用户编号(动态,足迹或回复时才会出现)
    private String Memo;//评论内容
    private String ReplyID;//回复的用户编号(当Action为reply才会出现)
    private String ReplyName;//回复的用户名称(当Action为reply才会出现)
    private String Created_TS;//评论创建日期
    private Object ParentObj;
	private CommentData replyData;
	private int likeCount;
    
	public Comment() {
		// TODO Auto-generated constructor stub
	}
	
	public Comment(String commentID, String action, String rootType, String rootID, String userName, String userHead,
			String userID, String parentType, String parentID, String parentUserID, String memo, String replyID,
			String replyName, String created_TS) {
		CommentID = commentID;
		Action = action;
		RootType = rootType;
		RootID = rootID;
		UserName = userName;
		UserHead = userHead;
		UserID = userID;
		ParentType = parentType;
		ParentID = parentID;
		ParentUserID = parentUserID;
		Memo = memo;
		ReplyID = replyID;
		ReplyName = replyName;
		Created_TS = created_TS;
	}
	
	public CommentData getReplyData() {
		return replyData;
	}
	public void setReplyData(CommentData replyData) {
		this.replyData = replyData;
	}
	public String getCommentID() {
		return CommentID;
	}
	public void setCommentID(String commentID) {
		CommentID = commentID;
	}
	public String getAction() {
		return Action;
	}
	public void setAction(String action) {
		Action = action;
	}
	public String getRootType() {
		return RootType;
	}
	public void setRootType(String rootType) {
		RootType = rootType;
	}
	public String getRootID() {
		return RootID;
	}
	public void setRootID(String rootID) {
		RootID = rootID;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getUserHead() {
		return UserHead;
	}
	public void setUserHead(String userHead) {
		UserHead = userHead;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getParentType() {
		return ParentType;
	}
	public void setParentType(String parentType) {
		ParentType = parentType;
	}
	public String getParentID() {
		return ParentID;
	}
	public void setParentID(String parentID) {
		ParentID = parentID;
	}
	public String getParentUserID() {
		return ParentUserID;
	}
	public void setParentUserID(String parentUserID) {
		ParentUserID = parentUserID;
	}
	public String getMemo() {
		return Memo;
	}
	public void setMemo(String memo) {
		Memo = memo;
	}
	public String getReplyID() {
		return ReplyID;
	}
	public void setReplyID(String replyID) {
		ReplyID = replyID;
	}
	public String getReplyName() {
		return ReplyName;
	}
	public void setReplyName(String replyName) {
		ReplyName = replyName;
	}
	public String getCreated_TS() {
		return Created_TS;
	}
	public void setCreated_TS(String created_TS) {
		Created_TS = created_TS;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public Object getParentObj() {
		return ParentObj;
	}

	public void setParentObj(Object parentObj) {
		ParentObj = parentObj;
	}
	
    
}
