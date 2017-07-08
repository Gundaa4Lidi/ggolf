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
	private static final String Refund_apply = "退款申请";
	private static final String Refund_applying = "退款处理中";
	private static final String Refund_success = "退款成功";
	private static final String Refund_field = "退款失败";
	
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
				+ "OtherPayment,"
				+ "PayBillorNot,"
				+ "StartDate,"
				+ "StartTime,"
				+ "Names,"
				+ "MemberCount,"
				+ "Tel,"
				+ "ServiceExplain,"
				+ "Marks,"
				+ "IsRead,"
				+ "Created_TS,"
				+ "Activity"
				+ ")values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return super.executeUpdate(sql,o.getOrderID(),o.getUserID(),o.getClubID(),o.getClubName(),o.getClubserveID(),
				o.getClubserveName(),o.getClubserveLimitTimeID(),o.getClubservePriceID(),Submit_order,Time1(),
				o.getDownPayment(),o.getOtherPayment(),"0",o.getStartDate(),o.getStartTime(),o.getNames(),o.getMemberCount(),o.getTel(),
				o.getServiceExplain(),o.getMarks(),"0",Time(),Time()+",订单提交|");
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
		String limit = super.limit(null, rows);
		String sql = "select * from cluborder where DeletedFlag is null "+sqlString+""
				+ "order by Created_TS desc "+limit+" ";
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
	 * 新增订单
	 * @param rows
	 * @param pageNum
	 * @return
	 */
	public Collection<ClubOrder> getNewOrder(String rows,String pageNum){
		String limit = super.limit(pageNum, rows);
		String sql = "select * from cluborder where DeletedFlag is null and State='"+Submit_order+"' "
				+ "order by Created_TS desc "+limit+"";
		return super.executeQuery(sql);
	}
	
	/**
	 * 新增订单的数量
	 * @return
	 */
	public int getNewOrderCount(){
		String sql = "select count(*) from cluborder where DeletedFlag is null and State='"+Submit_order+"'";
		return super.count(sql);
	}
	
	/**
	 * 新增订单未查阅的数量
	 * @return
	 */
	public int getNewOrderRealCount(){
		String sql = "select count(*) from cluborder where DeletedFlag is null and State='"+Submit_order+"' and IsRead='0' ";
		return super.count(sql);
	}
	
	/**
	 * 获取退款订单
	 * @param rows
	 * @param pageNum
	 * @return
	 */
	public Collection<ClubOrder> getRefundOrder(String rows,String pageNum){
		String limit = super.limit(pageNum, rows);
		String sql = "select * from cluborder where DeletedFlag is null and Refund='"+Refund_apply+"'"
				+ " order by Created_TS desc "+limit+"";
		return super.executeQuery(sql);
	}
	
	/**
	 * 新增退款订单的数量
	 * @return
	 */
	public int getRefundOrderCount(){
		String sql = "select count(*) from cluborder where DeletedFlag is null and Refund='"+Refund_apply+"'";
		return super.count(sql);
	}
	
	/**
	 * 新增退款订单未查阅的数量
	 * @return
	 */
	
	public int getRefundOrderRealCount(){
		String sql = "select count(*) from cluborder where DeletedFlag is null and Refund='"+Refund_apply+"' and IsRead='0'";
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
	 * 修改留言
	 * @param OrderID
	 * @param Marks
	 * @return
	 */
	public boolean updateMarks(String OrderID,String Marks){
		String sql = "update `cluborder` set `Activity` = concat(`Activity`,'"+Time()+",添加留言|'), Marks = '"+Marks
				+"',Updated_TS='"+Time()+"' where OrderID='"+OrderID+"'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 设置已读
	 * @param OrderID
	 * @return
	 */
	public boolean IsRead(String OrderID){
		String sql = "update `cluborder` set IsRead = '1', Updated_TS='"+Time()+"' where IsRead='0' and OrderID='"+OrderID+"'";
		return super.executeUpdate(sql);
	}
	/**
	 * 客服确认球位
	 * @param OrderID
	 * @return
	 */
	public boolean confirmBall(String OrderID){
		String sql = "update `cluborder` set `Activity` = concat(`Activity`,'"+Time()+",球位确认|'), State = '"+Confirm_ball
				+"', IsRead='1', Updated_TS='"+Time()+"' where State = '"+Submit_order+"' and OrderID='"+OrderID+"'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 在线预订
	 * @param OrderID
	 * @return
	 */
	public boolean onlineBooking(String OrderID){
		String sql = "update `cluborder` set `Activity` = concat(`Activity`,'"+Time()+",在线支付|'), State = '"+Online_booking
				+"', Updated_TS='"+Time()+"' where State = '"+Confirm_ball+"' and OrderID='"+OrderID+"'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 完成预订
	 * @param OrderID
	 * @return
	 */
	public boolean finishBooking(String OrderID,String Type,String chargeID){
		String ChargeID = "";
		if(chargeID!=null){
			ChargeID = "ChargeID='"+chargeID+"',";
		}
		String sql = "update `cluborder` set `Activity` = concat(`Activity`,'"+Time()+",预订成功|'),Type='"+Type+"',"
				+ " State = '"+Finish_booking+"',"+ChargeID+" Updated_TS='"+Time()+"' where"
				+ " State = '"+Online_booking+"' and OrderID='"+OrderID+"'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 现金支付
	 * @param orderID
	 * @return
	 */
	public boolean cashPayment(String orderID){
		String sql = "update `cluborder` set `Activity` = concat(`Activity`,'"+Time()+",现金支付|'),PayBillorNot='1',Updated_TS='"+Time()+"' "
				+ "where State='"+Finish_booking+"' and OtherPayment is not null and OrderID='"+orderID+"'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 申请退款
	 * @param OrderID
	 * @return
	 */
	public boolean applyRefund(String OrderID,String description){
		String sql = "update `cluborder` set `Activity` = concat(`Activity`,'"+Time()+",申请退款|'), Refund ='"+Refund_apply
				+"',Description='"+description+"', IsRead='0', Updated_TS='"+Time()+"' where State='"+Finish_booking+"' and OrderID='"+OrderID+"'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 退款结果
	 * @param result
	 * @param OrderID
	 * @return
	 */
	public boolean refundResult(String result,String OrderID){
		String sql = "update `cluborder` set `Activity` = concat(`Activity`,'"+Time()+","+result+"|'), Refund ='"+result
				+"', Updated_TS='"+Time()+"' where Refund='"+Refund_apply+"' and OrderID='"+OrderID+"'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 手动取消订单
	 * @param OrderID
	 * @return
	 */
	public boolean cancel(String OrderID){
		String sql = "update `cluborder` set Activity = concat(Activity,'"+Time()+",订单取消|'),"
				+ "State='"+Cancel_order+"',Updated_TS='"+Time()+"' where State!="
				+ "'"+Finish_booking+"' and State!='"+Cancel_order+"' and OrderID='"+OrderID+"'";
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
				+"',Updated_TS='"+Time()+"' where State!='"+Finish_booking+"' and State!='"+Cancel_order+"' and Created_TS<'"+dateTime+"'";
		return super.executeUpdate(sql);
	}
	
	
}
