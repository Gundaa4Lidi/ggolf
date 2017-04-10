package com.galaxy.ggolf.dto;

import java.util.Collection;

import com.galaxy.ggolf.domain.Comment;

public class CommentData {

	private int count;
	private int commentCount;
	private Collection<Comment> comment;
	
	
	public CommentData(int count, Collection<Comment> comment) {
		this.count = count;
		this.comment = comment;
	}
	public CommentData(int count, int commentCount, Collection<Comment> comment) {
		this.count = count;
		this.commentCount = commentCount;
		this.comment = comment;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public Collection<Comment> getComment() {
		return comment;
	}
	public void setComment(Collection<Comment> comment) {
		this.comment = comment;
	}

	
	
	
}
