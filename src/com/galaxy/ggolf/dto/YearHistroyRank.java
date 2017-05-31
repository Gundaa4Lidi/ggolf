package com.galaxy.ggolf.dto;

import java.util.Collection;

import com.galaxy.ggolf.domain.HistoryRank;


public class YearHistroyRank {
    private Collection<Year<HistoryRank>> monthRank;

    
    
    public YearHistroyRank(Collection<Year<HistoryRank>> monthRank) {
	super();
	this.monthRank = monthRank;
    }

    public Collection<Year<HistoryRank>> getMonthRank() {
        return monthRank;
    }

    public void setMonthRank(Collection<Year<HistoryRank>> monthRank) {
        this.monthRank = monthRank;
    }
    
    
}
