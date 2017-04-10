package com.galaxy.ggolf.dto;

import java.util.Collection;

import com.galaxy.ggolf.domain.Message;
import com.galaxy.ggolf.domain.NotifyList;

public class MessageData {
	
	private Message message;
	private Collection<NotifyList> notifyLists;
	
	public MessageData(Message message, Collection<NotifyList> notifyLists) {
		this.message = message;
		this.notifyLists = notifyLists;
	}
	
	
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public Collection<NotifyList> getNotifyLists() {
		return notifyLists;
	}
	public void setNotifyLists(Collection<NotifyList> notifyLists) {
		this.notifyLists = notifyLists;
	}
	

}
