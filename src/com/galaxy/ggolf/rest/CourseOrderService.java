package com.galaxy.ggolf.rest;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.dao.CoachCourseDAO;
import com.galaxy.ggolf.dao.CoachScoreDAO;
import com.galaxy.ggolf.dao.CourseOrderDAO;
import com.galaxy.ggolf.dao.UserDAO;
import com.galaxy.ggolf.domain.ClubOrder;
import com.galaxy.ggolf.domain.CoachCourse;
import com.galaxy.ggolf.domain.CourseOrder;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.domain.User;
import com.galaxy.ggolf.dto.CourseOrderData;
import com.galaxy.ggolf.dto.GenericData;
import com.galaxy.ggolf.dto.NewNotifyData;

//@Consumes("multipart/form-data")
@Produces("application/json")
@Path("/CourseOrder")
public class CourseOrderService extends BaseService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final String Refund_apply = "退款申请";
	private static final String Refund_applying = "退款处理中";
	private static final String Refund_success = "退款成功";
	private static final String Refund_field = "退款失败";
	
	private final CourseOrderDAO courseOrderDAO;
	private final CoachCourseDAO coachCourseDAO;
	private final CoachScoreDAO coachScoreDAO;
	private final UserDAO userDAO;
	
	public CourseOrderService(CourseOrderDAO courseOrderDAO,
			 CoachCourseDAO coachCourseDAO,
			 CoachScoreDAO coachScoreDAO,
			 UserDAO userDAO) {
		this.courseOrderDAO = courseOrderDAO;
		this.coachCourseDAO = coachCourseDAO;
		this.coachScoreDAO = coachScoreDAO;
		this.userDAO = userDAO;
	}
	
