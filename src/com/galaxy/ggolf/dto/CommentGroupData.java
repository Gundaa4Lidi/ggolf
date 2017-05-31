package com.galaxy.ggolf.dto;

import java.util.Collection;

import com.galaxy.ggolf.domain.Comment;

public class CommentGroupData {
	private int count;
	private Collection<GroupData<Comment>> Data;
	public CommentGroupData(int count, Collection<GroupData<Comment>> data) {
		this.count = count;
		Data = data;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Collection<GroupData<Comment>> getData() {
		return Data;
	}
	public void setData(Collection<GroupData<Comment>> data) {
		Data = data;
	}
	
}
