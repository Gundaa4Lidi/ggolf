package com.galaxy.ggolf.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import com.galaxy.ggolf.dao.CourseOrderDAO;
import com.galaxy.ggolf.domain.CourseOrder;
import com.mysql.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.dao.ClubOrderDAO;
import com.galaxy.ggolf.domain.ClubOrder;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.tools.PingPPUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.ChargeCollection;
import com.pingplusplus.model.ChargeRefundCollection;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.Refund;
import com.pingplusplus.model.Webhooks;

import jdk.nashorn.internal.parser.JSONParser;

//@Consumes("multipart/form-data")
@Produces("application/json")
@Path("/PingPP")
public class PingPPService extends BaseService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final ClubOrderDAO clubOrderDAO;

	private final CourseOrderDAO courseOrderDAO;

	public PingPPService(ClubOrderDAO clubOrderDAO,CourseOrderDAO courseOrderDAO) {
		this.clubOrderDAO = clubOrderDAO;
		this.courseOrderDAO = courseOrderDAO;
	}
	
	/**
	 * 创建球场ping++支付订单
	 * @param orderID
	 * @param channel
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/createCharge")
	public String createCharge(@FormParam("orderID") String orderID,
							   @FormParam("channel") String channel,
							   @Context HttpServletRequest request,
							   @Context HttpHeaders headers){
		try {
			ClubOrder clubOrder = this.clubOrderDAO.getOrderByOrderID(orderID);
			Charge charge = null;
			Map<String,Object> metadata = new HashMap<String,Object>();
			metadata.put("orderType", "club");
			String ip = getIp(request);
			if(clubOrder!=null){
				//开始创建ping++订单
				charge = PingPPUtil.createCharge(clubOrder.getOrderID(),
						clubOrder.getDownPayment(), channel, ip,
						clubOrder.getClubName(), clubOrder.getServiceExplain(), metadata);
				if(charge!=null){
					if(clubOrder.getState().equals("确认球位")){
						clubOrderDAO.onlineBooking(clubOrder.getOrderID());

					}
//					else{
//						return getErrormessage("请确认球位,再进行支付");
//					}
				}else {
					return getErrormessage("订单不存在或已取消");
				}
				return getResponse(charge);
			}
		} catch (Exception e){
			e.printStackTrace();
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}

	/**
	 * 创建教练课程ping++支付订单
	 * @param orderID
	 * @param channel
	 * @param request
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/createCourseCharge")
	public String createCourseCharge(@FormParam("orderID") String orderID,
									 @FormParam("channel") String channel,
									 @Context HttpServletRequest request,
									 @Context HttpHeaders headers){
		try {
			CourseOrder co = this.courseOrderDAO.getOrderByOrderID(orderID);
			Charge charge = null;
			Map<String,Object> metadata = new HashMap<String,Object>();
			metadata.put("orderType", "coachCourse");
			String time = this.courseOrderDAO.Time();
			String activity = "";
			String ip = getIp(request);
			if(co!=null){
				//开始创建ping++订单
				charge = PingPPUtil.createCharge(co.getCourseOrderID(),co.getDownPayment(),channel,ip,co.getCoachName(),
						co.getServiceExplain(),metadata);
				if(charge!=null){
					//修改订单状态为待付款
					if(co.getState().equals("1")){
						activity = time + ",待付款|";
						this.courseOrderDAO.updateOrderState(co.getCourseOrderID(), activity, "2", "1", null);
					}
				}
				return getResponse(charge);
			}else{
				return getErrormessage("订单不存在或已取消");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 查询 Charge
	 *
	 * @param ChargeID
	 */
	@GET
	@Path("/getCharge")
	public String getCharge(@FormParam("ChargeID") String ChargeID,
			@Context HttpHeaders headers){
		try {
			Charge charge = PingPPUtil.getCharge(ChargeID, null);
			return getResponse(charge);
		} catch (Exception e){
			e.printStackTrace();
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	/**
	 * 分页查询 Charge 或 加载查询Charge
	 * @param limit
	 * @param starting
	 * @param ending
	 * @param channel
	 * @param paid
	 * @param refunded
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/ChargeList")
	public String ChargeList(
			@FormParam("limit") String limit,
			@FormParam("starting") String starting,
			@FormParam("ending") String ending,
			@FormParam("channel") String channel,
			@FormParam("paid") String paid,
			@FormParam("refunded") String refunded,
			@Context HttpHeaders headers){
		try {
			ChargeCollection cc = PingPPUtil.ChargeList(limit, starting, ending, channel, paid, refunded);
			return getResponse(cc);
		} catch (Exception e){
			e.printStackTrace();
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 创建退款
	 * @param ChargeID
	 * @param amount
	 * @param description
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/Refund")
	public String Refund(
			@FormParam("ChargeID") String ChargeID,
			@FormParam("amount") String amount,
			@FormParam("description") String description,
			@Context HttpHeaders headers){
		try {
			Refund refund = PingPPUtil.refund(ChargeID, Integer.parseInt(amount), description, null);
			return getResponse(refund);
		} catch (Exception e){
			e.printStackTrace();
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	/**
	 * 查询退款
	 * @param ChargeID
	 * @param RefundID
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getRefund")
	public String getRefund(
			@FormParam("ChargeID") String ChargeID,
			@FormParam("RefundID") String RefundID,
			@Context HttpHeaders headers){
		try {
			Refund refund = PingPPUtil.getRefund(ChargeID, RefundID);
			return getResponse(refund);
		} catch (Exception e){
			e.printStackTrace();
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 分页查询退款 或 加载查询退款
	 * @param ChargeID
	 * @param limit
	 * @param starting
	 * @param ending
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/RefundList")
	public String RefundList(
			@FormParam("ChargeID") String ChargeID,
			@FormParam("limit") String limit,
			@FormParam("starting") String starting,
			@FormParam("ending") String ending,
			@Context HttpHeaders headers){
		try {
			ChargeRefundCollection refundList = PingPPUtil.RefundList(ChargeID, limit, starting, ending);
			return getResponse(refundList);
		} catch (Exception e){
			e.printStackTrace();
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * ping++成功回调后验证签名
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/Webhooks")
	public void Webhooks(String data,@Context HttpHeaders headers){
		try {
			logger.info("订单数据------{}",data);
			String Signature = headers.getHeaderString("X-Pingplusplus-Signature");
			logger.debug("Signature-------{}",Signature);
			boolean result = PingPPUtil.verifyWebhooks(data, Signature);
			logger.debug("验签结果：--------{}", (result ? "通过" : "失败"));
			if(result){
				Object event = Webhooks.getObject(data);
				if(event instanceof Charge){//支付
					Charge ch = (Charge) event;
					Map<String, Object> mMap = ch.getMetadata();
					if(mMap.containsKey("orderType")){//是否有订单类型
						String mVal = (String) mMap.get("orderType");
						if(mVal.equals("club")){//球场订单
							ClubOrderStatus(ch);
						}else if(mVal.equals("coachCourse")){//教练课程订单
							CourseOrderFinish(ch);
						}
					}
				}else if(event instanceof Refund){//退款
					Refund refund = (Refund) event;
				}
				
			}
		} catch (Exception e){
			e.printStackTrace();
			logger.error("Error occured",e);
		}
	}
	
	//球场订单完成
	private void ClubOrderStatus(Charge charge){
		try {
			if(!this.clubOrderDAO.finishBooking(charge.getOrderNo(),charge.getChannel(),charge.getId())){
					throw new GalaxyLabException("Error in finish booking");
			}
		} catch (GalaxyLabException e) {
			e.printStackTrace();
		}
	}

	//教练课程订单完成
	private void CourseOrderFinish(Charge ch){
		try {
			CourseOrder co = this.courseOrderDAO.getOrderByOrderID(ch.getOrderNo());
			String time = this.courseOrderDAO.Time();
			String activity = time + ",预约完成|";
			if(co.getIsBatch().equals("1")){
				activity = time + ",购买完成|";
			}
			if(this.courseOrderDAO.updateOrderState(co.getCourseOrderID(), activity, "3", "2", ch.getChannel())){
				throw new GalaxyLabException("Error in update Order state 3");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
