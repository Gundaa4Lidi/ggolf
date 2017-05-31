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
import com.galaxy.ggolf.tools.ListUtil;

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
		ClubDetail clubDetail = this.clubDetailDAO.getClubDetailByClubID(cd.getClubID());
		if(clubDetail == null){
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
		String sqlString = "";
		if(cd.getClubName()!=null&&!cd.getClubName().equalsIgnoreCase("null")){
			sqlString += "ClubName='"+cd.getClubName()+"',";
		}
		if(cd.getMode()!=null&&!cd.getMode().equalsIgnoreCase("null")){
			sqlString += "Mode='"+cd.getMode()+"',";
		}
		if(cd.getTotalHole()!=null&&!cd.getTotalHole().equalsIgnoreCase("null")){
			sqlString += "TotalHole='"+cd.getTotalHole()+"',";
		}
		if(cd.getTotalStemNum()!=null&&!cd.getTotalStemNum().equalsIgnoreCase("null")){
			sqlString += "TotalStemNum='"+cd.getTotalStemNum()+"',";
		}
		if(cd.getPhoneNum()!=null&&!cd.getPhoneNum().equalsIgnoreCase("null")){
			sqlString += "PhoneNum='"+cd.getPhoneNum()+"',";
		}
		if(cd.getCreateTime()!=null&&!cd.getCreateTime().equalsIgnoreCase("null")){
			sqlString += "CreateTime='"+cd.getCreateTime()+"',";
		}
		if(cd.getStylist()!=null&&!cd.getStylist().equalsIgnoreCase("null")){
			sqlString += "Stylist='"+cd.getStylist()+"',";
		}
		if(cd.getSquare()!=null&&!cd.getSquare().equalsIgnoreCase("null")){
			sqlString += "Square='"+cd.getSquare()+"',";
		}
		if(cd.getLength()!=null&&!cd.getLength().equalsIgnoreCase("null")){
			sqlString += "Length='"+cd.getLength()+"',";
		}
		if(cd.getPuttingSeed()!=null&&!cd.getPuttingSeed().equalsIgnoreCase("null")){
			sqlString += "PuttingSeed='"+cd.getPuttingSeed()+"',";
		}
		if(cd.getFairwaySeed()!=null&&!cd.getFairwaySeed().equalsIgnoreCase("null")){
			sqlString += "FairwaySeed='"+cd.getFairwaySeed()+"',";
		}
		if(cd.getAddress()!=null&&!cd.getAddress().equalsIgnoreCase("null")){
			sqlString += "Address='"+cd.getAddress()+"',";
		}
		if(cd.getIntro()!=null&&!cd.getIntro().equalsIgnoreCase("null")){
			sqlString += "Intro='"+cd.getIntro()+"',";
		}
		if(cd.getPhotoList()!=null&&cd.getPhotoList().size() > 0){
			String photoList = new ListUtil().ListToString(cd.getPhotoList());
			sqlString += "PhotoList='"+photoList+"',";
		}
		if(cd.getMapImg()!=null&&cd.getMapImg().size() > 0){
			String mapImg = new ListUtil().ListToString(cd.getMapImg());
			sqlString += "MapImg='"+mapImg+"',";
		}
		if(cd.getFacility()!=null&&!cd.getFacility().equalsIgnoreCase("null")){
			sqlString += "Facility='"+cd.getFacility()+"',";
		}
		if(!clubDetailDAO.updateClubDetail(cd, sqlString)){
			throw new GalaxyLabException("Error in update ClubDetail");
		}
		ClubDetail clubDetail = clubDetailDAO.getClubDetailByClubID(cd.getClubID());
		if(clubDetail != null){
			this.cache.put(clubDetail.getClubID(),clubDetail);
		}
		
	}
	
	private void updateFairway(ClubFairway cf)throws GalaxyLabException {
		ClubFairway exist1 = clubFairwayDAO.getFairwayNameByClubID(cf.getFairwayName(), cf.getClubID());
		if(exist1 != null){
			throw new GalaxyLabException("ClubFairway exist");
		}
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
		if(clubDetail!=null){
			cache.put(clubDetail.getClubID(), clubDetail);
		}
		
	}
	
	private void createFairway(ClubFairway cf)throws GalaxyLabException {
		ClubFairway exist = clubFairwayDAO.getClubFairwayByUID(cf.getUID());
		if(exist != null){
			throw new GalaxyLabException("ClubFairway exist");
		}
		ClubFairway exist1 = clubFairwayDAO.getFairwayNameByClubID(cf.getFairwayName(), cf.getClubID());
		if(exist1 != null){
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
