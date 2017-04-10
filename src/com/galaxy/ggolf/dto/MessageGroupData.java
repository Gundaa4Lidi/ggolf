package com.galaxy.ggolf.dto;

import java.util.Collection;

import com.galaxy.ggolf.domain.Message;

public class MessageGroupData {
	private int count;
	private Collection<GroupData<Message>> Data;
	public MessageGroupData(int count, Collection<GroupData<Message>> data) {
		this.count = count;
		Data = data;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Collection<GroupData<Message>> getData() {
		return Data;
	}
	public void setData(Collection<GroupData<Message>> data) {
		Data = data;
	}
	
	
	
	
}
