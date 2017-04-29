package com.galaxy.ggolf.dto;

import java.util.Collection;

import com.galaxy.ggolf.domain.ClubOrder;

public class ClubOrderData {
	private int count;
	private Collection<ClubOrder> clubOrders;
	public ClubOrderData(int count, Collection<ClubOrder> clubOrders) {
		this.count = count;
		this.clubOrders = clubOrders;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Collection<ClubOrder> getClubOrders() {
		return clubOrders;
	}
	public void setClubOrders(Collection<ClubOrder> clubOrders) {
		this.clubOrders = clubOrders;
	}
	
	
}
