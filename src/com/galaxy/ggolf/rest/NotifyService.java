package com.galaxy.ggolf.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.dao.ClubOrderDAO;
import com.galaxy.ggolf.dao.CoachCourseDAO;
import com.galaxy.ggolf.dao.CoachDAO;
import com.galaxy.ggolf.dao.CourseOrderDAO;
import com.galaxy.ggolf.dao.UserDAO;
import com.galaxy.ggolf.domain.ClubOrder;
import com.galaxy.ggolf.domain.Coach;
import com.galaxy.ggolf.domain.CoachCourse;
import com.galaxy.ggolf.domain.CourseOrder;
import com.galaxy.ggolf.domain.User;
import com.galaxy.ggolf.dto.GenericData;
import com.galaxy.ggolf.dto.NewNotifyData;

//@Consumes("multipart/form-data")
@Produces("application/json")
@Path("/Notify")
public class NotifyService extends BaseService{

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final UserDAO userDAO;
	private final ClubOrderDAO clubOrderDAO;
	private final CourseOrderDAO courseOrderDAO;
	private final CoachDAO coachDAO;
	private final CoachCourseDAO coachCourseDAO;
	
	private final static String AddClubOrder = "AddClubOrder";
	private final static String RefundClubOrder = "RefundClubOrder";
	private final static String AddCourseOrder = "AddCourseOrder";
	private final static String RefundCourseOrder = "RefundCourseOrder";
	private final static String ApplyCoach = "ApplyCoach";
	private final static String ApplyCourse = "ApplyCourse";
	
	
	public NotifyService(UserDAO userDAO,
			ClubOrderDAO clubOrderDAO,
			CourseOrderDAO courseOrderDAO,
			CoachDAO coachDAO,
			CoachCourseDAO coachCourseDAO) {
		this.userDAO = userDAO;
		this.clubOrderDAO = clubOrderDAO;
		this.courseOrderDAO = courseOrderDAO;
		this.coachDAO = coachDAO;
		this.coachCourseDAO = coachCourseDAO;
	}
	
	
	/**
	 * 获取新增订单
	 * @param pageNum
	 * @param rows
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getNotify")
	public String getNotify(@Context HttpHeaders headers){
		try {
			int count = 0;
			int realCount = 0;
			Collection<NewNotifyData> data = new ArrayList<NewNotifyData>();
			Collection<NewNotifyData> No1 = addClubOrderNotify();
			Collection<NewNotifyData> No2 = refundClubOrderNotify();
			Collection<NewNotifyData> No3 = addCourseOrderNotify();
			Collection<NewNotifyData> No4 = refundCourseOrderNotify();
			Collection<NewNotifyData> No5 = applyCoachNotify();
			Collection<NewNotifyData> No6 = applyCourseNotify();
			if(No1.size()>0){
				data.addAll(No1);
			}
			if(No2.size()>0){
				data.addAll(No2);
			}
			if(No3.size()>0){
				data.addAll(No3);
			}
			if(No4.size()>0){
				data.addAll(No4);
			}
			if(No5.size()>0){
				data.addAll(No5);
			}
			if(No6.size()>0){
				data.addAll(No6);
			}
			if(data.size()>0){
				count = getCount();
				realCount = getRealCount();
				data = sortForTime(data);
			}
			GenericData<NewNotifyData> result = new GenericData<NewNotifyData>(count, realCount, data);
			return getResponse(result);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 获取新增通知但未查阅的数量
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/RealCount")
	public String RealCount(
			@Context HttpHeaders headers){
		try {
			return getResponse(getRealCount());
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 获取通知总数
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/GetCount")
	public String GetCount(@Context HttpHeaders headers){
		try {
			return getResponse(getCount());
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
	public String IsRead(@FormParam("ThemeID") String ThemeID,
			@FormParam("Type") String Type,
			@Context HttpHeaders headers){
		try {
			if(Type.equalsIgnoreCase(AddClubOrder)||Type.equalsIgnoreCase(RefundClubOrder)){
				this.clubOrderDAO.IsRead(ThemeID);
			}
			if(Type.equalsIgnoreCase(AddCourseOrder)||Type.equalsIgnoreCase(RefundCourseOrder)){
				this.courseOrderDAO.IsRead(ThemeID);
			}
			if(Type.equalsIgnoreCase(ApplyCoach)){
				this.coachDAO.IsRead(ThemeID);
			}
			if(Type.equalsIgnoreCase(ApplyCourse)){
				this.coachCourseDAO.IsRead(ThemeID);
			}
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 按日期排序
	 * @param notifyList
	 * @return
	 * @throws Exception
	 */
	private Collection<NewNotifyData> sortForTime(Collection<NewNotifyData> notifyList)throws Exception{
		ArrayList<NewNotifyData> nnd = new ArrayList<NewNotifyData>();
		if(notifyList.size()>0){
			nnd = (ArrayList<NewNotifyData>) notifyList;
			long bt = System.currentTimeMillis();//开始排序
			Collections.sort(nnd,new Comparator<NewNotifyData>() {

				@Override
				public int compare(NewNotifyData o1, NewNotifyData o2) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date d1 = new Date();
					Date d2 = new Date();
					try {
						d1 = sdf.parse(o1.getCreated_TS());
						d2 = sdf.parse(o2.getCreated_TS());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if(d1.getTime() < d2.getTime()){
						return 1;
					}else if(d1.getTime() > d2.getTime()){
						return -1;
					}
					return 0;
				}
				
			});
			long et = System.currentTimeMillis();//结束排序
			logger.info("时段比较耗时----{}毫秒",((bt - et)));
		}
		return nnd;
	}
	
