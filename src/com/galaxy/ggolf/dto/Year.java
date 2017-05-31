package com.galaxy.ggolf.dto;

import java.util.Collection;

public class Year<T> {
       private String Year;
       private Collection<T> month;
       
       
    
    public Year(String year, Collection<T> month) {
	super();
	Year = year;
	this.month = month;
    }
    public String getYear() {
        return Year;
    }
    public void setYear(String year) {
        Year = year;
    }
    public Collection<T> getMonth() {
        return month;
    }
    public void setMonth(Collection<T> month) {
        this.month = month;
    }
    
}
