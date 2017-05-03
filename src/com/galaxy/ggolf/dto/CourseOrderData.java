package com.galaxy.ggolf.dto;

import java.util.Collection;

import com.galaxy.ggolf.domain.CourseOrder;

public class CourseOrderData {
	private int count;
	private Collection<CourseOrder> courseOrders;
	public CourseOrderData(int count, Collection<CourseOrder> courseOrders) {
		this.count = count;
		this.courseOrders = courseOrders;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Collection<CourseOrder> getCourseOrders() {
		return courseOrders;
	}
	public void setCourseOrders(Collection<CourseOrder> courseOrders) {
		this.courseOrders = courseOrders;
	}
	
	
}