//	@POST
//	@Path("/test")
//	public String test(String data,
//			@FormParam("test") String test,
//			@Context HttpHeaders headers){
//		try {
//			
//		} catch (Exception e) {
//			logger.error("Error occured",e);
//		}
//		return getErrorResponse();
//	}
	
	/**
	 * 创建课程订单
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/createCourseOrder")
	public String createCourseOrder(String data,
			@Context HttpHeaders headers){
		try {
			CourseOrder co = super.fromGson(data, CourseOrder.class);
			String sqlString = "";
			if(co.getStartDateTime()!=null&&co.getUserID()!=null&&co.getCourseID()!=null){
				CoachCourse coachCourse = this.coachCourseDAO.getByCourseID(co.getCourseID());
				if(coachCourse!=null){
					sqlString = "and CourseID='"+coachCourse.getCourseID()+"' and StartDateTime='"+co.getStartDateTime()+"' and State='3' ";
					Collection<CourseOrder> orders = this.courseOrderDAO.getCourseOrder(sqlString, null, null);
					int maxPeople = Integer.parseInt(coachCourse.getMaxPeople());
					int realCount = orders.size();
					if(realCount == maxPeople){
						return getErrormessage("当前时段已满人数");
					}else if(realCount < maxPeople){
						sqlString = "and CourseID='"+coachCourse.getCourseID()+"' and StartDateTime='"+co.getStartDateTime()+"' and State!='0' ";
						orders = this.courseOrderDAO.getCourseOrder(sqlString, null, null);
						realCount = orders.size();
						if(realCount == maxPeople){
							return getErrormessage("当前下单人数过多,请稍候再试");
						}
					}
				}
				CourseOrder courseOrder = this.courseOrderDAO.getByStartDateTime(co.getStartDateTime(), co.getUserID());
				if(courseOrder!=null){
					return getErrormessage("已购买该时段的课程,无需再重新购买");
				}else{
//					String orderID = courseOrderDAO.getOrderID()+"";
					String prefix = "COD";
					String CourseOrderID = this.courseOrderDAO.getID(prefix, 12);
					co.setCourseOrderID(CourseOrderID);
					if(!this.courseOrderDAO.create(co)){
						throw new GalaxyLabException("Error in create course order");
					}
				}
				
			}
			
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 修改该课程的订单状态
	 * @param CourseOrderID
	 * @param StateType
	 * @param PayType
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/updateOrderState")
	public String updateOrderState(@FormParam("CourseOrderID") String CourseOrderID,
			@FormParam("StateType") String StateType,
			@FormParam("PayType") String PayType,
			@Context HttpHeaders headers){
		try {
			CourseOrder co = this.courseOrderDAO.getOrderByOrderID(CourseOrderID); 
			String time = this.courseOrderDAO.Time();
			String activity = "";
			if(co!=null){
				if(StateType.equals("2")){
					activity = time + ",待付款|";
					if(this.courseOrderDAO.updateOrderState(CourseOrderID, activity, "2", "1", null)){
						throw new GalaxyLabException("Error in update Order state 2");
					}
				}
				if(StateType.equals("3")){
					activity = time + ",预约完成|";
					if(co.getIsBatch().equals("1")){
						activity = time + ",购买完成|";
					}
					if(this.courseOrderDAO.updateOrderState(CourseOrderID, activity, "3", "2", PayType)){
						throw new GalaxyLabException("Error in update Order state 3");
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 获取课程订单
	 * @param keyword 搜索关键字(非必要)
	 * @param pageNum 实现分页(非必要)
	 * @param rows 显示条数(非必要,不填,默认加载全部)
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/GETCourseOrder")
	public String GETCourseOrder(@FormParam("keyword") String keyword,
			@FormParam("pageNum") String pageNum,
			@FormParam("rows") String rows,
			@Context HttpHeaders headers){
		try {
			String sqlString = search(keyword);
			Collection<CourseOrder> orders = this.courseOrderDAO.getCourseOrder(sqlString, pageNum, rows);
			int count = this.courseOrderDAO.getOrderCount(sqlString);
			CourseOrderData courseOrderData = new CourseOrderData(count, orders);
			return getResponse(courseOrderData);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 获取对应用户下的订单
	 * @param keyword 搜索关键字(非必要)
	 * @param pageNum 实现分页(非必要)
	 * @param rows 显示条数(非必要,不填,默认加载全部)
	 * @param UserID 用户编号(必要)
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getCourseOrderByUserID")
	public String getCourseOrderByUserID(@FormParam("keyword") String keyword,
			@FormParam("pageNum") String pageNum,
			@FormParam("rows") String rows,
			@FormParam("UserID") String UserID,
			@Context HttpHeaders headers){
		try {
			if(UserID!=null&&!UserID.equals("")&&!UserID.equalsIgnoreCase("null")){
				String sqlString = search(keyword);
				sqlString += "and UserID='"+UserID+"'";
				Collection<CourseOrder> orders = this.courseOrderDAO.getCourseOrder(sqlString, pageNum, rows);
				int count = this.courseOrderDAO.getOrderCount(sqlString);
				CourseOrderData courseOrderData = new CourseOrderData(count, orders);
				return getResponse(courseOrderData);
			}else{
				return getErrormessage("请填入用户编号");
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 获取教练接收的订单
	 * @param keyword 搜索关键字(非必要)
	 * @param pageNum 实现分页(非必要)
	 * @param rows 显示条数(非必要,不填,默认加载全部)
	 * @param CoachID 教练编号(必要)
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getCourseOrderByCoachID")
	public String getCourseOrderByCoachID(@FormParam("keyword") String keyword,
			@FormParam("pageNum") String pageNum,
			@FormParam("rows") String rows,
			@FormParam("CoachID") String CoachID,
			@Context HttpHeaders headers){
		try {
			if(CoachID!=null&&!CoachID.equals("")&&!CoachID.equalsIgnoreCase("null")){
				String sqlString = search(keyword);
				sqlString += "and CoachID='"+CoachID+"'";
				Collection<CourseOrder> orders = this.courseOrderDAO.getCourseOrder(sqlString, pageNum, rows);
				int count = this.courseOrderDAO.getOrderCount(sqlString);
				CourseOrderData courseOrderData = new CourseOrderData(count, orders);
				return getResponse(courseOrderData);
			}else{
				return getErrormessage("请填入教练编号");
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	public String search(String keyword){
		String sqlString = "";
		if(keyword!=null&&!keyword.equals("")&&!keyword.equalsIgnoreCase("null")){
			sqlString = "and (CourseOrderID like '%"
					+ keyword
					+ "%' or CoachName like '%"
					+ keyword 
					+ "%' or CourseTitle like '%"
					+ keyword 
					+ "%' or State like '%"
					+ keyword 
					+ "%' or Type like '%"
					+ keyword
					+ "%' or TeachingMethod like '%"
					+ keyword
					+ "%' or DownPayment like '%"
					+ keyword
					+ "%' or date_format(`StartDateTime`,'%Y-%m-%d') like '%"
					+ keyword
					+ "%' or Tel like '%"
					+ keyword
					+ "%' or ServiceExplain like '%"
					+ keyword
					+ "%' or date_format(`Created_TS`,'%Y-%m-%d') like '%"
					+ keyword +"%') ";
		}
		return sqlString;
	}
	
	
	/**
	 * 获取新增订单
	 * @param pageNum
	 * @param rows
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getNewOrder")
	public String getNewOrder(@FormParam("pageNum") String pageNum,
			@FormParam("rows") String rows,
			@Context HttpHeaders headers){
		try {
			Collection<CourseOrder> courseOrder = this.courseOrderDAO.getNewOrder(rows, pageNum);
			int count = this.courseOrderDAO.getNewOrderCount();
			int realCount = this.courseOrderDAO.getNewOrderRealCount();
			Collection<NewNotifyData> data = new ArrayList<NewNotifyData>();
			if(courseOrder.size()>0){
				for(CourseOrder co : courseOrder){
					User user = this.userDAO.getUserByUserID(co.getUserID());
					if(user!=null){
						NewNotifyData od = new NewNotifyData(user.getUserID(),user.getName(),
								user.getHead_portrait(),"course",co.getCourseOrderID(),co.getIsRead(),co.getCreated_TS());
						data.add(od);
					}
				}
			}
			GenericData<NewNotifyData> result = new GenericData<NewNotifyData>(count, realCount, data);
			return getResponse(result);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 获取新增但未查阅订单的数量
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getNewOrderCount")
	public String getNewOrderCount(
			@Context HttpHeaders headers){
		try {
			String sqlString = "and State='1' ";
			int realCount = this.courseOrderDAO.getOrderCount(sqlString);
			return getResponse(realCount);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 设置订单已查阅
	 * @param OrderID
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/IsRead")
	public String IsRead(@FormParam("OrderID") String OrderID,
			@Context HttpHeaders headers){
		try {
			this.courseOrderDAO.IsRead(OrderID);
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 获取对应课程订单信息
	 * @param OrderID
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getByCourseOrderID")
	public String getByCourseOrderID(@FormParam("OrderID") String OrderID,
			@Context HttpHeaders headers){
		try {
			CourseOrder order = this.courseOrderDAO.getOrderByOrderID(OrderID);
			return getResponse(order);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 申请退款
	 * @param OrderID
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/applyRefund")
	public String applyRefund(@FormParam("OrderID") String OrderID,
			@FormParam("Description") String description,
			@Context HttpHeaders headers){
		try {
			CourseOrder co = this.courseOrderDAO.getOrderByOrderID(OrderID);
			if(co!=null&&co.getState().equals("3")){
				if(!this.courseOrderDAO.applyRefund(OrderID,description)){
					return getErrormessage("申请退款失败");
				}
				return getSuccessResponse();
			}else{
				return getErrormessage("订单不存在或未完成支付!");
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 拒绝申请退款
	 * @param OrderID
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/rejectRefund")
	public String rejectRefund(@FormParam("OrderID") String OrderID,
			@Context HttpHeaders headers){
		try {
			if(this.courseOrderDAO.refundResult(Refund_field, OrderID)){
				return getSuccessResponse();
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
}
