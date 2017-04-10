package com.galaxy.ggolf.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.dao.OnlineDAO;
import com.galaxy.ggolf.domain.GalaxyLabException;

public class OnlineManager {
	
	private OnlineDAO onlineDAO;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public OnlineManager(OnlineDAO onlineDAO){
		this.onlineDAO = onlineDAO;
	}
	
	
	public void Online(String UserID)throws Exception{
		if(onlineDAO.getByUserID(UserID)!=null){
			logger.info("{}------已上线",UserID);
			onlineDAO.online(UserID);
		}else{
			logger.info("{}------已创建上线",UserID);
			onlineDAO.create(UserID);
		}
	}
	
	public void Offline(String UserID)throws Exception{
		if(!onlineDAO.offline(UserID)){
			throw new GalaxyLabException("Error in offline");
		}
		logger.info("{}------已离线",UserID);
	}
	
	public void addLocation(String UserID,String location)throws Exception{
		if(!onlineDAO.addLocation(UserID,location)){
			throw new GalaxyLabException("Error in OpenGPS");
		}
		logger.info("{}------已开启GPS",UserID);
	}
	
	public void cleanLocation(String UserID)throws Exception{
		if(!onlineDAO.cleanLocation(UserID)){
			throw new GalaxyLabException("Error in CloseGPS");
		}
		logger.info("{}------已关闭GPS",UserID);
	}
	
}
