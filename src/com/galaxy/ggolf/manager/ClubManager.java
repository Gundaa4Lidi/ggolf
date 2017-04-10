package com.galaxy.ggolf.manager;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.cache.GenericCache;
import com.galaxy.ggolf.dao.ClubDAO;
import com.galaxy.ggolf.domain.Club;
import com.galaxy.ggolf.domain.GalaxyLabException;

public class ClubManager {

	public GenericCache<String, Club> clubCache;
	public ClubDAO clubDAO;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public ClubManager(ClubDAO clubDAO) {
		this.clubCache = new GenericCache<String, Club>();
		this.clubDAO = clubDAO;
	}
	
	public void saveClub(Club club)throws GalaxyLabException{
		if(club.getClubID()==null){
			createClub(club);
		}else{
			updateClub(club);
		}
	}
	
	public Collection<Club> getAll()throws Exception{
		Collection<Club> clubs = clubDAO.getAll();
		return clubs;
	}
	
	public int getCount()throws Exception{
		return clubDAO.getCount();
	}
	
	public Collection<Club> getSearchClub(String keyword, String rows,String pageNum)throws Exception{
		return clubDAO.getSearchClub(keyword, rows, pageNum);
	}
	
	public int getSearchCount(String keyword){
		return clubDAO.getSearchCount(keyword);
	}
	
	
	private void createClub(Club club)throws GalaxyLabException{
		String clubID = clubDAO.getClubID()+"";
		club.setClubID(clubID);
		Club existngClub = getClub(club.getClubID());
		if(existngClub != null){
			throw new GalaxyLabException("Club exist");
		}
		if(!clubDAO.create(club)){
			throw new GalaxyLabException("Error in create club");
		}
		Club club1 = clubDAO.getClubByClubID(club.getClubID());
		if(club!=null){
			clubCache.put(club1.getClubID(), club1);
		}
	}
	
	private void updateClub(Club club)throws GalaxyLabException{
		if(!clubDAO.updateClub(club)){
			throw new GalaxyLabException("Error in update club");
		}
		clubCache.put(club.getClubID(), club);
	}
	
	public Club getClub(String clubID){
		if(clubCache.get(clubID)!=null){
			logger.debug("cache hit");
			return clubCache.get(clubID);
		}else{
			logger.debug("cache missed,looking up from DB");
			Club club = clubDAO.getClubByClubID(clubID);
			if(club != null){
				this.clubCache.put(club.getClubID(),club);
			}
			return club;
		}
	}
	
	public void deleteClub(String clubID){
		if(clubDAO.deleteClub(clubID)){
			clubCache.remove(clubID);
		}
	}

}
