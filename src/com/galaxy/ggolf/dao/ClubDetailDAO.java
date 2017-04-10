package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.ClubDetailRowMapper;
import com.galaxy.ggolf.domain.ClubDetail;
import com.galaxy.ggolf.tools.ListUtil;

public class ClubDetailDAO extends GenericDAO<ClubDetail> {

	public ClubDetailDAO() {
		super(new ClubDetailRowMapper());
	}
	
	
	public boolean create(ClubDetail c){
		String photoList = new ListUtil().ListToString(c.getPhotoList());
		String mapImg = new ListUtil().ListToString(c.getMapImg());
		String facility = new ListUtil().ListToString(c.getFacility());
		String sql = "insert into clubdetail(ClubID,ClubName,Mode,TotalHole,"
				+ "TotalStemNum,PhoneNum,CreateTime,Stylist,Area,Length,PuttingSeed,"
				+ "FairwaySeed,Address,Intro,Updated_TS,PhotoList,MapImg,Facility)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return super.sqlUpdate(sql, c.getClubID(),c.getClubName(),c.getMode(),c.getTotalHole(),c.getTotalStemNum(),
						c.getPhoneNum(),c.getCreateTime(),c.getStylist(),c.getArea(),c.getLength(),c.getPuttingSeed(),
						c.getFairwaySeed(),c.getAddress(),c.getIntro(),Time(),photoList,mapImg,facility);
	}
	
	public boolean update(ClubDetail c){
		String photoList = new ListUtil().ListToString(c.getPhotoList());
		String mapImg = new ListUtil().ListToString(c.getMapImg());
		String facility = new ListUtil().ListToString(c.getFacility());
		String sql = "update clubdetail set ClubName=?,Mode=?,TotalHole=?,TotalStemNum=?,PhoneNum=?,"
				+ "CreateTime=?,Stylist=?,Area=?,Length=?,PuttingSeed=?,FairwaySeed=?,Address=?,Intro=?,"
				+ "Updated_TS=?,PhotoList=?,MapImg=?,Facility=? where ClubID=? and DeletedFlag is null";
		return super.sqlUpdate(sql, c.getClubName(),c.getMode(),c.getTotalHole(),c.getTotalStemNum(),
						c.getPhoneNum(),c.getCreateTime(),c.getStylist(),c.getArea(),c.getLength(),
						c.getPuttingSeed(),c.getFairwaySeed(),c.getAddress(),c.getIntro(),Time(),
						photoList,mapImg,facility);
	}
	
	public Collection<ClubDetail> getDetailByClubID(String clubID){
		String sql = "select * from clubdetail where ClubID='"+clubID+"' and DeletedFlag is null ";
		return super.executeQuery(sql);
	}
	
	public ClubDetail getClubDetailByClubID(String clubID){
		String sql = "select * from clubdetail where ClubID='"+clubID+"' and DeletedFlag is null ";
		Collection<ClubDetail> club = super.executeQuery(sql);
		if(club.size() > 0){
			return (ClubDetail) club.toArray()[0];
		}else{
			return null;
		}
	}
	
	public boolean detele(String clubID){
		String sql = "update clubdetail set DeletedFlag='Y',Updated_TS='"+Time()+"' where ClubID='"+clubID+"'";
		return super.executeUpdate(sql);
	}
}
