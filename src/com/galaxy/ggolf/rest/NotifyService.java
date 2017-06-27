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

import com.galaxy.ggolf.dao.ClubOrderDAO;
import com.galaxy.ggolf.dao.CoachCourseDAO;
import com.galaxy.ggolf.dao.CoachDAO;
import com.galaxy.ggolf.dao.CourseOrderDAO;
import com.galaxy.ggolf.dao.UserDAO;
import com.galaxy.ggolf.domain.ClubOrder;
import com.galaxy.ggolf.domain.User;
import com.galaxy.ggolf.dto.GenericData;
import com.galaxy.ggolf.dto.NewOrderData;

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
	@Path("/getNewOrder")
	public String getNewOrder(@FormParam("pageNum") String pageNum,
			@FormParam("rows") String rows,
			@Context HttpHeaders headers){
		try {
			Collection<ClubOrder> clubOrder = this.clubOrderDAO.getNewOrder(rows, pageNum);
			int count = this.clubOrderDAO.getNewOrderCount();
			int realCount = this.clubOrderDAO.getNewOrderRealCount();
			Collection<NewOrderData> data = new ArrayList<NewOrderData>();
			if(clubOrder.size()>0){
				for(ClubOrder co : clubOrder){
					User user = this.userDAO.getUserByUserID(co.getUserID());
					if(user!=null){
						NewOrderData od = new NewOrderData(user.getUserID(),user.getName(),
								user.getHead_portrait(),"club",co.getOrderID(),co.getIsRead(),co.getCreated_TS());
						data.add(od);
					}
				}
			}
			GenericData<NewOrderData> result = new GenericData<NewOrderData>(count, realCount, data);
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
			int realCount = this.clubOrderDAO.getNewOrderRealCount();
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
			this.clubOrderDAO.IsRead(OrderID);
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
}
