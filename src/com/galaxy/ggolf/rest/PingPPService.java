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

import com.galaxy.ggolf.dao.*;
import com.galaxy.ggolf.domain.*;
import com.mysql.jdbc.StringUtils;
import com.pingplusplus.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.dto.PushOption;
import com.galaxy.ggolf.jdbc.CommonConfig;
import com.galaxy.ggolf.push.PushManager;
import com.galaxy.ggolf.push.PushUtil;
import com.galaxy.ggolf.tools.PingPPUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


//@Consumes("multipart/form-data")
@Produces("application/json")
@Path("/PingPP")
public class PingPPService extends BaseService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String OrderType = "orderType";
	private static final String OrderType_Club = "club";
	private static final String OrderType_CoachCourse = "coachCourse";
	private static final String OrderType_RechargeWallet = "rechargeWallet";
	private static final String OrderType_WithdrawalsWallet = "withdrawalsWallet";
	private static final String Refund_Status_Pending = "pending";
	private static final String Refund_Status_Success = "succeeded";
	private static final String Refund_Status_Failed = "failed";
	private static final String Refund_pending = "退款处理中";
	private static final String Refund_success = "退款成功";
	private static final String Refund_field = "退款失败";
	//企业付款(提现)回调状态
	private static final String Transfer_status_pending = "pending";//处理中
	private static final String Transfer_status_paid = "paid";//付款成功
	private static final String Transfer_status_failed = "failed";//付款失败
	private static final String Transfer_status_scheduled = "scheduled";//待发送



	private final ClubOrderDAO clubOrderDAO;

	private final CourseOrderDAO courseOrderDAO;
	
	private final UmengDAO umengDAO;

	private final WalletDAO walletDAO;

	private final WalletLogDAO walletLogDAO;

	private final WalletRecordDAO walletRecordDAO;

	public PingPPService(ClubOrderDAO clubOrderDAO,CourseOrderDAO courseOrderDAO,
			UmengDAO umengDAO,WalletDAO walletDAO,
		 	WalletLogDAO walletLogDAO,
		 	WalletRecordDAO walletRecordDAO) {
		this.clubOrderDAO = clubOrderDAO;
		this.courseOrderDAO = courseOrderDAO;
		this.umengDAO = umengDAO;
		this.walletDAO = walletDAO;
		this.walletLogDAO = walletLogDAO;
		this.walletRecordDAO = walletRecordDAO;
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
			metadata.put(OrderType, OrderType_Club);
//			String ip = "127.0.0.1";
			String ip = super.getIp(request);
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
			metadata.put(OrderType, OrderType_CoachCourse);
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
			@FormParam("orderType") String orderType,
			@Context HttpHeaders headers){
		try {
			Map<String,Object> metadata = new HashMap<String,Object>();
			metadata.put(OrderType, orderType);
			Refund refund = PingPPUtil.refund(ChargeID, Integer.parseInt(amount), description, metadata);
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
	 * 钱包充值
	 * @param recordSn 流水账编号
	 * @param channel  支付方式
	 * @param request
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/rechargeWallet")
	public String rechargeWallet(@FormParam("recordSn") String recordSn,
								 @FormParam("channel") String channel,
								 @Context HttpServletRequest request,
								 @Context HttpHeaders headers){
		try {
			WalletRecord wr = this.walletRecordDAO.getByRecordSn(recordSn);
			Charge charge = null;
			Map<String,Object> metadata = new HashMap<String,Object>();
			metadata.put(OrderType, OrderType_RechargeWallet);
//			String ip = "127.0.0.1";
			String ip = super.getIp(request);
			if(wr!=null){
				//开始创建ping++订单
				charge = PingPPUtil.createCharge(wr.getRecordSn(),
						wr.getMoney(), channel, ip,
						wr.getRemark(), wr.getRemark(), metadata);
				return getResponse(charge);
			}
		} catch (Exception e){
			e.printStackTrace();
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}

	/**
	 * 钱包提现
	 * @param recordSn
	 * @param channel
	 * @param request
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/withdrawalsWallet")
	public String withdrawalsWallet(@FormParam("recordSn") String recordSn,
									@FormParam("channel") String channel,
									@FormParam("recipient") String recipient,
									@Context HttpServletRequest request,
									@Context HttpHeaders headers){
		try {
			WalletRecord wr = this.walletRecordDAO.getByRecordSn(recordSn);
			Transfer transfer = null;
			Map<String,String> metadata = new HashMap<String,String>();
			metadata.put(OrderType, OrderType_WithdrawalsWallet);
			String description = "提现"+wr.getMoney()+"元";
			if(wr!=null){
				transfer = PingPPUtil.createTransfer(wr.getRecordSn(),
						channel, wr.getMoney(), recipient,
						description, metadata);
				return getResponse(transfer);
			}
		} catch (Exception e){
			e.printStackTrace();
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}

	/**
	 * 查询企业付款(提现)状态
	 * @param TransferID
	 * @param request
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/checkTransfer")
	public String checkTransfer(@FormParam("TransferID") String TransferID,
								@Context HttpServletRequest request,
								@Context HttpHeaders headers){
		try {
			Transfer transfer = PingPPUtil.GetTransfer(TransferID);
			return getResponse(transfer);
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
					if(mMap.containsKey(OrderType)){//是否有订单类型
						String mVal = (String) mMap.get(OrderType);
						if(mVal.equalsIgnoreCase(OrderType_Club)){//球场订单
							ClubOrderStatus(ch);
						}else if(mVal.equalsIgnoreCase(OrderType_CoachCourse)){//教练课程订单
							CourseOrderFinish(ch);
						}else if(mVal.equalsIgnoreCase(OrderType_RechargeWallet)){//充值订单
							WalletRecordStatus(ch);
						}
					}
				}else if(event instanceof Refund){//退款
					Refund refund = (Refund) event;
					Map<String, Object> reMap = refund.getMetadata();
					if(reMap.containsKey(OrderType)){
						String reVal = (String) reMap.get(OrderType);
						if(reVal.equalsIgnoreCase(OrderType_Club)){
							ClubOrderRefund(refund);//球场订单退款状态修改
						}else if(reVal.equalsIgnoreCase(OrderType_CoachCourse)){
							CourseOrderRefund(refund);//课程订单退款状态修改
						}
					}
				}else if(event instanceof Transfer){//企业付款(提现)
					Transfer transfer = (Transfer) event;
					Map<String,String> traMap = transfer.getMetadata();
					if(traMap.containsKey(OrderType)){
						String traVal = traMap.get(OrderType);
						if(traVal.equalsIgnoreCase(OrderType_WithdrawalsWallet)){
							WalletTransfer(transfer);//查看企业付款返回状态
						}
					}
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
//			logger.info("订单编号:{},支付渠道:{},ping++ID:{}",charge.getOrderNo(),charge.getChannel(),charge.getId());
			if(!this.clubOrderDAO.finishBooking(charge.getOrderNo(),charge.getChannel(),charge.getId())){
					throw new GalaxyLabException("Error in finish booking");
			}else{
				ClubOrder co = this.clubOrderDAO.getOrderByOrderID(charge.getOrderNo());
				Umeng umeng = this.umengDAO.getByUserID(co.getUserID());
				if(umeng!=null){
					PushManager.sendFinish_booking(umeng.getUmeng_Token());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//查看企业付款返回状态
	public String WalletTransfer(Transfer transfer){
		String result = "";
		String time = this.walletRecordDAO.Time();
		String payType = setPayType(transfer.getChannel());
		String sqlString = "";
		WalletRecord walletRecord = this.walletRecordDAO.getByRecordSn(transfer.getOrderNo());
		try {
			if(transfer.getStatus().equalsIgnoreCase(Transfer_status_pending)){
				result = "处理中";
			}else if(transfer.getStatus().equalsIgnoreCase(Transfer_status_paid)){
				result = "付款成功";
				if(walletRecord!=null&&walletRecord.getPayStatus().equals("0")){
					sqlString = "PayType='"+payType+"',PayStatus='1',PayTime='"+time+"',";
					if(this.walletRecordDAO.updateStatus(sqlString,walletRecord.getRecordSn())){

					}
				}
			}else if(transfer.getStatus().equalsIgnoreCase(Transfer_status_failed)){
				result = "付款失败";
				if(walletRecord!=null&&walletRecord.getPayStatus().equals("0")){
					sqlString = "PayType='"+payType+"',PayStatus='2',PayTime='"+time+"',";
					if(this.walletRecordDAO.updateStatus(sqlString,walletRecord.getRecordSn())){
						Wallet wallet = this.walletDAO.getByUserID(walletRecord.getFromUID(),null);
						int money = Integer.parseInt(wallet.getMoney()) + Integer.parseInt(walletRecord.getMoney());
						WalletLog wLog = new WalletLog();
						wLog.setRecordSn(walletRecord.getRecordSn());
						wLog.setUserID(walletRecord.getFromUID());
						wLog.setRemark("提现失败,返回¥"+walletRecord.getMoney());
						wLog.setChangeMoney("+"+walletRecord.getMoney());
						wLog.setMoney(money+"");
						this.walletLogDAO.create(wLog);
					}
				}

			}else if(transfer.getStatus().equalsIgnoreCase(Transfer_status_scheduled)){
				result = "待发送";
			}

		} catch (Exception e){
			e.printStackTrace();
		}
		return result;

	}

	//改变钱包支付的状态
	private void WalletRecordStatus(Charge charge){
		try {
			WalletRecord wr = this.walletRecordDAO.getByRecordSn(charge.getOrderNo());
			String time = this.walletRecordDAO.Time();
			if(wr.getFetchStatus().equals("0")){
				String payType = setPayType(charge.getChannel());
				String sqlString = "PayType='"+payType+"',FetchStatus='1',FetchTime='"+time+"',";
				if(this.walletRecordDAO.updateStatus(sqlString,wr.getRecordSn())){
					WalletRecord wRecord = this.walletRecordDAO.getByRecordSn(wr.getRecordSn());
					Wallet wallet = this.walletDAO.getByUserID(wRecord.getToUID(),null);
					WalletLog wLog = new WalletLog();
					if(wRecord.getFetchStatus().equals("1")){
						int money = Integer.parseInt(wRecord.getMoney()) + Integer.parseInt(wallet.getMoney());
						wLog.setRecordSn(wRecord.getRecordSn());
						wLog.setUserID(wallet.getUserID());
						wLog.setRemark(wRecord.getRemark());
						wLog.setChangeMoney("+"+wRecord.getMoney());
						wLog.setMoney(money+"");
						this.walletLogDAO.create(wLog);//生成日志
					}

				}
			}
		} catch (Exception e) {
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
//				throw new GalaxyLabException("Error in update Order state 3");
				PushOption po = new PushOption();
				PushOption po1 = new PushOption();
				String ticker = "课程订单";
				String title = ticker;
				String text = "你的课程订单已生成,编号为:"+co.getCourseOrderID();
				String text1 = "有一位学员订购你的"+co.getCourseTitle()+"课程,订单号为:"+co.getCourseOrderID()+",点击查看.";
				String type = CommonConfig.Send_Unicast;
				String after_open = PushManager.GoApp;
				String display_type = PushManager.Display_Type_Notify;
				Umeng um = this.umengDAO.getByUserID(co.getUserID());//学员
				Umeng um1 = this.umengDAO.getByUserID(co.getCoachID());//教练
				if(um!=null){
					po.setDevice_tokens(um.getUmeng_Token());
					po.setTicker(ticker);
					po.setTitle(title);
					po.setText(text);
					po.setAfter_open(after_open);
					po.setDisplay_type(display_type);
					po.setType(type);
					PushManager.sendUnicast(po);
				}
				if(um1!=null){
					po1.setDevice_tokens(um1.getUmeng_Token());
					po1.setTicker(ticker);
					po1.setTitle(title);
					po1.setText(text1);
					po1.setAfter_open(after_open);
					po1.setDisplay_type(display_type);
					po1.setType(type);
					PushManager.sendUnicast(po1);
				}

			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	//球场订单退款状态修改
	private void ClubOrderRefund(Refund re) throws Exception{
		String result = "";
		if(re.getStatus().equalsIgnoreCase(Refund_Status_Pending)){
			result = Refund_pending;
		}else if(re.getStatus().equalsIgnoreCase(Refund_Status_Success)){
			result = Refund_success;
		}else if(re.getStatus().equalsIgnoreCase(Refund_Status_Failed)){
			result = Refund_field;
		}
		if(!result.equals("")){
			if(!this.clubOrderDAO.refundResult(result, re.getChargeOrderNo())){
				throw new GalaxyLabException("Error in update refund");
			}else if(!result.equals(Refund_pending)){
				ClubOrder co = this.clubOrderDAO.getOrderByOrderID(re.getChargeOrderNo());
				Umeng um = this.umengDAO.getByUserID(co.getUserID());
				if(um!=null){
					String ticker = "退款结果";
					String title = ticker;
					String text = "你的球场订单"+re.getChargeOrderNo()+","+result+",若有疑问,请联系客服.";
					String type = CommonConfig.Send_Unicast;
					String after_open = PushManager.GoApp;
					String display_type = PushManager.Display_Type_Notify;
					PushOption po = new PushOption();
					po.setDevice_tokens(um.getUmeng_Token());
					po.setTicker(ticker);
					po.setTitle(title);
					po.setText(text);
					po.setAfter_open(after_open);
					po.setDisplay_type(display_type);
					po.setType(type);
					PushManager.sendUnicast(po);
				}

			}
		}
		
	}
	
	//课程订单退款状态修改
	private void CourseOrderRefund(Refund re) throws Exception{
		String result = "";
		if(re.getStatus().equalsIgnoreCase(Refund_Status_Pending)){
			result = Refund_pending;
		}else if(re.getStatus().equalsIgnoreCase(Refund_Status_Success)){
			result = Refund_success;
		}else if(re.getStatus().equalsIgnoreCase(Refund_Status_Failed)){
			result = Refund_field;
		}
		if(!result.equals("")){
			CourseOrder co = this.courseOrderDAO.getOrderByOrderID(re.getChargeOrderNo());
			if(!this.courseOrderDAO.refundResult(result, re.getChargeOrderNo())){
				throw new GalaxyLabException("Error in update refund");
			}else if(!result.equals(Refund_pending)){
				Umeng um = this.umengDAO.getByUserID(co.getUserID());
				if(um!=null){
					String ticker = "退款结果";
					String title = ticker;
					String text = "你的课程订单"+re.getChargeOrderNo()+","+result+",若有疑问,请联系客服.";
					String type = CommonConfig.Send_Unicast;
					String after_open = PushManager.GoApp;
					String display_type = PushManager.Display_Type_Notify;
					PushOption po = new PushOption();
					po.setDevice_tokens(um.getUmeng_Token());
					po.setTicker(ticker);
					po.setTitle(title);
					po.setText(text);
					po.setAfter_open(after_open);
					po.setDisplay_type(display_type);
					po.setType(type);
					PushManager.sendUnicast(po);
				}
			}
			if(result.equals(Refund_success)){
				Umeng umeng = this.umengDAO.getByUserID(co.getCoachID());
				if(umeng!=null){
					String ticker = "退订课程";
					String title = ticker;
					String text = "你有一位学员退订你的课程,点击查看.";
					String type = CommonConfig.Send_Unicast;
					String after_open = PushManager.GoApp;
					String display_type = PushManager.Display_Type_Notify;
					PushOption po = new PushOption();
					po.setDevice_tokens(umeng.getUmeng_Token());
					po.setTicker(ticker);
					po.setTitle(title);
					po.setText(text);
					po.setAfter_open(after_open);
					po.setDisplay_type(display_type);
					po.setType(type);
					PushManager.sendUnicast(po);
				}

			}
		}
		
	}

	//变更支付状态
	private String setPayType(String channel){
		String payType = "";
		if(channel.indexOf("wx")>-1){
			payType = "2";
		}else if(channel.indexOf("alipay")>-1){
			payType = "1";
		}else{
			payType = "3";
		}
		return payType;
	}




}
