package com.galaxy.ggolf.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.galaxy.ggolf.dao.mapper.UserRowMapper;
import com.galaxy.ggolf.domain.User;
import com.spatial4j.core.shape.Rectangle;



public class UserDAO extends GenericDAO<User> {

	public UserDAO() {
		super(new UserRowMapper());
	}
	
	
	//创建用户
	public boolean createUser(User user) {
		Date now = new Date();
		String dt = new String(
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
		String sql = "INSERT INTO user (phone,Name,password,head_portrait,Created_TS) VALUES ('"
				+ user.getPhone() + "','"+user.getName()+"','" + user.getPassword() + "','"+user.getHead_portrait()+"','"+dt+"')";
		return super.executeUpdate(sql);
	}
	
	
	
	//获取所有用户的数量
	public int getCount() {
		String sql = "select count(*) from user where DeletedFlag is null";
		return super.count(sql);
	}
	
	//获取搜索数量
	public int getSearchCount(String sqlString){
		String sql = "select count(*) from user where DeletedFlag is null "+sqlString+"";
		return super.count(sql);
	}
	
	//关键字获取用户信息(分页,加载)
	public Collection<User> getUsers(String pageNum,String size,String sqlString) {
		int pages = 1;
		String limit = "";
		if(pageNum!=null){
			pages = Integer.parseInt(pageNum);
		}
		if(size!=null && !size.equals("")){
			limit ="limit " +((pages - 1) * Integer.parseInt(size)) + "," + Integer.parseInt(size);
		}
		String sql = "select * from user where DeletedFlag is null "+sqlString+" order by created_ts desc "+limit+" ";
		return GetUserList(super.executeQuery(sql));
	}
	
	
	//获取所有用户信息
	public Collection<User> getAll(){
		String sql = "select * from user where DeletedFlag is null order by created_ts desc";
		return GetUserList(super.executeQuery(sql));
	}
	
	//修改用户基本信息
	public boolean updateUser(User user){
		String sql = "update user set Name='"+ user.getName()
					+"', Sex='"+ user.getSex()
					+"', head_portrait='"+ user.getHead_portrait()
					+"', Updated_TS='"+ Time()
					+"' where UserID='"+ user.getUserID() +"'";
		return super.executeUpdate(sql);
	}
	
	//修改头像
	public boolean updateHead(String head,String userID){
		String sql = "update user set head_portrait='"+head+"' where UserID = '"+userID+"' and DeletedFlag is null";
		return super.executeUpdate(sql);
	}

	/*public boolean checkusername(String phone, String password)
			throws Exception {
		String sql = "select count(*) from user where phone = '" + phone
				+ "' and password='" + password + "' and where DeletedFlag is null";
		if (super.count(sql) == 1) {
			return true;
		}
		return false;
	}*/
	
	//修改用户的位置信息
	public boolean updateLocation(String longitude,String latitude,String userID)throws Exception{
		String sql = "update user set Longitude = '"+ longitude +"', latitude = '"+ latitude +"',Updated_TS = '"+Time()+"' where UserID = '"+ userID +"'";
		return super.executeUpdate(sql);
	}

	
	
	//微信号创建用户
	public boolean createUserByWechat(User user){
		String sql = "insert into user(wechat,name,head_portait,Created_TS)values(?,?,?,?)";
		return super.sqlUpdate(sql, user.getWechat(),user.getName(),user.getHead_portrait(),Time());
	}

	//根据用户id查询是否存在
	public User getUserByUserID(String userID) {
		String sql = "select * from user where UserID='" + userID + "'AND DeletedFlag is null";
		Collection<User> result = super.executeQuery(sql);
		if (result.size() > 0) {
			return GetUser((User) result.toArray()[0]);
		} else {
			return null;
		}
	}
	
	//根据微信查询
	public User getUserByWechat(String wechat) {
		String sql = "select * from user where Wechat='" + wechat + "'AND DeletedFlag is null";
		Collection<User> result = super.executeQuery(sql);
		if (result.size() > 0) {
			return GetUser((User) result.toArray()[0]);
		} else {
			return null;
		}
	}
	//根据手机查询
	public User getUserByPhone(String phone) {
		String sql = "select * from user where phone='" + phone + "'AND DeletedFlag is null";
		Collection<User> result = super.executeQuery(sql);
		if (result.size() > 0) {
			return (User) result.toArray()[0];
		} else {
			return null;
		}
	}
	//修改密码
	public boolean updatepassword(String userID,String password) throws Exception {
		String sql = "update user set password ='"+password+"'  where UserID ='"
				+ userID + "' ";
		return super.executeUpdate(sql);
	}
	//彻底删除用户
	public boolean deleteUser(String userID) throws Exception {
		String sql = "delete from user where UserID = '"+userID+"'";
		return super.executeUpdate(sql);
	}
	
	/*public Collection<User> searchUserInfo(String keyword,int pageNum,int size) {
		String sql = "select * from user where DeletedFlag is null and (Name like '%"
				+ keyword
				+ "%' or Phone like '%"
				+ keyword 
				+"%' or created_ts like '%"
				+ keyword +"%') order by created_ts desc limit "
				+ ((pageNum - 1) * size) + ","+ size + "";
			
		return super.executeQuery(sql);
	}*/
	
	
	//更换手机
	public boolean updatePhone(String phone, String userID){
		String sql = "update user set phone='"+phone+"' where UserID='"+userID+"'";
		return super.executeUpdate(sql);
	}
	
	//搜索附近的人
	public Collection<User> getNearByUser(Rectangle rec, String sqlString){
		String sql = "select * from user where(longitude between '"+rec.getMinX()+"' and '"+rec.getMaxX()+"') "
				+ "and (latitude between '"+rec.getMinY()+"' and '"+rec.getMaxY()+"')"
				+ "and DeletedFlag is null "+sqlString+"";
		return GetUserList(super.executeQuery(sql));
	}
	//附近的人数
	public int getCountNearUser(Rectangle rec,String sqlString){
		String sql = "select count(*) from user where(longitude between '"+rec.getMinX()+"' and '"+rec.getMaxX()+"') "
				+ "and (latitude between '"+rec.getMinY()+"' and '"+rec.getMaxY()+"')"
				+ "and DeletedFlag is null "+sqlString+"";
		return super.count(sql);
	}
	
	public Collection<User> getOnlineAll(){
		String sql = "select * from user where UserID in (select UserID from online where OnlineOrNot='Y') and DeletedFlag is null";
		return GetUserList(super.executeQuery(sql));
	}
	
	public int getOnlineCount(){
		String sql = "select count(*) from user where UserID in (select UserID from online where OnlineOrNot='Y') and DeletedFlag is null";
		return super.count(sql);
	}
	
	public Collection<User> getOpenGPSUser(){
		String sql = "select * from user where UserID in (select UserID from online where OnlineOrNot='Y' and OpenGPSOrNot='Y') and DeletedFlag is null";
		return GetUserList(super.executeQuery(sql));
	}
	
	public int getOpenGPSCount(){
		String sql = "select count(*) from user where UserID in (select UserID from online where OnlineOrNot='Y' and OpenGPSOrNot='Y') and DeletedFlag is null";
		return super.count(sql);
	}
	
	public User GetUser(User user){
		user = new User(user.getUserID(), user.getPhone(), user.getName(), user.getAge(),
				user.getSex(), user.getHead_portrait(), user.getLongitude(), user.getLatitude(),
				user.getDistance(), user.getWechat(), user.getCreated_TS());
		return user;
		
	}
	
	public Collection<User> GetUserList(Collection<User> list){
		Collection<User> users = new ArrayList<User>();
		User user = new User();
		for(User u : list){
			user = GetUser(u);
			users.add(user);
		}
		return users;
	}
	//获取点赞人员
	public Collection<User> getLikeList(String ThemeID,String Type){
		String sql = "select * from user where UserID in(select UserID from likes where ThemeID='"+ThemeID+"' and Type='"+Type+"') and DeletedFlag is null";
		Collection<User> users = super.executeQuery(sql);
		Collection<User> userList = new ArrayList<>();
		User u = new User();
		if(users.size()>0){
			for(User user : users){
				u = new User(user.getUserID(), user.getPhone(), user.getName(), user.getAge(), user.getSex(), user.getHead_portrait());
				userList.add(u);
			}
		}
		return userList;
	}
	
	/**
	 * 随机获取未关注过的用户
	 * @param UserID
	 * @return
	 */
	public Collection<User> getRandomUser(String UserID){
		String sql = "SELECT * FROM `user` where UserID!='"+UserID+"'"
				+ " and UserID NOT IN (select FenID from follow where DeletedFlag is null AND UserID='"+UserID+"')"
				+ " ORDER BY RAND() LIMIT 20";
		return GetUserList(super.executeQuery(sql));
	}
//	//根据关系获取好友列表
//	public Collection<User> getFollow(String UserID,String Relation,String Relation1){ 
//		String sql = "select * from user,follow where DeletedFlag is null and UserID in"
//				+ "(select FenID from follow where UserID='"+UserID+"' and DeletedFlag is null"
//				+ "and (Relation='"+Relation+"' or Relation='"+Relation1+"'))";
//		Collection<User> users = super.executeQuery(sql);
//		Collection<User> userList = new ArrayList<>();
//		User u = new User();
//		if(users.size()>0){
//			for(User user : users){
//				u = new User(user.getUserID(), user.getPhone(), user.getName(), user.getAge(), user.getSex(), user.getHead_portrait());
//				userList.add(u);
//			}
//		}
//		return userList;
//	}
	
}
