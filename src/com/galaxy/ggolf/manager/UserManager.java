package com.galaxy.ggolf.manager;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.cache.GenericCache;
import com.galaxy.ggolf.dao.Common_configDAO;
import com.galaxy.ggolf.dao.UserDAO;
import com.galaxy.ggolf.dao.UserLogRecDAO;
import com.galaxy.ggolf.domain.Common_config;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.domain.User;
import com.galaxy.ggolf.domain.UserLogRec;
import com.galaxy.ggolf.tools.LocationUtil;
import com.spatial4j.core.shape.Rectangle;



public class UserManager {

	public GenericCache<String, User> userCache;
	public UserDAO userDAO;
	public UserLogRecDAO userLogRecDAO;
	public Common_configDAO configDAO;

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final String YI_GUAN_ZHU = "已关注";
	private static final String HU_XIANG_GUAN_ZHU = "互相关注";
	private static final String YI_BEI_GUAN_ZHU = "已被关注";
	private static final String QU_XIAO_GUAN_ZHU = "取消关注";
	private static final String BLACKLIST = "黑名单";

	public UserManager(UserDAO userDAO, UserLogRecDAO userLogRecDAO, Common_configDAO configDAO) {
		this.userCache = new GenericCache<String, User>();
		this.userDAO = userDAO;
		this.userLogRecDAO = userLogRecDAO;
		this.configDAO = configDAO;
	}
	
	//创建用户
	public void createUser(String phone, String password, String name)
			throws GalaxyLabException {
		String key = "HeadPhoto";
		Common_config config = configDAO.getCommon_cofig(key);
		User user = new User(phone, password, name, config.getVALUE());
		if (userDAO.createUser(user)) {
			User user1 = userDAO.getUserByPhone(phone);
			userCache.put(user1.getPhone(), user1);
		}
	}
	
	//跟据微信账号创建用户
	public void createUserByWechat(User user) throws GalaxyLabException{
		if(user.getHead_portrait()==null){
			String key = "HeadPhoto";
			Common_config config = configDAO.getCommon_cofig(key);
			user.setHead_portrait(config.getVALUE());
		}
		if(!userDAO.createUserByWechat(user)){
			throw new GalaxyLabException("Error in create User by Wecha");
		}
		User user1 = this.userDAO.getUserByWechat(user.getWechat());
		if(user1!=null){
			userCache.put(user1.getWechat(), user1);
		}
	}
	
	
	//修改位置
	public void updateLocation(String longitude,String latitude,String userID)throws Exception{
		userDAO.updateLocation(longitude, latitude, userID);
		User user = userDAO.getUserByUserID(userID);
		if(user != null){
			userCache.put(user.getUserID()+","+user.getName(), user);
		}
	}
	
	
	//获取附近的人
	public Collection<User> getNearByUser(Rectangle rec, String rows){
		String sqlString = "";
		if(rows != null){
			sqlString = "limit 0 , "+rows+"";
		}
		Collection<User> userList = this.userDAO.getNearByUser(rec,sqlString); 
		userList = getDistance(rec.getMinX(),rec.getMinY(),userList);
		return userList;
	}
	
	
	
	//登录记录
	public void loginRecord(String longitude,String latitude,String login_place,String userid)throws Exception{
		UserLogRec userLogRec = new UserLogRec(userid,login_place,null,longitude,latitude);
		userLogRecDAO.createUserLogRec(userLogRec);
	}
	
	//获取所有登录记录
	public Collection<UserLogRec> getAllUserLogRec()throws Exception{
		return userLogRecDAO.getAll();
	}
	
	//根据ID获取登录记录
	public Collection<UserLogRec> getUserLogRec(String userID)throws Exception{
		return userLogRecDAO.getRecordByUserID(userID);
	}
	
	//查询用户信息
	public User getUserByUserID(String userID) throws Exception{
		if (userCache.get(userID) != null) {
			logger.debug("cache hit");
			return userCache.get(userID);
		} else {
			logger.debug("cache missed, looking up from DB");
			User user = userDAO.getUserByUserID(userID);
			
			if (user != null) {
				this.userCache.put(user.getUserID()+","+user.getName(), user);
			}
			return user;
		}
	}
	
	//根据微信查询
	public User getUserByWechat(String wechat) throws Exception{
		if (userCache.get(wechat) != null) {
			logger.debug("cache hit");
			return userCache.get(wechat);
		} else {
			logger.debug("cache missed, looking up from DB");
			User user = userDAO.getUserByWechat(wechat);
			if(user != null){
				this.userCache.put(user.getWechat(), user);
			}
			return user;
		}
		
	}
	
	//根据电话查询
	public User getUserByPhone(String phone) throws Exception{
		if (userCache.get(phone) != null) {
			logger.debug("cache hit");
			return userCache.get(phone);
		} else {
			logger.debug("cache missed, looking up from DB");
			User user = userDAO.getUserByPhone(phone);
			if(user != null){
				this.userCache.put(user.getPhone(), user);
			}
			return user;
		}
		
	}
	
