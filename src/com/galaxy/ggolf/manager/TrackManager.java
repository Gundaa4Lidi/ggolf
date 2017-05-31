package com.galaxy.ggolf.manager;

import java.util.Collection;

import com.galaxy.ggolf.dao.TrackDAO;
import com.galaxy.ggolf.domain.Track;

public class TrackManager {

	private TrackDAO trackDAO;

	public TrackManager(TrackDAO footPrint) {
		super();
		this.trackDAO = footPrint;
	}
	 
	public boolean createTrack(Track tk){
		
		if(tk.getUserId()!=null&& !tk.getUserId().equals("")){
			return trackDAO.create(tk);
		}
		
		    return false;
	}
	
	public Collection<Track> getTrackList(String UserId){
		return trackDAO.getTrackList(UserId);
	}
	
	
	public boolean deleteTrack(String UserId,String TrackId){
		return trackDAO.deleteTrack(UserId, TrackId);
	}
	
	public Collection<Track>getTrackDetail(String UserId,String TrackId){
	    return trackDAO.getTrackDetail(UserId, TrackId);
	}
}
