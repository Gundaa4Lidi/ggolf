package com.galaxy.ggolf.rest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.html.HTML;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.dao.ClubDAO;
import com.galaxy.ggolf.dao.ClubOrderDAO;
import com.galaxy.ggolf.dao.ClubServeDAO;
import com.galaxy.ggolf.domain.ClubOrder;
import com.galaxy.ggolf.domain.ClubServe;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.dto.ClubOrderData;
import com.galaxy.ggolf.jdbc.CommonConfig;
import com.galaxy.ggolf.tools.PingPPUtil;
import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.ChargeCollection;

//@Consumes("multipart/form-data")
@Produces("application/json")
@Path("/ClubOrder")
public class ClubOrderService extends BaseService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final String Cancel_order = "取消订单";	
	private static final String Submit_order = "提交订单"; 
	private static final String Confirm_ball = "确认球位"; 
	private static final String Online_booking = "在线预订";
	private static final String Finish_booking = "完成预订";
	private static final String Refund_apply = "退款申请";
	private static final String Refund_success = "退款成功";
	private static final String Refund_field = "退款失败";
	
	private ClubOrderDAO orderDAO;
	private ClubDAO clubDAO;
	private ClubServeDAO clubServeDAO;
	
	
	public ClubOrderService(ClubOrderDAO orderDAO,ClubDAO clubDAO,
			ClubServeDAO clubServeDAO) {
		this.orderDAO = orderDAO;
		this.clubDAO = clubDAO;
		this.clubServeDAO = clubServeDAO;
	}
	
	/**
	 * 根据搜索关键字获取订单
	 * @param days
	 * @param keyword
	 * @param rows
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getClubOrder{days}")
	public String getClubOrder(@PathParam("days") int days,
			@FormParam("keyword") String keyword,
			@FormParam("rows") String rows,
			@Context HttpHeaders headers){
		try {
			String sqlString = "";
			if(days > 0){
				DateTime time = DateTime.now().minusDays(days);
				sqlString += "and Created_TS > '"+time.toString("yyyy-MM-dd")+"' ";
			}
			if(keyword!=null && !keyword.equalsIgnoreCase("null")&&!keyword.equals("")){
				sqlString += "and (OrderID like '%"
						+ keyword
						+ "%' or ClubName like '%"
						+ keyword 
						+ "%' or ClubserveName like '%"
						+ keyword 
						+ "%' or State like '%"
						+ keyword 
						+ "%' or CreateTime like '%"
						+ keyword
						+ "%' or Type like '%"
						+ keyword
						+ "%' or DownPayment like '%"
						+ keyword
						+ "%' or StartDate like '%"
						+ keyword
						+ "%' or StartTime like '%"
						+ keyword
						+ "%' or Names like '%"
						+ keyword
						+ "%' or Tel like '%"
						+ keyword
						+ "%' or ServiceExplain like '%"
						+ keyword
						+ "%' or date_format(`Created_TS`,'%Y-%m-%d') like '%"
						+ keyword +"%') ";
			}
			int count = this.orderDAO.getOrderCount(sqlString);
			Collection<ClubOrder> clubOrders = this.orderDAO.getClubOrder(sqlString, rows);
			ClubOrderData clubOrderData = new ClubOrderData(count, clubOrders);
			return getResponse(clubOrderData);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
		
	}
	
	/**
	 * 获取对应用户的订单信息(分页)
	 * @param days
	 * @param StateType
	 * @param UserID (必填)
	 * @param pageNum (必填)
	 * @param rows (必填)
	 * @return
	 */
	@GET
	@Path("/getClubOrderByUserID{days}")
	public String getClubOrderByUserID(@PathParam("days") int days,
			@FormParam("StateType") String StateType,
			@FormParam("UserID") String UserID,
			@FormParam("pageNum") String pageNum,
			@FormParam("rows") String rows){
		try {
			String sqlString = "";
			if(days > 0){
				DateTime time = DateTime.now().minusDays(days);
				sqlString += "and Created_TS > '"+time.toString("yyyy-MM-dd")+"' ";
			}
			if(StateType!=null){
				String state = "";
				if(StateType.equals("0")){
					state = Cancel_order;
				}else if(StateType.equals("1")){
					state = Submit_order;
				}else if(StateType.equals("2")){
					state = Confirm_ball;
				}else if(StateType.equals("3")){
					state = Online_booking;
				}else if(StateType.equals("4")){
					state= Finish_booking;
				}
				sqlString += "and State='"+state+"' ";
			}
			int count = this.orderDAO.getCountByUserID(UserID, sqlString);
			Collection<ClubOrder> clubOrders = this.orderDAO.getOrderByUserID(UserID, pageNum, rows, sqlString);
			ClubOrderData clubOrderData = new ClubOrderData(count, clubOrders);
			return getResponse(clubOrderData);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	
	/**
	 * 提交订单
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/createOrder")
	public String createOrder(String data,@Context HttpHeaders headers){
		try {
			logger.info("订单数据------{}",data);
			ClubOrder order = super.fromGson(data, ClubOrder.class);
			if(order.getClubserveID()!=null){
				ClubServe cs = this.clubServeDAO.getClubServe(order.getClubserveID());
				order.setClubID(cs.getClubID());
				order.setClubName(cs.getClubName());
				order.setClubserveName(cs.getName());
				if(!this.orderDAO.createOrder(order)){
					throw new GalaxyLabException("Error in create cluborder");
				}
//				String channel = "alipay";
//				if(order.getType().equals("支付宝支付")){
//					channel = "alipay";
//				}else if(order.getType().equals("微信支付")){
//					channel = "wx";
//				}
//				String clientIp = "127.0.0.1";
//				Charge c = sendPingppOrder(order.getOrderID(), order.getDownPayment(), channel, clientIp, "1", "12544", "13202845411");
//				return getResponse(c);
				return getSuccessResponse();
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 变更订单的状态
	 * @param StateType
	 * @param OrderID
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/updateOrderState")
	public String updateState(@FormParam("StateType") String StateType,
			@FormParam("OrderID") String OrderID,
			@Context HttpHeaders headers){
		try {
			if(StateType.equals("0")){
				if(!this.orderDAO.cancel(OrderID)){
					throw new GalaxyLabException("Error in cancel Order");
				}
			}
			if(StateType.equals("2")){
				if(!this.orderDAO.confirmBall(OrderID)){
					throw new GalaxyLabException("Error in confirm for ball");
				}
			}
//			if(StateType.equals("3")){
//				if(!this.orderDAO.onlineBooking(OrderID)){
//					throw new GalaxyLabException("Error in online booking ");
//				}
//			}
//			if(StateType.equals("4")){
//				if(!this.orderDAO.finishBooking(OrderID,ch.getChannel())){
//					throw new GalaxyLabException("Error in finish booking");
//				}
//			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}

	
	
	
	public Charge sendPingppOrder(String orderID,
			String price,
			String channel,
			String clientIp,
			String subject,
			String body,
			String phoneNum){
		try {
			Pingpp.apiKey = CommonConfig.PingPP_Apikey;
			String path = "D:\\workspace\\GGolfz\\ggolf\\res\\my_rsa_private_key.pem";
			Pingpp.privateKeyPath = path;
			double orderPrice = Double.parseDouble(price) * 100;
			Map<String, Object> chargeParams = new HashMap<String, Object>();
			chargeParams.put("order_no",  orderID);
//			chargeParams.put("Authorization",CommonConfig.PingPP_Apikey);
			chargeParams.put("amount", orderPrice);
			Map<String, String> app = new HashMap<String, String>();
			app.put("id", CommonConfig.PingPP_AppID);
			chargeParams.put("app", app);
			chargeParams.put("channel",  channel);
			chargeParams.put("currency", "cny");
			chargeParams.put("client_ip",  clientIp);
			chargeParams.put("subject",  subject);
			chargeParams.put("body",  body);
			Map<String, String> initialMetadata = new HashMap<String, String>();
			initialMetadata.put("color", "red");
			initialMetadata.put("phone_number", phoneNum);
			chargeParams.put("metadata", initialMetadata);
			return Charge.create(chargeParams);
		} catch (Exception e) {
			logger.error("Error occured",e);
			return null;
		}
	}
}
