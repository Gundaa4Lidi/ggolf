package com.galaxy.ggolf.dto;

import java.util.Collection;

import com.galaxy.ggolf.domain.Club;

public class ClubData {
	
	private int count;
	private Collection<Club> data;
	
	public ClubData(int count, Collection<Club> data) {
		this.count = count;
		this.data = data;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Collection<Club> getData() {
		return data;
	}
	public void setData(Collection<Club> data) {
		this.data = data;
	}
	

	
	
}
