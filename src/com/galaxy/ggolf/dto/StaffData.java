package com.galaxy.ggolf.dto;

import java.util.Collection;

import com.galaxy.ggolf.domain.Staff;

public class StaffData {
	private int count;
	private Collection<Staff> data;
	
	public StaffData(int count, Collection<Staff> data) {
		this.count = count;
		this.data = data;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Collection<Staff> getData() {
		return data;
	}
	public void setData(Collection<Staff> data) {
		this.data = data;
	}
	
	
}
