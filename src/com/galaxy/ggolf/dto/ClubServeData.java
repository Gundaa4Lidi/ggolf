package com.galaxy.ggolf.dto;

import java.util.Collection;

import com.galaxy.ggolf.domain.ClubServe;

public class ClubServeData {
	
	private int count;
	private Collection<ClubServe> clubServes;
	
	public ClubServeData(int count, Collection<ClubServe> clubServes) {
		this.count = count;
		this.clubServes = clubServes;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Collection<ClubServe> getClubServes() {
		return clubServes;
	}
	public void setClubServes(Collection<ClubServe> clubServes) {
		this.clubServes = clubServes;
	}
	
	

}
