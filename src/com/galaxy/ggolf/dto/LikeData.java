package com.galaxy.ggolf.dto;

import java.util.Collection;

import com.galaxy.ggolf.domain.User;

public class LikeData {
	private int count;
	private Collection<User> userlist;
	
	public LikeData(int count, Collection<User> userlist) {
		this.count = count;
		this.userlist = userlist;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Collection<User> getUserlist() {
		return userlist;
	}
	public void setUserlist(Collection<User> userlist) {
		this.userlist = userlist;
	}
	
	
	
	
}
