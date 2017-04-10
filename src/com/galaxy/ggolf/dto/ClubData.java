package com.galaxy.ggolf.dto;

import java.util.Collection;

import com.galaxy.ggolf.domain.Club;

public class ClubData {
	
	private Collection<Club> data;
	
	private int pageCount;

	public ClubData(Collection<Club> data, int pageCount) {
		super();
		this.data = data;
		this.pageCount = pageCount;
	}

	public Collection<Club> getData() {
		return data;
	}

	public void setData(Collection<Club> data) {
		this.data = data;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	
}
