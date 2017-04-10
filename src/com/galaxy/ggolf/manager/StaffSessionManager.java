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
import com.galaxy.ggolf.domain.Staff;
import com.galaxy.ggolf.jdbc.CommonConfig;



public class StaffSessionManager {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final ScheduledExecutorService tokenCleanUpService = Executors.newScheduledThreadPool(1);
	
	private final ConcurrentHashMap<String, Staff> loginMap;
	
	private final ConcurrentHashMap<String, Long> expireMap;
	
	public StaffSessionManager(){
		this.loginMap = new ConcurrentHashMap<String, Staff>();
		this.expireMap = new ConcurrentHashMap<String, Long>();
	}
	
	public void putAuth(String token, Staff staff){
		this.loginMap.put(token, staff);
		refresh(token);
	}
	
	public boolean isAuth(String token){
		if(this.loginMap.get(token)!=null){
			refresh(token);
			return true;
		}
		return false;
	}
	
	public Staff getStaff(String token){
		refresh(token);
		return this.loginMap.get(token);
	}

	public void refresh(String token){
		if(token!=null){
			long now = DateTime.now().getMillis();
			this.expireMap.put(token, now + CommonConfig.SESSION_EXPIRY);
		}
	}
	
	public Collection<Staff> getOnlineStaff(){
		Collection<Staff> staffs = new ArrayList<Staff>();
		Map<String, String> pMap = new HashMap<String, String>();
		try {
			for(String key : expireMap.keySet()){
				if(expireMap.get(key) > DateTime.now().getMillis()){
					Staff staff = loginMap.get(key);
					if(!pMap.containsKey(staff.getStaffID())){
						pMap.put(staff.getStaffID(), staff.getStaffID());
						staffs.add(staff);
					}
				}
			}
		} catch (Exception e) {
			logger.error("error Exception",e);
		}
		return staffs;
	}
	
	public int getCount(){
		/*return this.onlineMap.size();*/
		return getOnlineStaff().size();
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
					logger.info("员工信息--------{}",loginMap.get(key));
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
