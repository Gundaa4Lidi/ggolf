package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.ClubserveLimitTimeRowMapper;
import com.galaxy.ggolf.domain.ClubserveLimitTime;

public class ClubserveLimitTimeDAO extends GenericDAO<ClubserveLimitTime> {

	public ClubserveLimitTimeDAO() {
		super(new ClubserveLimitTimeRowMapper());
	}
	
	
	/**
	 * 生成订单编号
	 * @return
	 */
	public int getClubserveLimitTimeID(){
		String[] s = new String[2];
		s[0] = "insert into clubservelimittimeid(`Num`)values(NULL)";
		s[1] = "select max(Num) as a  from clubservelimittimeid";
		return super.getId(s);
	}
	
	//创建限时活动
	public boolean create(ClubserveLimitTime clt){
		if(clt.getClubserveLimitTimeID()!=null){
			String lt = "LT";
			String main = "000000";
			String count = clt.getClubserveLimitTimeID();
			String id = super.getCustomID(lt, main, count);
			clt.setClubserveLimitTimeID(id);
			String sql = "insert into clubservelimittime("
					+ "ClubserveLimitTimeID,"
					+ "Name,"
					+ "ClubID,"
					+ "ClubserveID,"
					+ "Price,"
					+ "StartTime,"
					+ "EndTime,"
					+ "Count,"
					+ "Date,"
					+ "BeginStartTime,"
					+ "BeginEndTime,"
					+ "ServiceExplain,"
					+ "Created_TS)values(?,?,?,?,?,?,?,?,?,?,?,?,?) ";
			return super.executeUpdate(sql,clt.getClubserveLimitTimeID(),clt.getName(),clt.getClubID(),
					clt.getClubserveID(),clt.getPrice(),clt.getStartTime(),clt.getEndTime(),clt.getCount(),clt.getDate(),
					clt.getBeginStartTime(),clt.getBeginEndTime(),clt.getServiceExplain(),Time());
		}
		return false;
	}
	
	//修改限时活动
	public boolean update(ClubserveLimitTime clt){
		String sql = "update clubservelimittime set "
				+ "Name=?,"
				+ "Price=?,"
				+ "StartTime=?,"
				+ "EndTime=?,"
				+ "Count=?,"
				+ "Date=?,"
				+ "BeginStartTime=?,"
				+ "BeginEndTime=?,"
				+ "ServiceExplain=?,"
				+ "Updated_TS=? where ClubserveLimitTimeID=?";
		return super.executeUpdate(sql,clt.getName(),clt.getPrice(),clt.getStartTime(),
				clt.getEndTime(),clt.getCount(),clt.getDate(),clt.getBeginStartTime(),clt.getBeginStartTime(),
				clt.getServiceExplain(),Time(),clt.getClubserveLimitTimeID());
	}
	
	//查看是否时间冲突
	public ClubserveLimitTime getLimitTimeByClubserveID(String ClubserveID,String Date){
		String sql = "select * from clubservelimittime where DeletedFlag is null and "
				+ "Date='"+Date+"' and ClubserveID='"+ClubserveID+"' ";
		Collection<ClubserveLimitTime> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (ClubserveLimitTime) result.toArray()[0];
		}
		return null;
	}
	
	//查看限时编号的活动信息
	public ClubserveLimitTime getByClubserveLimitTimeID(String ClubserveLimitTimeID){
		String sql = "select * from clubservelimittime where DeletedFlag is null and "
				+ "ClubserveLimitTimeID='"+ClubserveLimitTimeID+"'";
		Collection<ClubserveLimitTime> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (ClubserveLimitTime) result.toArray()[0];
		}
		return null;
	}
	
	//获取供应商下的限时活动
	public Collection<ClubserveLimitTime> getbyClubserveID(String ClubserveID,String rows,String pageNum){
		String limit = super.limit(pageNum, rows);
		String sql = "select * from clubservelimittime where DeletedFlag is null and "
				+ "ClubserveID='"+ClubserveID+"' order by Created_TS desc "+limit+"";
		return super.executeQuery(sql);
	}
	
	//获取供应商下的限时活动数量
	public int getCountbyClubserveID(String ClubserveID){
		String sql = "select count(*) from clubservelimittime where DeletedFlag is null and "
				+ "ClubserveID='"+ClubserveID+"'";
		return super.count(sql);
	}
	
	//删除
	public boolean delete(String ClubserveLimitTimeID){
		String sql = "update clubservelimittime set DeletedFlag='Y' where ClubserveLimitTimeID='"+ClubserveLimitTimeID+"'";
		return super.executeUpdate(sql);
	}
	
	//修改限时活动的抢购数量
	public boolean realCount(String ClubserveLimitTimeID,String realCount){
		String sql = "update clubservelimittime set Count='"+realCount+"' where DeletedFlag is null and ClubserveLimitTimeID='"+ClubserveLimitTimeID+"'";
		return super.executeUpdate(sql);
	}
	
	//获取已开抢的限时活动
	public Collection<ClubserveLimitTime> getForTime(String dateTime,String rows,String pageNum){
		String limit = super.limit(pageNum, rows);
		String sql = "select * from clubservelimittime where DeletedFlag is null and "
				+ "BeginStartTime <= '"+dateTime+"' and BeginEndTime > '"+dateTime+"' "
				+ "order by Created_TS desc "+limit+"";
		return super.executeQuery(sql);
	}
	//获取已开抢的限时活动数量
	public int getForTimeCount(String dateTime){
		String sql = "select count(*) from clubservelimittime where DeletedFlag is null and "
				+ "BeginStartTime <= '"+dateTime+"' and BeginEndTime > '"+dateTime+"'";
		return super.count(sql);
	}
	//获取未开抢的限时活动
	public Collection<ClubserveLimitTime> getForNotOpen(String dateTime,String rows,String pageNum){
		String limit = super.limit(pageNum, rows);
		String sql = "select * from clubservelimittime where DeletedFlag is null and "
				+ "BeginStartTime > '"+dateTime+"' order by Created_TS desc "+limit+"";
		return super.executeQuery(sql);
	}
	//获取未开抢的限时活动数量
	public int getCountForNotOpen(String dateTime){
		String sql = "select count(*) from clubservelimittime where DeletedFlag is null and "
				+ "BeginStartTime > '"+dateTime+"'";
		return super.count(sql);
	}
	
	

}