	/**
	 * 通知的总数
	 * @return
	 */
	private int getCount(){
		String sqlString = "and Verify='0' ";
		int C1 = this.clubOrderDAO.getNewOrderCount();
		int C2 = this.clubOrderDAO.getRefundOrderCount();
		int C3 = this.courseOrderDAO.getNewOrderCount();
		int C4 = this.courseOrderDAO.getRefundOrderCount();
		int C5 = this.coachDAO.getAllCount(sqlString);
		int C6 = this.coachCourseDAO.getCourseCount(sqlString);
		int Count = C1 + C2 + C3 + C4 + C5 + C6;
		return Count;
	}
	
	/**
	 * 通知未查阅的数量
	 * @return
	 */
	private int getRealCount(){
		int C1 = this.clubOrderDAO.getNewOrderRealCount();
		int C2 = this.clubOrderDAO.getRefundOrderRealCount();
		int C3 = this.courseOrderDAO.getNewOrderRealCount();
		int C4 = this.courseOrderDAO.getRefundOrderRealCount();
		int C5 = this.coachDAO.getRealCount();
		int C6 = this.coachCourseDAO.getRealCount();
		int realCount = C1 + C2 + C3 + C4 + C5 + C6;
		return realCount;
	}
	
	/**
	 * 新增球场订单通知
	 * @return
	 * @throws Exception
	 */
	private Collection<NewNotifyData> addClubOrderNotify()throws Exception{
		Collection<ClubOrder> clubOrders = this.clubOrderDAO.getNewOrder(null, null);
		Collection<NewNotifyData> result = new ArrayList<NewNotifyData>();
		if(clubOrders.size()>0){
			for(ClubOrder co : clubOrders){
				User user = this.userDAO.getUserByUserID(co.getUserID());
				if(user!=null){
					NewNotifyData od = new NewNotifyData(user.getUserID(),user.getName(),
							user.getHead_portrait(),AddClubOrder,co.getOrderID(),co.getIsRead(),co.getCreated_TS());
					result.add(od);
				}
			}
		}
		return result;
	}
	
