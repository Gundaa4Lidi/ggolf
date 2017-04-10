package com.galaxy.ggolf.manager;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.cache.GenericCache;
import com.galaxy.ggolf.dao.ClubDAO;
import com.galaxy.ggolf.dao.ClubDetailDAO;
import com.galaxy.ggolf.dao.ClubFairwayDAO;
import com.galaxy.ggolf.domain.Club;
import com.galaxy.ggolf.domain.ClubDetail;
import com.galaxy.ggolf.domain.ClubFairway;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.domain.Staff;

public class ClubDetailManager {

	public GenericCache<String, ClubDetail> cache;
	public ClubDAO clubDAO; 
	public ClubDetailDAO clubDetailDAO;
	public ClubFairwayDAO clubFairwayDAO;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	public ClubDetailManager(ClubDAO clubDAO, ClubDetailDAO clubDetailDAO,ClubFairwayDAO clubFairwayDAO) {
		this.cache = new GenericCache<String,ClubDetail>();
		this.clubDAO = clubDAO;
		this.clubDetailDAO = clubDetailDAO;
		this.clubFairwayDAO = clubFairwayDAO;
	}
	
	/*public Collection<ClubDetail> getClubDetailByClubID(String clubID)throws Exception{
		return this.clubDetailDAO.getDetailByClubID(clubID);
	}*/
	
	public ClubDetail getClubDetailByClubID(String clubID)throws Exception{
		if (cache.get(clubID) != null) {
			logger.debug("cache hit");
			return cache.get(clubID);
		} else {
			logger.debug("cache missed, looking up from DB");
			ClubDetail clubDetail = clubDetailDAO.getClubDetailByClubID(clubID);
			if (clubDetail != null) {
				this.cache.put(clubDetail.getClubID(), clubDetail);
			}
			return clubDetail;
		}
	}
	
	public Collection<ClubFairway> getClubFairwayByClubID(String clubID)throws Exception{
		Collection<ClubFairway> clubFairway = clubFairwayDAO.getClubFairway(clubID);
		return clubFairway;
	}
	
	public void saveClubDetail(ClubDetail cd)throws GalaxyLabException{
		if(cd.getUID() == null){
			createDetail(cd);
		}else{
			updateDetail(cd);
		}
	}
	
	public void saveClubFairway(ClubFairway cf)throws GalaxyLabException{
		if(cf.getUID() == null){
			createFairway(cf);
		}else{
			updateFairway(cf);
		}
	}

	private void updateDetail(ClubDetail cd)throws GalaxyLabException {
		if(!clubDetailDAO.update(cd)){
			throw new GalaxyLabException("Error in update ClubDetail");
		}
		
		cache.put(cd.getClubID(), cd);
		
	}
	
	private void updateFairway(ClubFairway cf)throws GalaxyLabException {
		if(!clubFairwayDAO.update(cf)){
			throw new GalaxyLabException("Error in update ClubFairway");
		}
	}

	private void createDetail(ClubDetail cd)throws GalaxyLabException {
		ClubDetail exist = getClubDetail(cd);
		if(exist != null){
			throw new GalaxyLabException("ClubDetail exist");
		}
		if(!clubDetailDAO.create(cd)){
			throw new GalaxyLabException("Error in create clubDetail");
		}
		ClubDetail clubDetail = clubDetailDAO.getClubDetailByClubID(cd.getClubID());
		cache.put(clubDetail.getClubID(), clubDetail);
		
	}
	
	private void createFairway(ClubFairway cf)throws GalaxyLabException {
		Collection<ClubFairway> exist = clubFairwayDAO.getClubFairway(cf.getClubID());
		if(exist != null){
			throw new GalaxyLabException("ClubFairway exist");
		}
		if(!clubFairwayDAO.create(cf)){
			throw new GalaxyLabException("Error in create clubFairway");
		}
	}
	
	private ClubDetail getClubDetail(ClubDetail cd)throws GalaxyLabException{
		if(cache.get(cd.getClubID())!=null){
			logger.debug("cache hit");
			return cache.get(cd.getClubID());
		}else{
			logger.debug("cache missed,looking up from DB");
			ClubDetail clubDetail = clubDetailDAO.getClubDetailByClubID(cd.getClubID());
			if(clubDetail != null){
				this.cache.put(clubDetail.getClubID(),clubDetail);
			}
			return clubDetail;
		}
	}
	
	public void deleteDetail(String clubID){
		if(clubDetailDAO.detele(clubID)){
			cache.remove(clubID);
		}
	}
	
	public void deleteFairway(String uid){
		clubFairwayDAO.delete(uid);
	}
}
