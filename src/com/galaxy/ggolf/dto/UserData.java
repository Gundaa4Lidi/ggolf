package com.galaxy.ggolf.dto;

import java.util.Collection;

import com.galaxy.ggolf.domain.User;

public class UserData {
	private Integer count;
	private Collection<User> userList;
	public UserData(Integer count, Collection<User> userList) {
		this.count = count;
		this.userList = userList;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Collection<User> getUserList() {
		return userList;
	}
	public void setUserList(Collection<User> userList) {
		this.userList = userList;
	}
	
	
}
