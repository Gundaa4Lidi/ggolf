package com.galaxy.ggolf.dao;


import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.TimeRowMapper;
import com.galaxy.ggolf.domain.Time;
public class TimeDAO extends GenericDAO<Time> {
   
    public TimeDAO() {
	super(new TimeRowMapper());
	// TODO Auto-generated constructor stub
}
    public Collection<Time>getmin(String UserId){
   	String sql="SELECT MIN(s.Created_TS) as Screated_TS ,MIN(t.Created_TS) as Tcreated_TS  FROM score s  join track t on s.UserId=t.UserId  Where s.UGrade>60 AND t.UGrade>60 AND s.ScoresOne IS NOT NULL AND s.ScoresTwo IS NOT NULL AND s.UserId='"+UserId+"'";
              return super.executeQuery(sql);
       }
}
