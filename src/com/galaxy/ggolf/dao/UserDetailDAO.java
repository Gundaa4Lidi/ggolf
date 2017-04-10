package com.galaxy.ggolf.dao;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import com.galaxy.ggolf.dao.mapper.UserDetailRowMapper;
import com.galaxy.ggolf.domain.User;
import com.galaxy.ggolf.domain.UserDetail;

public class UserDetailDAO extends GenericDAO<UserDetail> {

	public UserDetailDAO() {
		super(new UserDetailRowMapper());
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 创建用户详情
	 * @param user
	 * @return
	 */
	public boolean createUserDL(UserDetail user){
		String sql = "insert into userdetail (UserID,"
				+ "PhoneNum,"
				+ "HeadPhoto,"
				+ "UserName,"
				+ "Sex,"
				+ "Birthday,"
				+ "Age,"
				+ "Score,"
				+ "City,"
				+ "Signature,"
				+ "Status,"
				+ "CustomTag,"
				+ "Updated_TS) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return super.sqlUpdate(sql, user.getUserID(),user.getPhoneNum(),user.getHeadPhoto(),
				user.getUserName(),user.getSex(),user.getBirthday(),user.getAge(),user.getScore(),
				user.getCity(),user.getSignature(),
				user.getStatus(),user.getCustomTag(),Time());
	}
	/**
	 * 创建图册（用户未创建详情下创建图册）
	 * @param photoList
	 * @param userID
	 * @return
	 */
	public boolean createPhotoList(String photoList, String userID){
		String sql = "insert into userdetail(PhotoList,UserID)values('"+ photoList +"','"+ userID +"')";
		return super.executeUpdate(sql);
	}
	
	
	
	/**
	 * 修改全部详细
	 * @param user
	 * @return
	 */
	public boolean updateAllUserDetail(UserDetail user){
		String sql = "update userdetail set HeadPhoto='"+user.getHeadPhoto()
					+"',UserName='"+user.getUserName()
					+"',PhoneNum='"+user.getPhoneNum()
					+"',Sex='"+user.getSex()
					+"',Birthday='"+user.getBirthday()
					+"',Age='"+user.getAge()
					+"',Score='"+user.getScore()
					+"',City='"+user.getCity()
					+"',Signature='"+user.getSignature()
					+"',CustomTag='"+user.getCustomTag()
					+"',Updated_TS='"+Time()
					+"' where UserID ='"+user.getUserID()+"'";
		return super.executeUpdate(sql);
	}
	
	
	/**
	 * 根据用户编号获取用户详情
	 * @param userID
	 * @return
	 */
	public UserDetail getUserDetailByUserID(String userID) {
		String sql = "select * from userDetail where UserID ='" + userID + "' and DeletedFlag is null";
		Collection<UserDetail> result = super.executeQuery(sql);
		if (result.size() > 0) {
			return (UserDetail) result.toArray()[0];
		} else {
			return null;
		}
	}
	
	/**
	 * 修改部分详细
	 * @param user
	 * @param sqlString
	 * @return
	 */
	public boolean updateUserDetail(UserDetail user, String sqlString){
		String sql = "update userdetail set "+sqlString+" Updated_TS = '"+Time()+"' where userID = '"+user.getUserID()+"'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 修改手机
	 * @param phone
	 * @param userID
	 * @return
	 */
	public boolean updateUserDetailPhone(String phone, String userID){
		String sql = "update userdetail set Phone='"+phone+"', Updated_TS = '"+Time()+"' where userID = '"+userID+"'";
		return super.executeUpdate(sql);
	}
	
	
	/**
	 * 单张或多张图片上传
	 * @param photo
	 * @param phone
	 * @return
	 */
	/*public boolean updatePhotoList(String photo,String userID){
		String sql = "update userdetail set PhotoList = concat(PhotoList,'"+photo+";'),Updated_TS='"+Time()+"' where UserID = '"+userID+"'";
		return super.executeUpdate(sql);
	}*/
	
	
	/**
	 * 更新全部图片
	 * @param photos
	 * @param phone
	 * @return
	 */
	public boolean updateAllPhotoList(String photos, String userID){
		String sql = "update userdetail set PhotoList = '"+photos+"',Updated_TS='"+Time()+"' where UserID = '"+userID+"'";
		return super.executeUpdate(sql);
	}
	

}
