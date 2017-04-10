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

import com.galaxy.ggolf.dao.OnlineDAO;
import com.galaxy.ggolf.domain.User;
import com.galaxy.ggolf.jdbc.CommonConfig;



public class SessionManager {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final ScheduledExecutorService tokenCleanUpService = Executors.newScheduledThreadPool(1);
	
	private final ConcurrentHashMap<String, User> loginMap;
	
	private final ConcurrentHashMap<String, Long> expireMap;
	
	public SessionManager(){
		this.loginMap = new ConcurrentHashMap<String, User>();
		this.expireMap = new ConcurrentHashMap<String, Long>();
	}
	
	public void putAuth(String token, User user){
		this.loginMap.put(token, user);
		refresh(token);
	}
	
	public boolean isAuth(String token){
		if(this.loginMap.get(token)!=null){
			refresh(token);
			return true;
		}
		return false;
	}
	
	public User getUser(String token){
		refresh(token);
		return this.loginMap.get(token);
	}

	public void refresh(String token){
		if(token!=null){
			long now = DateTime.now().getMillis();
			this.expireMap.put(token, now + CommonConfig.SESSION_EXPIRY);
		}
	}
	
	
	public Collection<User> getOnlineUser(){
		Collection<User> users = new ArrayList<User>();
		Map<String, String> pMap = new HashMap<String, String>();
		try {
			for(String key : expireMap.keySet()){
				if(expireMap.get(key) > DateTime.now().getMillis()){
					User user = loginMap.get(key);
					if(!pMap.containsKey(user.getUserID())){
						pMap.put(user.getUserID(), user.getUserID());
						users.add(user);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception:-----",e);
		}
		
		return users;
	}
	
	public int getOnlineCount(){
		return getOnlineUser().size();
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
