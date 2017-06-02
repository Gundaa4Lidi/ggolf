package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.ClubServeRowMapper;
import com.galaxy.ggolf.domain.ClubServe;

public class ClubServeDAO extends GenericDAO<ClubServe> {

	public ClubServeDAO() {
		super(new ClubServeRowMapper());
		// TODO Auto-generated constructor stub
	}
	
	
	//生成供应商编号
	public int getClubserveID(){
		String[] s = new String[2];
		s[0] = "insert into servecount(`Num`)values(NULL)";
		s[1] = "select max(Num) as a  from servecount";
		return super.getId(s);
	}
	
	/**
	 * 创建球场供应商(俱乐部)
	 * @param cs
	 * @return
	 */
	public boolean create(ClubServe cs){
		String sql = "insert into clubserve(ClubserveID,"
				+ "ClubID,"
				+ "ClubName,"
				+ "Name,"
				+ "CancelExplain,"
				+ "ReserveExplain,"
				+ "ServiceExplain,"
				+ "MaxDay,"
				+ "Created_TS)values(?,?,?,?,?,?,?,?,?)";
		return super.executeUpdate(sql,cs.getClubserveID(),cs.getClubID(),cs.getClubName(),cs.getName(),cs.getCancelExplain(),cs.getReserveExplain(),
				cs.getServiceExplain(),cs.getMaxDay(),Time());
	}
	
	/**
	 * 获取对应球场的供应商(分页)
	 * @param sqlString
	 * @param pageNum
	 * @param rows
	 * @param ClubID
	 * @return
	 */
	public Collection<ClubServe> getClubServeByClubID(String sqlString,String pageNum,String rows,String ClubID){
		String limit = super.limit(pageNum, rows);
		String sql = "select * from clubserve where DeletedFlag is null and ClubID='"+ClubID+"' "+sqlString+""
				+ "order by Created_TS desc "+limit+" ";
		return super.executeQuery(sql);
	}
	
	/**
	 * 获取对应球场的供应商数量
	 * @param sqlString
	 * @param pageNum
	 * @param rows
	 * @param ClubID
	 * @return
	 */
	public int getCountByClubID(String sqlString,String ClubID){
		String sql = "select count(*) from clubserve where DeletedFlag is null and ClubID='"+ClubID+"' "+sqlString+"";
		return super.count(sql);
	}
	
	public Collection<ClubServe> getClubServe(String sqlString,String pageNum,String rows){
		String limit = super.limit(pageNum, rows);
		String sql = "select * from clubserve where DeletedFlag is null  "+sqlString+""
				+ "order by Created_TS desc "+limit+" ";
		return super.executeQuery(sql);
	}
	
	public int getCount(String sqlString){
		String sql = "select count(*) from clubserve where DeletedFlag is null "+sqlString+"";
		return super.count(sql);
	}
	
	/**
	 * 是否有效和有优惠时段的供应商
	 * @param DateTime
	 * @param ClubID
	 * @param pageNum
	 * @param rows
	 * @return
	 */
	public Collection<ClubServe> getPrivilegeClubserve(String DateTime,String ClubID, String pageNum,String rows){
		String limit = super.limit(pageNum, rows);
		String sql = "select * from clubserve where DeletedFlag is null and ClubserveID in("
				+ "select ClubserveID from pricefortime where DeletedFlag is null and IsPrivilege='1'"
				+ " and IsValid='1' and DateTime='"+DateTime+"' and ClubID='"+ClubID+"' group by ClubserveID)"
				+ " order by Created_TS desc "+limit+" ";
		return super.executeQuery(sql);
	}
	
	/**
	 * 是否有效和有优惠时段的供应商数量
	 * @param DateTime
	 * @param ClubID
	 * @return
	 */
	public int getPrivilegeCount(String DateTime,String ClubID){
		String sql = "select count(*) from clubserve where DeletedFlag is null and ClubserveID in("
				+ "select ClubserveID from pricefortime where DeletedFlag is null IsPrivilege='1'"
				+ " and IsValid='1' and DateTime='"+DateTime+"' and ClubID='"+ClubID+"' group by ClubserveID)";
		return super.count(sql);
	}
	
