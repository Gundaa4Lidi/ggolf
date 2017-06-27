package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.CourseOrderRowMapper;
import com.galaxy.ggolf.domain.ClubOrder;
import com.galaxy.ggolf.domain.CourseOrder;

public class CourseOrderDAO extends GenericDAO<CourseOrder> {

	public CourseOrderDAO() {
		super(new CourseOrderRowMapper());
	}
	
	private static final String Refund_apply = "退款申请";
	private static final String Refund_applying = "退款处理中";
	private static final String Refund_success = "退款成功";
	private static final String Refund_field = "退款失败";
	
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
	
	/**
	 * 创建课程订单
	 * @param co
	 * @return
	 */
	public boolean create(CourseOrder co){
		String sql = "insert into courseorder("
				+ "CourseOrderID,"
				+ "CoachID,"
				+ "CoachName,"
				+ "CourseID,"
				+ "CourseTitle,"
				+ "UserID,"
				+ "UserName,"
				+ "State,"
				+ "TeachingMethod,"
				+ "ClassHour,"
				+ "DownPayment,"
				+ "StartDateTime,"
				+ "Tel,"
				+ "IsBatch,"
				+ "IsRead,"
				+ "Marks,"
				+ "ServiceExplain,"
				+ "Created_TS,"
				+ "Activities)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return super.executeUpdate(sql,co.getCourseOrderID(),co.getCoachID(),co.getCoachName(),co.getCourseID(),co.getCourseTitle(),
				co.getUserID(),co.getUserName(),"1",co.getTeachingMethod(),co.getClassHour(),co.getDownPayment(),
				co.getStartDateTime(),co.getTel(),co.getIsBatch(),"0",co.getMarks(),co.getServiceExplain(),Time(),Time()+",提交订单|");
	}
	
	/**
	 * 修改订单状态
	 * @param CourseOrderID
	 * @param activity
	 * @param state
	 * @param state1
	 * @return
	 */
	public boolean updateOrderState(String CourseOrderID,String activity,String state,String state1,String payType){
		String sqlString = "";
		if(state.equals("3") && payType!=null){
			sqlString +="Type='"+payType+"',IsTaught='0',";
		}
		String sql = "update `courseorder` set `Activity` = concat(`Activity`,'"+activity+"'),"
				+ "Updated_TS='"+Time()+"', "+sqlString+" State ='"+state+"'"
				+"' where State = '"+state1+"' and CourseOrderID='"+CourseOrderID+"'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 获取订单
	 * @param sqlString
	 * @param pageNum
	 * @param rows
	 * @return
	 */
	public Collection<CourseOrder> getCourseOrder(String sqlString, String pageNum,String rows){
		String limit = super.limit(pageNum, rows);
		String sql = "select * from courseorder where DeletedFlag is null "+sqlString+" order by Created_TS desc "+limit+"";
		return super.executeQuery(sql);
	}
	
	/**
	 * 获取订单数量
	 * @param sqlString
	 * @return
	 */
	public int getOrderCount(String sqlString){
		String sql = "select count(*) from courseorder where DeletedFlag is null "+sqlString+"";
		return super.count(sql);
	}
	
	/**
	 * 新增订单
	 * @param rows
	 * @param pageNum
	 * @return
	 */
	public Collection<CourseOrder> getNewOrder(String rows,String pageNum){
		String limit = super.limit(pageNum, rows);
		String sql = "select * from courseOrder where DeletedFlag is null and State='1' "
				+ "order by Created_TS desc "+limit+"";
		return super.executeQuery(sql);
	}
	
	
	/**
	 * 新增订单的数量
	 * @return
	 */
	public int getNewOrderCount(){
		String sql = "select count(*) from courseorder where DeletedFlag is null and State='1'";
		return super.count(sql);
	}
	
	/**
	 * 新增订单未查阅的数量
	 * @return
	 */
	public int getNewOrderRealCount(){
		String sql = "select count(*) from courseorder where DeletedFlag is null and State='1' and IsRead='0' ";
		return super.count(sql);
	}
	
	/**
	 * 设置已读
	 * @param OrderID
	 * @return
	 */
	public boolean IsRead(String CourseOrderID){
		String sql = "update `courseOrder` set IsRead = '1', Updated_TS='"+Time()+"' where IsRead='0' and CourseOrderID='"+CourseOrderID+"'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 查看对应订单的信息
	 * @param CourseOrderID
	 * @return
	 */
	public CourseOrder getOrderByOrderID(String CourseOrderID){
		String sql = "select * from `courseorder` where DeletedFlag is null and CourseOrderID='"+CourseOrderID+"'";
		Collection<CourseOrder> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (CourseOrder) result.toArray()[0];
		}
		return null;
	}
	
	/**
	 * 查看该用户在该时段是否购买课程
	 * @param StartDateTime
	 * @param UserID
	 * @return
	 */
	public CourseOrder getByStartDateTime(String StartDateTime,String UserID){
		String sql = "select * from `courseorder` where DeletedFlag is null and StartDateTime='"+StartDateTime+"'"
				+ " and UserID='"+UserID+"' and State='3'";
		Collection<CourseOrder> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (CourseOrder) result.toArray()[0];
		}
		return null;
	}
	
	/**
	 * 手动取消订单
	 * @param OrderID
	 * @return
	 */
	public boolean cancel(String OrderID){
		String sql = "update `courseorder` set Activity = concat(Activity,'"+Time()+",取消订单|'),Updated_TS='"+Time()+"',"
				+ "State='0' where State!='3' and State!='0' and OrderID='"+OrderID+"'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 取消30分钟没有付款的订单
	 * @param dateTime
	 * @return
	 * @throws Exception
	 */
	public boolean cancelOrders(String dateTime)throws Exception{
		String sql = "update `courseorder` set `Activity` = concat(`Activity`,'"+Time()+",下单30分钟没有付款取消订单|'),"
				+ "Updated_TS='"+Time()+"',State='0' where State!='3' and State!='0' and Created_TS<'"+dateTime+"'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 修改订单为课程结束
	 * @param dateTime
	 * @return
	 * @throws Exception
	 */
	public boolean IsTaught(String dateTime)throws Exception{
		String sql = "update `courseorder` set `Activity` = concat(`Activity`,'"+Time()+",课程结束|'),"
				+ "Updated_TS='"+Time()+"',IsTaught='1' where Refund!='"+Refund_success+"' and State='3' and StartDateTime<'"+dateTime+"'";
		return super.executeUpdate(sql);
	}
	
	public Collection<CourseOrder> getIsTaughtCoach(){
		String sql = "select * from courseorder where DeletedFlag is null and IsTaught='1' and State='3' Group by CoachID";
		return super.executeQuery(sql);
	}
	
	/**
	 * 获取教练教过的学生人数
	 * @param CoachID
	 * @return
	 */
	public int getIsTaughtCount(String CoachID){
		String sql = "select count(*) from courseOrder where DeletedFlag is null and IsTaught='1' and State='3' and CoachID='"+CoachID+"' Group by UserID";
		return super.count(sql);
	}

	
	/**
	 * 申请退款
	 * @param OrderID
	 * @param description 退款理由
	 * @return
	 */
	public boolean applyRefund(String OrderID,String description){
		String sql = "update `courseOrder` set `Activity` = concat(`Activity`,'"+Time()+",申请退款|'), Refund ='"+Refund_apply
				+"',Description='"+description+"', IsRead='0', Updated_TS='"+Time()+"' where State='3' and OrderID='"+OrderID+"'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 退款结果
	 * @param result
	 * @param OrderID
	 * @return
	 */
	public boolean refundResult(String result,String OrderID){
		String sql = "update `courseOrder` set `Activity` = concat(`Activity`,'"+Time()+","+result+"|'), Refund ='"+result
				+"', Updated_TS='"+Time()+"' where Refund='"+Refund_apply+"' and OrderID='"+OrderID+"'";
		return super.executeUpdate(sql);
	}

}
