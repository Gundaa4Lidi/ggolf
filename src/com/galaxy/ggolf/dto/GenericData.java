package com.galaxy.ggolf.dto;

import java.util.Collection;

public class GenericData<T> {
	private int count;
	private int realCount;
	private Collection<T> data;
	public GenericData(int count, Collection<T> data) {
		this.count = count;
		this.data = data;
	}
	
	public GenericData(int count, int realCount, Collection<T> data) {
		this.count = count;
		this.realCount = realCount;
		this.data = data;
	}

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getRealCount() {
		return realCount;
	}
	public void setRealCount(int realCount) {
		this.realCount = realCount;
	}
	public Collection<T> getData() {
		return data;
	}
	public void setData(Collection<T> data) {
		this.data = data;
	}
	
	
}
