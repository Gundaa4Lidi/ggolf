package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.Comment;

public class CommentRowMapper implements RowMapper<Comment> {
	 public static final String COLUMN_CommentID = "CommentID";
	 public static final String COLUMN_Action = "Action";
	 public static final String COLUMN_RootType = "RootType";
	 public static final String COLUMN_RootID = "RootID";
	 public static final String COLUMN_UserName = "UserName";
	 public static final String COLUMN_UserHead = "UserHead";
	 public static final String COLUMN_UserID = "UserID";
	 public static final String COLUMN_ParentType = "ParentType";
	 public static final String COLUMN_ParentID = "ParentID";
	 public static final String COLUMN_ParentUserID = "ParentUserID";
	 public static final String COLUMN_Memo = "Memo";
	 public static final String COLUMN_ReplyID = "ReplyID";
	 public static final String COLUMN_ReplyName = "ReplyName";
	 public static final String COLUMN_Created_TS = "Created_TS";

	@Override
	public Comment mapRow(ResultSet res) throws SQLException {
		String CommentID = res.getString(COLUMN_CommentID);
		String Action = res.getString(COLUMN_Action);
		String RootType = res.getString(COLUMN_RootType);
		String RootID = res.getString(COLUMN_RootID);
		String UserName = res.getString(COLUMN_UserName);
		String UserHead = res.getString(COLUMN_UserHead);
		String UserID = res.getString(COLUMN_UserID);
		String ParentType = res.getString(COLUMN_ParentType);
		String ParentID = res.getString(COLUMN_ParentID);
		String ParentUserID = res.getString(COLUMN_ParentUserID);
		String Memo = res.getString(COLUMN_Memo);
		String ReplyID = res.getString(COLUMN_ReplyID);
		String ReplyName = res.getString(COLUMN_ReplyName);
		String Created_TS = res.getString(COLUMN_Created_TS);
		return new Comment(CommentID,Action,RootType,RootID,UserName,UserHead,UserID,ParentType,ParentID,ParentUserID,Memo,ReplyID,ReplyName,Created_TS);
	}

}
