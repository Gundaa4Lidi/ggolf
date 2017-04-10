package com.galaxy.ggolf.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.dto.Location;
import com.galaxy.ggolf.jdbc.CommonConfig;

public class LocationSessionManager {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final ScheduledExecutorService tokenCleanUpService = Executors.newScheduledThreadPool(1);
	
	private final ConcurrentHashMap<String, Location> loginMap;
	
	private final ConcurrentHashMap<String, Long> expireMap;
	
	public LocationSessionManager(){
		this.loginMap = new ConcurrentHashMap<String,Location>();
		this.expireMap = new ConcurrentHashMap<String, Long>();
	}
	
	public void putAuth(String token, Location location){
		this.loginMap.put(token, location);
		refresh(token);
	}
	
	public boolean isAuth(String token){
		if(this.loginMap.get(token)!=null){
			refresh(token);
			return true;
		}
		return false;
	}
	
	public Location getLocation(String token){
		refresh(token);
		return this.loginMap.get(token);
	}

	public void refresh(String token){
		if(token!=null){
			long now = DateTime.now().getMillis();
			this.expireMap.put(token, now + CommonConfig.SESSION_LOCATION_EXPIRY);
		}
	}
	
	public void clean(String token){
		if(expireMap.get(token)!=null){
			expireMap.remove(token);
			loginMap.remove(token);
		}
	}
	
	public Collection<Location> getLocationUsers(){
		Collection<Location> Locations = new ArrayList<Location>();
		Map<String, String> pMap = new HashMap<String, String>();
		try {
			for(String key : expireMap.keySet()){
				if(expireMap.get(key) > DateTime.now().getMillis()){
					Location Location = loginMap.get(key);
					if(!pMap.containsKey(Location.getUserID())){
						pMap.put(Location.getUserID(), Location.getUserID());
						Locations.add(Location);
					}
				}
			}
		} catch (Exception e) {
			logger.error("error Exception",e);
		}
		return Locations;
	}
	
	public int getCount(){
		return getLocationUsers().size();
	}
	
	
	
	public void startCleanUpTask(){
		Runnable task = new TokenCleanUpRunner();
		logger.info("Start TokenCleanUpRunner()");
		tokenCleanUpService.scheduleWithFixedDelay(task, 0, 60, TimeUnit.SECONDS);
	}
	
	class TokenCleanUpRunner implements Runnable{
		@Override
		public void run() {
			try{
				for(String key : expireMap.keySet()){
					logger.info("位置信息--------{}",loginMap.get(key));
					if(expireMap.get(key) < DateTime.now().getMillis()){
						logger.debug("token {} is expired, removing from memory", key);
						expireMap.remove(key);
						loginMap.remove(key);
					}
				}
			}catch(Exception ex){
				logger.error("Exception", ex);
			}
		}
	}
}