	/**
	 * 球场订单退款通知
	 * @return
	 * @throws Exception
	 */
	private Collection<NewNotifyData> refundClubOrderNotify()throws Exception{
		Collection<ClubOrder> clubOrders = this.clubOrderDAO.getRefundOrder(null, null);
		Collection<NewNotifyData> result = new ArrayList<NewNotifyData>();
		if(clubOrders.size()>0){
			for(ClubOrder co : clubOrders){
				User user = this.userDAO.getUserByUserID(co.getUserID());
				if(user!=null){
					NewNotifyData od = new NewNotifyData(user.getUserID(),user.getName(),
							user.getHead_portrait(),RefundClubOrder,co.getOrderID(),co.getIsRead(),co.getUpdated_TS());
					result.add(od);
				}
			}
		}
		return result;
	}
	
	/**
	 * 新增课程订单通知
	 * @return
	 * @throws Exception
	 */
	private Collection<NewNotifyData> addCourseOrderNotify()throws Exception{
		Collection<CourseOrder> courseOrders = this.courseOrderDAO.getNewOrder(null, null);
		Collection<NewNotifyData> result = new ArrayList<NewNotifyData>();
		if(courseOrders.size()>0){
			for(CourseOrder co : courseOrders){
				User user = this.userDAO.getUserByUserID(co.getUserID());
				if(user!=null){
					NewNotifyData od = new NewNotifyData(user.getUserID(),user.getName(),
							user.getHead_portrait(),AddCourseOrder,co.getCourseOrderID(),co.getIsRead(),co.getCreated_TS());
					result.add(od);
				}
			}
		}
		return result;
	}
	
	/**
	 * 课程订单通知
	 * @return
	 * @throws Exception
	 */
	private Collection<NewNotifyData> refundCourseOrderNotify()throws Exception{
		Collection<CourseOrder> courseOrders = this.courseOrderDAO.getRefundOrder(null, null);
		Collection<NewNotifyData> result = new ArrayList<NewNotifyData>();
		if(courseOrders.size()>0){
			for(CourseOrder co : courseOrders){
				User user = this.userDAO.getUserByUserID(co.getUserID());
				if(user!=null){
					NewNotifyData od = new NewNotifyData(user.getUserID(),user.getName(),
							user.getHead_portrait(),RefundCourseOrder,co.getCourseOrderID(),co.getIsRead(),co.getCreated_TS());
					result.add(od);
				}
			}
		}
		return result;
	}
	
	/**
	 * 用户申请教练通知
	 * @return
	 * @throws Exception
	 */
	private Collection<NewNotifyData> applyCoachNotify()throws Exception{
		String sqlString = "and Verify='0' ";
		Collection<Coach> coachs = this.coachDAO.getAll(null, sqlString);
		Collection<NewNotifyData> result = new ArrayList<NewNotifyData>();
		if(coachs.size()>0){
			for(Coach c : coachs){
				NewNotifyData od = new NewNotifyData(c.getCoachID(),c.getUserName(),c.getCoachHead(),ApplyCoach,c.getCoachID(),c.getIsRead(),c.getCreated_TS());
				result.add(od);
			}
		}
		return result;
	}
	
	/**
	 * 教练申请课程通知
	 * @return
	 * @throws Exception
	 */
	private Collection<NewNotifyData> applyCourseNotify()throws Exception{
		String sqlString = "and Verify='0' ";
		Collection<CoachCourse> courses = this.coachCourseDAO.getCourse(sqlString, null, null);
		Collection<NewNotifyData> result = new ArrayList<NewNotifyData>();
		if(courses.size()>0){
			for(CoachCourse cc : courses){
				Coach c = this.coachDAO.getCoachByCoachID(cc.getCoachID(), null);
				if(c!=null){
					NewNotifyData od = new NewNotifyData(c.getCoachID(),c.getCoachName(),c.getCoachHead(),ApplyCourse,cc.getCourseID(),cc.getIsRead(),cc.getCreated_TS());
					result.add(od);
				}
			}
		}
		return result;
	}
}