	//查询用户是否存在
	public boolean checkUser(String userID) throws Exception{
		if (userCache.get(userID) != null){ 
			return true;
		}else if(userDAO.getUserByUserID(userID)!=null){
			this.userCache.put(userID, userDAO.getUserByUserID(userID));
			return true;
		}else{
			return false;
		}
	}
	//绑定手机
	public void bindingPhone(String phone,String userID)throws Exception{
		if(!userDAO.updatePhone(phone, userID)){
			throw new GalaxyLabException("Error in binding phone");
		}
		User user = userDAO.getUserByUserID(userID);
		if (user != null) {
			this.userCache.put(user.getUserID()+","+user.getName(), user);
		}
	}
	
	//修改密码
	public boolean updatepassword(String userID,String password) throws Exception{
		if(userDAO.updatepassword(userID, password)){
			userCache.remove(userID);
			User user = userDAO.getUserByUserID(userID);
			userCache.put(userID, user);
			return true;
		}
		return false;
		
	}
	
	//修改用户
	public void updateUser(User user)throws Exception{
		if(user.getHead_portrait() == null||user.getHead_portrait() ==""){
			String key = "HeadPhoto";
			Common_config config = configDAO.getCommon_cofig(key);
			user.setHead_portrait(config.getVALUE());
		}
		if(!userDAO.updateUser(user)){
			throw new GalaxyLabException("Error in update user");
		}
		User user1 = userDAO.getUserByUserID(user.getUserID());
		if(user1 != null){
			userCache.put(user1.getUserID(), user1);
		}
		
	}
	
	public void saveHead(String head,String userID)throws Exception{
		if(!this.userDAO.updateHead(head, userID)){
			throw new GalaxyLabException("Error in update user head");
		}
		User user  = userDAO.getUserByUserID(userID);
		if(user != null){
			userCache.put(user.getUserID()+","+user.getName(), user);
		}
	}
	
	/*public String getCouponID() {
		Random r = new Random();
		long num = Math.abs(r.nextLong() % 1000000000000L);
		String s = String.valueOf(num);
		for (int i = 0; i < 12 - s.length(); i++) {
			s = "0" + s;
		}
		logger.debug(s);
		return s;
	}*/
	
	//分页获取全部用户信息
	public Collection<User> getUser(String pageNum,String size,String keyword,String lon,String lat) throws Exception{
		String sqlString = "";
		if(keyword!=null && !keyword.equals("")){
			sqlString = "and (UserID like '%"
					+ keyword
					+ "%' or Phone like '%"
					+ keyword 
					+ "%' or Name like '%"
					+ keyword 
					+ "%' or Age like '%"
					+ keyword 
					+ "%' or Sex like '%"
					+ keyword
					+ "%' or date_format(`Created_TS`,'%Y-%m-%d') like '%"
					+ keyword +"%') ";
		}
		Collection<User> userList = userDAO.getUsers(pageNum,size,sqlString);
		if(lon != null && lat != null){
			double lon1 = Double.parseDouble(lon);
			double lat1 = Double.parseDouble(lat);
			userList = getDistance(lon1, lat1, userList);
		}
		return userList;
	}
	
	public int getSearchCount(String keyword)throws Exception{
		String sqlString = "";
		if(keyword!=null && !keyword.equals("")){
			sqlString = "and (UserID like '%"
					+ keyword
					+ "%' or Phone like '%"
					+ keyword 
					+ "%' or Name like '%"
					+ keyword 
					+ "%' or Age like '%"
					+ keyword 
					+ "%' or Sex like '%"
					+ keyword
					+ "%' or date_format(`Created_TS`,'%Y-%m-%d') like '%"
					+ keyword +"%') ";
		}
		return this.userDAO.getSearchCount(sqlString);
	}
	
	public int getCount() throws Exception{
		return userDAO.getCount();
	}
	//
	public Collection<User> getAll(){
		return userDAO.getAll();
	}
	
	
	public boolean removeUser(String phone)throws Exception{
		boolean result = false;
		User user = userDAO.getUserByPhone(phone);
		if(userDAO.deleteUser(user.getUserID())){
			this.userCache.remove(user.getUserID());
			result = true;
		}
		return result;
	}
	
	//获取范围内的人距离
	public Collection<User> getDistance(double lon1, double lat1, Collection<User> userList){
		List<User> users = (List<User>) userList;
		if(users.size()>0){
			for(User user : users){
				if(user.getLongitude()!=null && user.getLatitude()!=null){
					double lon2 = Double.parseDouble(user.getLongitude());//用户的经度
					double lat2 = Double.parseDouble(user.getLatitude());//用户的纬度
					double distance = LocationUtil.GetDistance(lon1, lat1, lon2, lat2);//获取距离
					user.setDistance(String.valueOf(distance));
				}
				
			}
			long bt = System.nanoTime();//开始排序
			Collections.sort(users,new Comparator<User>() {

				@Override
				public int compare(User o1, User o2) {
					if(o1.getDistance() != null && o2.getDistance() != null){
						double distance1 = Double.parseDouble(o1.getDistance());
						double distance2 = Double.parseDouble(o2.getDistance());
						if(distance1 < distance2){
							return -1;
						}else if(distance1 > distance2){
							return 1;
						}else{
							return o1.getName().compareTo(o2.getName());
						}
					}else{
						return 0;
					}
				}
			});
			long et = System.nanoTime();//结束排序
			logger.info("耗时----{}to{}",bt,et);
			return users;
			
		}
		return null;
	}
	
	
}