	/**
	 * 查询有效价格时段的供应商
	 * @param DateTime
	 * @param ClubID
	 * @param pageNum
	 * @param rows
	 * @return
	 */
	public Collection<ClubServe> getValidClubserve(String DateTime,String ClubID, String Time, String pageNum,String rows){
		String time = "";
		if(Time!=null&&!Time.equals("")){
			time = "and Time='"+Time+"' ";
		}
		String limit = super.limit(pageNum, rows);
		String week = super.GetWeek(DateTime);
		String sql = "select * from clubserve where DeletedFlag is null and ClubserveID in("
				+ "select ClubserveID from("
				+ "(select * from pricefortime where `DeletedFlag` is null and `DateTime`='"+DateTime+"' "+time+")"
				+ " UNION "
				+ "(select * from pricefortime where `DeletedFlag` is null and `DateTime` is null and `Week`='"+week+"' "+time+""
				+ " and `Time` not in"
				+ "(select `Time` from pricefortime where `DeletedFlag` is null and `DateTime`='"+DateTime+"' "+time+"))) a "
				+ "where `IsValid`='1' and ClubID='"+ClubID+"' group by `ClubserveID`)"
				+ " order by Created_TS desc "+limit+" ";
		return super.executeQuery(sql);
	}
	
	/**
	 * 查询有效价格时段的供应商数量
	 * @param DateTime
	 * @param ClubID
	 * @return
	 */
	public int getValidClubserveCount(String DateTime,String ClubID,String Time){
		String time = "";
		if(Time!=null&&!Time.equals("")){
			time = "and Time='"+Time+"' ";
		}
		String week = super.GetWeek(DateTime);
		String sql = "select count(*) from clubserve where DeletedFlag is null and ClubserveID in("
				+ "select ClubserveID from("
				+ "(select * from pricefortime where `DeletedFlag` is null and `DateTime`='"+DateTime+"' "+time+")"
				+ " UNION "
				+ "(select * from pricefortime where `DeletedFlag` is null and `DateTime` is null and `Week`='"+week+"' "+time+""
				+ " and `Time` not in"
				+ "(select `Time` from pricefortime where `DeletedFlag` is null and `DateTime`='"+DateTime+"' "+time+"))) a "
				+ "where `IsValid`='1' and ClubID='"+ClubID+"' group by `ClubserveID`)";
		return super.count(sql);
	}
	
	
	
	/**
	 * 修改球场供应商(俱乐部)
	 * @param cs
	 * @return
	 */
	public boolean update(ClubServe cs){
		String sql = "update clubserve set Name=?,"
				+ "CancelExplain=?,"
				+ "ReserveExplain=?,"
				+ "ServiceExplain=?,"
				+ "MaxDay=?,"
				+ "Updated_TS=? where DeletedFlag is null and ClubServeID=?";
		return super.executeUpdate(sql,cs.getName(),cs.getCancelExplain(),
				cs.getReserveExplain(),cs.getServiceExplain(),cs.getMaxDay(),Time(),cs.getClubserveID());		
	}
	
	/**
	 * 查找相关的球场供应商(俱乐部)
	 * @param ClubserveID
	 * @return
	 */
	public ClubServe getClubServe(String ClubserveID){
		String sql = "select * from clubserve where DeletedFlag is null and ClubserveID='"+ClubserveID+"'";
		Collection<ClubServe> result = super.executeQuery(sql);
		if(result.size()>0){
			return (ClubServe) result.toArray()[0];
		}
		return null;
	}
	
	/**
	 * 删除球场供应商(俱乐部)
	 * @param ClubserveID
	 * @return
	 */
	public boolean delete(String ClubserveID){
		String sql = "update clubserve set DeletedFlag='Y',Updated_TS='"+Time()+"' where ClubserveID='"+ClubserveID+"'";
		return super.executeUpdate(sql);
	}

}
