package com.galaxy.ggolf.dto;

import java.util.Collection;

import com.galaxy.ggolf.domain.CoachCourse;

public class CoachCourseData {
	private int count;
	private Collection<CoachCourse> courses;
	public CoachCourseData(int count, Collection<CoachCourse> courses) {
		this.count = count;
		this.courses = courses;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Collection<CoachCourse> getCourses() {
		return courses;
	}
	public void setCourses(Collection<CoachCourse> courses) {
		this.courses = courses;
	}
	
	
}
