package com.galaxy.ggolf.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.galaxy.ggolf.dao.mapper.TrackRowMapper;
import com.galaxy.ggolf.domain.Track;
import com.galaxy.ggolf.tools.ListUtil;

public class TrackDAO extends GenericDAO<Track> {

	public TrackDAO() {
		super(new TrackRowMapper());
		
	}
  //创建足迹
	public boolean create(Track tk){
		 Date now = new Date();
	     String dt = new String(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
		
	     String sql="INSERT INTO Track(UserID,"
				  +"Grade,"
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
		   
		  return super.sqlUpdate(sql, tk.getUserId(),tk.getGrade(),dt,
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
			tk=new Track(t.getUserId(),t.getTrackId(),t.getGrade(),t.getCreated_TS(),
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
}
