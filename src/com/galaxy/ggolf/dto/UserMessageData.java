package com.galaxy.ggolf.dto;

import java.util.Collection;

import com.galaxy.ggolf.domain.Message;

public class UserMessageData {
	private Collection<Message> message;
	private Integer count;
	
	public UserMessageData(Collection<Message> message, Integer count) {
		this.message = message;
		this.count = count;
	}
	
	public Collection<Message> getMessage() {
		return message;
	}
	public void setMessage(Collection<Message> message) {
		this.message = message;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}
