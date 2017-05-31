package com.galaxy.ggolf.domain;

import com.galaxy.ggolf.dao.TimeDAO;

public class Time {
   private String Screated_TS;
   private String Tcreated_TS;
   
   public Time(){
       
   }

public String getScreated_TS() {
    return Screated_TS;
}

public void setScreated_TS(String screated_TS) {
    Screated_TS = screated_TS;
}

public String getTcreated_TS() {
    return Tcreated_TS;
}

public void setTcreated_TS(String tcreated_TS) {
    Tcreated_TS = tcreated_TS;
}

public Time(String screated_TS, String tcreated_TS) {
    super();
    Screated_TS = screated_TS;
    Tcreated_TS = tcreated_TS;
}
}

