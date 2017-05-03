package com.galaxy.ggolf.dto;

import java.util.Collection;

import com.galaxy.ggolf.domain.Coach;

public class CoachData {
	private int count;
	private Collection<Coach> coachs;
	public CoachData(int count, Collection<Coach> coachs) {
		this.count = count;
		this.coachs = coachs;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Collection<Coach> getCoachs() {
		return coachs;
	}
	public void setCoachs(Collection<Coach> coachs) {
		this.coachs = coachs;
	}
	
	
}
