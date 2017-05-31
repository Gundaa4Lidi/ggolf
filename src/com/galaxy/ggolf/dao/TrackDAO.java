package com.galaxy.ggolf.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.galaxy.ggolf.dao.mapper.TrackRowMapper;
import com.galaxy.ggolf.domain.Score;
import com.galaxy.ggolf.domain.Track;
import com.galaxy.ggolf.tools.ListUtil;

public class TrackDAO extends GenericDAO<Track> {

	public TrackDAO() {
		super(new TrackRowMapper());
		
	}
	
 	//查看足迹详情
	public Collection<Track> getTrackDetail(String UserId,String TrackId){
	    String  sql="SELECT * FROM Track WHERE UserId='"+UserId+" 'AND TrackId='"+TrackId+"'";
	     return super.executeQuery(sql);
	}
	
        //创建足迹
	public boolean create(Track tk){
		 Date now = new Date();
	     String dt = new String(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
		
	     String sql="INSERT INTO Track(UserID,"
				  +"UGrade,"
				  +"Created_TS,"
				  +"Content,"
				  +"PhotoList,"
				  +"ClubName,"
				  +"PlayerName,"
				  +"SiteOnePAR,"
				  +"SiteTwoPAR,"
				  +"ScoresOne,"
				  +"ScoresTwo,"
				  +"PuttersOne,"
				  +"PuttersTwo)"
				  +"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		    String PhotoList="";
		    if(tk.getPhotoList()!=null&&tk.getPhotoList().size()>0){
		    	PhotoList=new ListUtil().ListToString(tk.getPhotoList());
		    }
		   
		  return super.sqlUpdate(sql, tk.getUserId(),tk.getUGrade(),dt,
				  tk.getContent(), PhotoList,tk.getClubName(),tk.getPlayerName(),tk.getSiteOnePAR(),tk.getSiteTwoPAR(),tk.getScoresOne(),tk.getScoresTwo(),tk.getPuttersOne(),tk.getPuttersTwo());
	}
	
	//获取足迹列表
	public Collection<Track> getTrackList(String UserId){
		String sql="SELECT * FROM Track WHERE UserId='"+UserId+"'";
		Collection<Track> tracks=super.executeQuery(sql);
		Collection<Track> trackList=new ArrayList<>();
		Track tk=new Track();
		if(tracks.size()>0){
		for(Track t : tracks){
			tk=new Track(t.getUserId(),t.getTrackId(),t.getUGrade(),t.getCreated_TS(),
					t.getPhotoList(),t.getContent(),t.getClubName(),t.getPlayerName(),t.getSiteOnePAR(),t.getSiteTwoPAR(),t.getScoresOne(),t.getScoresTwo(),t.getPuttersOne(),t.getPuttersTwo());
            trackList.add(tk);
		}
	}
		return trackList;
	}
	
	//删除足迹
	public boolean deleteTrack(String UserId,String TrackId){
		String sql="DELETE FROM Track WHERE UserId='"+UserId+"' AND TrackId='"+TrackId+"'";
		return super.executeUpdate(sql);
	}
	 //查最高杆数
	public Collection<Track> getBest(String UserId,String Time){
		String sql="SElECT * FROM Track WHERE UGrade=(SELECT MIN(UGrade) FROM Track WHERE UserId='"+UserId+"' AND UGrade>60 "+Time+" ) AND UserId='"+UserId+"'"+Time+"";
	      
		return super.executeQuery(sql);
	}
	//获取足迹创建的最早时间
	  public Collection<Track> getTrackMinTime(String UserId){
	    	String sql="select * from Track  where Created_TS =(SELECT min(Created_TS) FROM Track  where UserId='"+UserId+"' AND UGrade>60) AND UserId='"+UserId+"'";
	    	return super.executeQuery(sql);
	    }
	  //按时间段查找足迹记录
	    public Collection<Track> getTrackTime(String UserId,String Time){
	    	String sql="SELECT * FROM score WHERE UGrade=(SELECT MIN(UGrade) FROM score WHERE UserId='"+UserId+"' AND  UGrade>60 AND Created_TS BETWEEN '"+Time+"') and  UserId='"+UserId+"'";    	
	    	return super.executeQuery(sql);
	    }
	  
}
