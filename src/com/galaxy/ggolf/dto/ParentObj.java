package com.galaxy.ggolf.dto;

public class ParentObj<T> {
	private T t;

	public ParentObj(T t) {
		this.t = t;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
	
	
}
