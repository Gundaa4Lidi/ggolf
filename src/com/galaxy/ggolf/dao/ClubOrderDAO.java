package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.OrderRowMapper;
import com.galaxy.ggolf.domain.ClubOrder;
import com.galaxy.ggolf.domain.ClubServe;

public class ClubOrderDAO extends GenericDAO<ClubOrder> {

	private static final String Cancel_order = "取消订单";	
	private static final String Submit_order = "提交订单"; 
	private static final String Confirm_ball = "确认球位"; 
	private static final String Online_booking = "在线预订";
	private static final String Finish_booking = "完成预订";
	
	public ClubOrderDAO() {
		super(new OrderRowMapper());
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 生成订单编号
	 * @return
	 */
	public int getOrderID(){
		String[] s = new String[2];
		s[0] = "insert into orderid(`Num`)values(NULL)";
		s[1] = "select max(Num) as a  from orderid";
		return super.getId(s);
	}
	
	public boolean createOrder(ClubOrder o){
		String sql = "insert into `cluborder`("
				+ "OrderID,"
				+ "UserID,"
				+ "ClubID,"
				+ "ClubName,"
				+ "ClubserveID,"
				+ "ClubserveName,"
				+ "ClubserveLimitTimeID,"
				+ "ClubservePriceID,"
				+ "State,"
				+ "CreateTime,"
				+ "DownPayment,"
				+ "StartDate,"
				+ "StartTime,"
				+ "Names,"
				+ "Tel,"
				+ "ServiceExplain,"
				+ "Created_TS,"
				+ "Activity"
				+ ")values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return super.executeUpdate(sql,o.getOrderID(),o.getUserID(),o.getClubID(),o.getClubName(),o.getClubserveID(),
				o.getClubserveName(),o.getClubserveLimitTimeID(),o.getClubservePriceID(),Submit_order,Time1(),
				o.getDownPayment(),o.getStartDate(),o.getStartTime(),o.getNames(),o.getTel(),
				o.getServiceExplain(),Time(),Time()+",提交订单|");
	}
	
	public Collection<ClubOrder> getAll(){
		String sql = "select * from `cluborder` where DeletedFlag is null order by Created_TS desc";
		return super.executeQuery(sql);
	}
	
	/**
	 * 获取对应用户的订单资料
	 * @param UserID
	 * @param sqlString
	 * @return
	 */
	public Collection<ClubOrder> getOrderByUserID(String UserID,String pageNum,String rows,String sqlString){
		String limit = super.limit(pageNum, rows);
		String sql = "select * from cluborder where DeletedFlag is null and UserID='"+UserID+"' "+sqlString+""
				+ "order by Created_TS desc "+limit+" ";
		return super.executeQuery(sql);
	}
	
	/**
	 * 获取对应用户的订单数量
	 * @param UserID
	 * @param sqlString
	 * @return
	 */
	public int getCountByUserID(String UserID,String sqlString){
		String sql = "select count(*) from cluborder where DeletedFlag is null and UserID='"+UserID+"' "+sqlString+"";
		return super.count(sql);
	}
	
	/**
	 * 获取订单
	 * @param sqlString
	 * @param pageNum
	 * @param rows
	 * @param ClubID
	 * @return
	 */
	public Collection<ClubOrder> getClubOrder(String sqlString,String rows){
		String sql = "select * from cluborder where DeletedFlag is null "+sqlString+""
				+ "order by Created_TS desc limit "
				+ "0 , " + Integer.parseInt(rows) + " ";
		return super.executeQuery(sql);
	}
	
	/**
	 * 获取订单数量
	 * @param sqlString
	 * @return
	 */
	public int getOrderCount(String sqlString){
		String sql = "select count(*) from cluborder where DeletedFlag is null "+sqlString+"";
		return super.count(sql);
	}
	
	/**
	 * 查看对应订单的信息
	 * @param OrderID
	 * @return
	 */
	public ClubOrder getOrderByOrderID(String OrderID){
		String sql = "select * from `cluborder` where DeletedFlag is null and OrderID='"+OrderID+"'";
		Collection<ClubOrder> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (ClubOrder) result.toArray()[0];
		}
		return null;
	}
	
	/**
	 * 客服确认球位
	 * @param OrderID
	 * @return
	 */
	public boolean confirmBall(String OrderID){
		String sql = "update `cluborder` set `Activity` = concat(`Activity`,'"+Time()+",确认球位|'), State = '"+Confirm_ball
				+"',Updated_TS='"+Time()+"' where State = '"+Submit_order+"' and OrderID='"+OrderID+"'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 在线预订
	 * @param OrderID
	 * @return
	 */
	public boolean onlineBooking(String OrderID){
		String sql = "update `cluborder` set `Activity` = concat(`Activity`,'"+Time()+",在线预订|'), State = '"+Online_booking
				+"',Updated_TS='"+Time()+"' where State = '"+Confirm_ball+"' and OrderID='"+OrderID+"'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 完成预订
	 * @param OrderID
	 * @return
	 */
	public boolean finishBooking(String OrderID,String Type){
		String sql = "update `cluborder` set `Activity` = concat(`Activity`,'"+Time()+",预订成功|'),Type='"+Type+"' State = '"+Finish_booking
				+"',Updated_TS='"+Time()+"' where State = '"+Online_booking+"' and OrderID='"+OrderID+"'";
		return super.executeUpdate(sql);
	}
	
	
	/**
	 * 手动取消订单
	 * @param OrderID
	 * @return
	 */
	public boolean cancel(String OrderID){
		String sql = "update `cluborder` set Activity = concat(Activity,'"+Time()+",取消订单|'),"
				+ "State='"+Cancel_order+"',Updated_TS='"+Time()+"' where State != '"+Finish_booking+"' and State != '"+Cancel_order+"' and OrderID='"+OrderID+"'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 取消30分钟没有付款的订单
	 * @param dateTime
	 * @return
	 * @throws Exception
	 */
	public boolean cancelOrders(String dateTime)throws Exception{
		String sql = "update `cluborder` set `Activity` = concat(`Activity`,'"+Time()+",下单30分钟没有付款取消订单|'), State = '"+Cancel_order
				+"',Updated_TS='"+Time()+"' where State != '"+Finish_booking+"' and State != '"+Cancel_order+"' and Created_TS<'"+dateTime+"'";
		return super.executeUpdate(sql);
	}
	
	
}
