package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.ClubDetailRowMapper;
import com.galaxy.ggolf.domain.ClubDetail;
import com.galaxy.ggolf.domain.UserDetail;
import com.galaxy.ggolf.tools.ListUtil;

public class ClubDetailDAO extends GenericDAO<ClubDetail> {

	public ClubDetailDAO() {
		super(new ClubDetailRowMapper());
	}
	
	
	public boolean create(ClubDetail c){
		String photoList = "";
		if(c.getPhotoList()!=null&&c.getPhotoList().size() > 0){
			photoList = new ListUtil().ListToString(c.getPhotoList());
		}
		String sql = "insert into clubdetail(ClubID,"
				+ "ClubName,"
				+ "TotalHole,"
				+ "TotalStemNum,"
				+ "PhoneNum,"
				+ "Address,"
				+ "PhotoList)values(?,?,?,?,?,?,?)";
		return super.executeUpdate(sql, c.getClubID(),c.getClubName(),c.getTotalHole(),
				c.getTotalStemNum(),c.getPhoneNum(),c.getAddress(),photoList);
	}
	
	/**
	 * 修改部分详细
	 * @param user
	 * @param sqlString
	 * @return
	 */
	public boolean updateClubDetail(ClubDetail c, String sqlString){
		String sql = "update clubdetail set "+sqlString+" Updated_TS = '"+Time()+"' where ClubID = '"+c.getClubID()+"'";
		return super.executeUpdate(sql);
	}
	
	public Collection<ClubDetail> getDetailByClubID(String clubID){
		String sql = "select * from clubdetail where ClubID='"+clubID+"' and DeletedFlag is null ";
		return super.executeQuery(sql);
	}
	
	public ClubDetail getClubDetailByClubID(String clubID){
		String sql = "select * from clubdetail where ClubID='"+clubID+"' and DeletedFlag is null ";
		Collection<ClubDetail> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (ClubDetail) result.toArray()[0];
		}
		return null;
	}
	
	public boolean detele(String clubID){
		String sql = "update clubdetail set DeletedFlag='Y',Updated_TS='"+Time()+"' where ClubID='"+clubID+"'";
		return super.executeUpdate(sql);
	}
}
