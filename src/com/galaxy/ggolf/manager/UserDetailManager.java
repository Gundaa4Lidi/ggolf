package com.galaxy.ggolf.manager;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.cache.GenericCache;
import com.galaxy.ggolf.dao.UserDAO;
import com.galaxy.ggolf.dao.UserDetailDAO;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.domain.User;
import com.galaxy.ggolf.domain.UserDetail;
import com.galaxy.ggolf.tools.ListUtil;

public class UserDetailManager {
	
	private UserDAO userDAO;
	private UserDetailDAO userDetailDAO;
	private GenericCache<String, UserDetail> userDetailCache;
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public UserDetailManager(UserDAO userDAO, UserDetailDAO userDetailDAO){
		this.userDetailCache = new GenericCache<String, UserDetail>();
		this.userDAO = userDAO;
		this.userDetailDAO = userDetailDAO;
	}
	
	/**
	 * 创建用户详情
	 * @param user
	 * @throws GalaxyLabException
	 */
	public void createUserDetail(UserDetail user)throws GalaxyLabException {
		if(!userDetailDAO.createUserDL(user)){
			throw new GalaxyLabException("Error in create UserDetail");
		}
		/*User user1 = new User(user.getUserID(), user.getPhoneNum(), user.getUserName(), user.getSex(), user.getHeadPhoto());
		if(userDAO.updateUser(user1)){
			throw new GalaxyLabException("Error in update User");
		}*/
		UserDetail userDetail = userDetailDAO.getUserDetailByUserID(user.getUserID());
		if(userDetail != null){
			userDetailCache.put(user.getUserID(), userDetail);
		}
	}
	/**
	 * 保存全部用户详细信息
	 * @param user
	 * @throws GalaxyLabException
	 */
	public void updateAllUserDetail(UserDetail user)throws GalaxyLabException{
		if(!userDetailDAO.updateAllUserDetail(user)){
			throw new GalaxyLabException("Errow in update UserDetail");
		}
//		User user1 = new User(user.getUserID(), user.getPhoneNum(), user.getUserName(), user.getSex(), user.getHeadPhoto());
//		if(userDAO.updateUser(user1)){
//			throw new GalaxyLabException("Error in update User");
//		}
		UserDetail userDetail = userDetailDAO.getUserDetailByUserID(user.getUserID());
		if(userDetail != null){
			userDetailCache.put(user.getUserID(), userDetail);
		}
	}
	
	/**
	 * 根据用户编号获取详情
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	public UserDetail getUserDetailByUserID(String userID) throws Exception{
		if(userDetailCache.get(userID) !=null){
			logger.debug("cache hit");
			return userDetailCache.get(userID);
		}else{
			UserDetail userDetail = userDetailDAO.getUserDetailByUserID(userID);
			if(userDetail != null){
				userDetailCache.put(userID, userDetail);
			}
			return userDetail;
		}
	}
	
	//查询用户详细是否存在
	public boolean checkUserDetail(String userID) {
		if (userDetailCache.get(userID) != null){ 
			return true;
		}else if(userDetailDAO.getUserDetailByUserID(userID)!=null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 修改手机
	 * @param phone
	 * @param userID
	 * @throws GalaxyLabException
	 */
	public void updateUserDetailPhone(String phone,String userID)throws GalaxyLabException{
		if(!this.userDetailDAO.updateUserDetailPhone(phone, userID)){
			throw new GalaxyLabException("Error in update userDetail phone");
		}
	}
	/**
	 * 创建图册
	 * @param photo
	 * @param userID
	 * @throws GalaxyLabException
	 */
	public void createPhoto(Collection<String> photo,String userID)throws GalaxyLabException{
		String imgs = "";
		for(String str : photo){
			imgs = imgs +";"+ str;
		}
		if(imgs.indexOf(";")!=-1){
			imgs = imgs.substring(1,imgs.length());
		}
		if(!userDetailDAO.createPhotoList(imgs,userID)){
			throw new GalaxyLabException("Error in create photo list");
		}
		UserDetail userDetail = userDetailDAO.getUserDetailByUserID(userID);
		if(userDetail != null){
			userDetailCache.put(userDetail.getUserID(), userDetail);
		}
	}
	/**
	 * 修改图册
	 * @param photo
	 * @param userID
	 * @throws Exception
	 */
	public void updatePhotoList(Collection<String> photo,String userID)throws Exception{
		String imgs = "";
		for(String str : photo){
			imgs = imgs +";"+ str;
		}
		if(imgs.indexOf(";")!=-1){
			imgs = imgs.substring(1,imgs.length());
		}
		if(!userDetailDAO.updateAllPhotoList(imgs, userID)){
			throw new GalaxyLabException("Error in update photo list");
		}
		UserDetail userDetail = userDetailDAO.getUserDetailByUserID(userID);
		if(userDetail != null){
			userDetailCache.put(userID, userDetail);
		}
	}
	
	/**
	 * 保存全部用户详细信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
//	public boolean updateAllUserDetail(UserDetail user)throws Exception{
//		return userDetailDAO.updateAllUserDetail(user);
//	}
	
	/**
	 * 保存单个用户详情
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public void updateUserDetail(UserDetail user)throws Exception{
		String sqlString = "";
		if(user.getHeadPhoto()!=null&&!user.getHeadPhoto().equalsIgnoreCase("null")){
			
			sqlString = sqlString + "HeadPhoto='"+user.getHeadPhoto()+"',";
		}
		if(user.getUserName()!=null&&!user.getUserName().equalsIgnoreCase("null")){
			
			sqlString = sqlString + "UserName='"+user.getUserName()+"',";
		}
		if(user.getSex()!=null&&!user.getSex().equalsIgnoreCase("null")){
			
			sqlString = sqlString + "Sex='"+user.getSex()+"',";
		}
		if(user.getBirthday()!=null&&!user.getBirthday().equalsIgnoreCase("null")){
			
			sqlString = sqlString + "Birthday='"+user.getBirthday()+"',";
		}
		if(user.getAge()!=null&&!user.getAge().equalsIgnoreCase("null")){
			
			sqlString = sqlString + "Age='"+user.getAge()+"',";
		}
		if(user.getScore()!=null&&!user.getScore().equalsIgnoreCase("null")){
			
			sqlString = sqlString + "Score='"+user.getScore()+"',";
		}
		if(user.getPhotoList()!=null && user.getPhotoList().size() > 0){
			
			String photoList = new ListUtil().ListToString(user.getPhotoList());
			sqlString = sqlString + "PhotoList='"+photoList+"',";
		}
		if(user.getCity()!=null&&!user.getCity().equalsIgnoreCase("null")){
			
			sqlString = sqlString + "City='"+user.getCity()+"',";
		}
		if(user.getSignature()!=null&&!user.getSignature().equalsIgnoreCase("null")){
			
			sqlString = sqlString + "Signature='"+user.getSignature()+"',";
		}
		if(user.getCustomTag()!=null&&!user.getCustomTag().equalsIgnoreCase("null")){
			
			sqlString = sqlString + "CustomTag='"+user.getCustomTag()+"',";
		}
		if(!userDetailDAO.updateUserDetail(user,sqlString)){
			
			throw new GalaxyLabException("Error in update UserDetail");
		}
		UserDetail userDetail = userDetailDAO.getUserDetailByUserID(user.getUserID());
		if(userDetail != null){
			userDetailCache.put(userDetail.getUserID(), userDetail);
		}
	}
	
	
	
}
