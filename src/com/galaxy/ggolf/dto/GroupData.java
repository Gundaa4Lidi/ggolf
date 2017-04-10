package com.galaxy.ggolf.dto;

import java.util.Collection;

public class GroupData<T> {
	private String Date;
	private Collection<T> Group;
	public GroupData(String date, Collection<T> group) {
		Date = date;
		Group = group;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public Collection<T> getGroup() {
		return Group;
	}
	public void setGroup(Collection<T> group) {
		Group = group;
	}
	
	
}
