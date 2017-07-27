package com.galaxy.ggolf.rest;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import com.galaxy.ggolf.dao.*;
import com.galaxy.ggolf.domain.User;
import com.galaxy.ggolf.domain.WalletLog;
import com.galaxy.ggolf.manager.PhoneCodeManager;
import com.galaxy.ggolf.tools.PingPPUtil;
import com.pingplusplus.model.Transfer;
import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.domain.Wallet;
import com.galaxy.ggolf.domain.WalletRecord;
import com.galaxy.ggolf.tools.CipherUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Produces("application/json")
@Path("/Wallet")
public class WalletService extends BaseService {

	private final Logger logger = LoggerFactory.getLogger(getClass()); 

	private int width = 90;//验证码宽度
	private int height = 40;//验证码高度
	private int codeCount = 4;//验证码个数
	private int lineCount = 19;//混淆线个数

	private char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	private final UserDAO userDAO;
	private final WalletDAO walletDAO;
	private final WalletLogDAO walletLogDAO;
	private final WalletRecordDAO walletRecordDAO;
	private final PhoneCodeManager phoneCodeManager;
	
	public WalletService(UserDAO userDAO,
			WalletDAO walletDAO, 
			WalletLogDAO walletLogDAO,
			WalletRecordDAO walletRecordDAO,
			PhoneCodeManager phoneCodeManager ) {
		this.userDAO = userDAO;
		this.walletDAO = walletDAO;
		this.walletLogDAO = walletLogDAO;
		this.walletRecordDAO = walletRecordDAO;
		this.phoneCodeManager = phoneCodeManager;
	}


//	@POST
//	@Path("/test")
//	public String test(@FormParam("data") String data,
//			@Context HttpHeaders headers){
//		try {
//			
//		} catch (Exception e) {
//			logger.error("Error occured",e);
//		}
//		return getErrorResponse();
//	}
	
//	/**
//	 * 创建/保存钱包
//	 * @param data
//	 * @param headers
//	 * @return
//	 */
//	@POST
//	@Path("/saveWallet")
//	public String saveWallet(String data,
//			@Context HttpHeaders headers){
//		try {
//			Wallet w = super.fromGson(data, Wallet.class);
//			if(!StringUtils.isEmpty(w.getPayPassword())){
//				String salt = this.walletDAO.getID("", 32);
//				w.setSalt(salt);
//				CipherUtil cipher = new CipherUtil();
//				String pwd = cipher.generatePassword(w.getPayPassword());
//				w.setPayPassword(pwd);
//				if(this.walletDAO.create(w)){
//					Wallet wallet = this.walletDAO.getByUserID(w.getUserID(), null);
//					return getResponse(wallet);
//				}
//			}else{
//				return getErrormessage("支付密码必须");
//			}
//		} catch (Exception e) {
//			logger.error("Error occured",e);
//		}
//		return getErrorResponse();
//	}

	/**
	 * 添加支付密码
	 * @param PayPassword
	 * @param Name
	 * @param IDCard
	 * @param UserID
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/addPayPassword")
	public String addPayPassword(@FormParam("PayPassword") String PayPassword,
			@FormParam("Code") String Code,
			@FormParam("Name") String Name,
			@FormParam("IDCard") String IDCard,
			@FormParam("UserID") String UserID,
			@Context HttpHeaders headers){
		try {
			User user = this.userDAO.getUserByUserID(UserID);
			if(user==null){
				return getErrormessage("该用户不存在");
			}
			if(StringUtils.isEmpty(user.getPhone())||user.getPhone().equalsIgnoreCase("null")){
				return getErrormessage("未绑定手机");
			}
			String codeResult = this.phoneCodeManager.ValidCode(user.getPhone(),Code);
			if(!codeResult.equals("")){
				return getErrormessage(codeResult);
			}
			//姓名中文验证
			String regName = "^[\\u4e00-\\u9fa5]+$";
			Pattern pattern = Pattern.compile(regName);
			Matcher matcher = pattern.matcher(Name);
			//身份证15位验证
			String isIDCard1="^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
			Pattern pattern1 = Pattern.compile(isIDCard1);
			Matcher matcher1 = pattern1.matcher(IDCard);
			//身份证18位验证
			String isIDCard2="^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)}$";
			Pattern pattern2 = Pattern.compile(isIDCard2);
			Matcher matcher2 = pattern2.matcher(IDCard);
			if(StringUtils.isEmpty(PayPassword)||PayPassword.length()>6){
				return getErrormessage("请设置6位的支付密码.");
			}else if(!matcher.matches()){
				return getErrormessage("请以中文填写姓名.");
			}else if(!matcher1.matches()||!matcher2.matches()){
				return getErrormessage("请填写有效的身份证号码.");
			}else{
				CipherUtil cipher = new CipherUtil();
				PayPassword = cipher.generatePassword(PayPassword);
				String sqlString = "PayPassword='"+PayPassword+"',Name='"+Name+"',IDCard='"+IDCard+"',";
				if(!this.walletDAO.update(sqlString,UserID)){
					return getErrormessage("操作有误");
				}
				return getSuccessResponse();
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}

	/**
	 * 忘记支付密码
	 * @param UserID
	 * @param Code
	 * @param IDCard
	 * @param PayPassword
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/forgetWalletPwd")
	public String forgetWalletPwd(@FormParam("UserID") String UserID,
			@FormParam("Code") String Code,
			@FormParam("IDCard") String IDCard,
			@FormParam("PayPassword") String PayPassword,
			@Context HttpHeaders headers){
		try {
			User user = this.userDAO.getUserByUserID(UserID);
			if(StringUtils.isEmpty(user.getPhone())||user.getPhone().equalsIgnoreCase("null")){
				return getErrormessage("未绑定手机");
			}
			String codeResult = this.phoneCodeManager.ValidCode(user.getPhone(),Code);
			if(!codeResult.equals("")){
				return getErrormessage(codeResult);
			}
			//身份证15位验证
			String isIDCard1="^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
			Pattern pattern1 = Pattern.compile(isIDCard1);
			Matcher matcher1 = pattern1.matcher(IDCard);
			//身份证18位验证
			String isIDCard2="^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)}$";
			Pattern pattern2 = Pattern.compile(isIDCard2);
			Matcher matcher2 = pattern2.matcher(IDCard);
			if(!matcher1.matches() || !matcher2.matches()){
				return getErrormessage("请填写有效的身份证号码.");
			}else if(StringUtils.isEmpty(PayPassword)||PayPassword.length()>6){
				return getErrormessage("请设置6位的支付密码.");
			}
			Wallet wallet = this.walletDAO.getByUserID(UserID,null);
			if(!wallet.getIDCard().equalsIgnoreCase(IDCard)){
				return getErrormessage("身份证号码不匹配");
			}else{
				CipherUtil cipher = new CipherUtil();
				PayPassword = cipher.generatePassword(PayPassword);
				String sqlString = "PayPassword='"+PayPassword+"',";
				if(this.walletDAO.update(sqlString,UserID)){
					return getSuccessResponse();
				}
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}

	/**
	 * 修改支付密码
	 * @param NewPassword
	 * @param OldPassword
	 * @param Code
	 * @param UserID
	 * @param session
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/updatePayPassword")
	public String updatePayPassword(@FormParam("NewPassword") String NewPassword,
								 @FormParam("OldPassword") String OldPassword,
								 @FormParam("Code") String Code,
								 @FormParam("UserID") String UserID,
								 @Context HttpSession session,
								 @Context HttpHeaders headers){
		try {
			Wallet wallet = this.walletDAO.getByUserID(UserID,"0");
			if(wallet!=null){
				CipherUtil cipher = new CipherUtil();
				NewPassword = cipher.generatePassword(NewPassword);
				if(!StringUtils.isEmpty(wallet.getPayPassword())){
					OldPassword= cipher.generatePassword(OldPassword);
					if(OldPassword.equals(wallet.getPayPassword())){
//						String code = (String) session.getAttribute("codeValidate");
						User user = this.userDAO.getUserByUserID(UserID);
						String codeResult = this.phoneCodeManager.ValidCode(user.getPhone(),Code);
						if(codeResult.equals("")){
							if(StringUtils.isEmpty(NewPassword)||NewPassword.length()>6){
								return getErrormessage("请设置6位的支付密码.");
							}
							if(this.walletDAO.updatePayPWD(NewPassword,UserID)){
								return getSuccessResponse();
							}
						}else{
							return getErrormessage(codeResult);
						}
					}else{
						return getErrormessage("新旧密码不一致");
					}
				}else{
					return getErrormessage("你还未设置支付密码,请先设置");
				}
			}else{
				return getErrormessage("该用户不存在");
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 获取用户钱包信息
	 * @param UserID
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getWallet")
	public String getWallet(@FormParam("UserID") String UserID,
			@Context HttpHeaders headers){
		try {
			return getResponse(this.walletDAO.getByUserID(UserID, "0"));
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}

	/**
	 * 生成充值订单
	 * @param Money
	 * @param UserID
	 * @param Remark
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/RechargeWallet")
	public String RechargeWallet(@FormParam("Money") String Money,
			@FormParam("UserID") String UserID,
			@FormParam("Remark") String Remark,
			@Context HttpHeaders headers){
		try {
			WalletRecord wr = new WalletRecord();
			if(StringUtils.isEmpty(Money)||Money.equalsIgnoreCase("null")){
				return getErrormessage("请填写金额");
			}
			User user = this.userDAO.getUserByUserID(UserID);
			if(user==null){
				return getErrormessage("该用户不存在");
			}
			if(StringUtils.isEmpty(Remark)||Remark.equalsIgnoreCase("null")){
				Remark = "充值:¥"+Money;
			}
			String prefix = "WA" + this.walletDAO.Time2();
			String recordSn = this.walletDAO.getID(prefix, 2);
			wr.setRecordSn(recordSn);
			wr.setToUID(UserID);
			wr.setMoney(Money);
			wr.setType("1");
			wr.setRemark(Remark);
			wr.setFetchStatus("0");
			if(this.walletRecordDAO.create(wr)){
				WalletRecord result = this.walletRecordDAO.getByRecordSn(recordSn);
				return getResponse(result);
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}

	/**
	 * 生成提现订单
	 * @param Money
	 * @param UserID
	 * @param Remark
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/WithdrawalsWallet")
	public String WithdrawalsWallet(
			@FormParam("Money") String Money,
			@FormParam("UserID") String UserID,
			@FormParam("PayPassword") String PayPassword,
			@FormParam("Remark") String Remark,
			@FormParam("channel") String channel,
			@FormParam("recipient") String recipient,
			@Context HttpHeaders headers){
		try {
			int wMoney = Integer.parseInt(Money);
			if(StringUtils.isEmpty(Money)||Money.equalsIgnoreCase("null")||wMoney<=0){
				return getErrormessage("请填写正确的金额");
			}
			User user = this.userDAO.getUserByUserID(UserID);
			if(user==null){
				return getErrormessage("该用户不存在");
			}
			WalletRecord wr = new WalletRecord();
			Wallet wallet = this.walletDAO.getByUserID(UserID,"0");
			if(!StringUtils.isEmpty(wallet.getPayPassword())&&!wallet.getPayPassword().equalsIgnoreCase("null")){
				CipherUtil cipher = new CipherUtil();
				PayPassword = cipher.generatePassword(PayPassword);
				if(!PayPassword.equals(wallet.getPayPassword())){
					return getErrormessage("请输入支付密码");
				}
			}

			int money = Integer.parseInt(wallet.getMoney()) - wMoney;
			if(money < 0){
				return getErrormessage("钱包余额不足");
			}
			if(StringUtils.isEmpty(Remark)||Remark.equalsIgnoreCase("null")){
				Remark = "提现:¥"+Money;
			}
			String prefix = "WA" + this.walletDAO.Time2();
			String recordSn = this.walletDAO.getID(prefix, 2);
			wr.setRecordSn(recordSn);
			wr.setFromUID(UserID);
			wr.setMoney(Money);
			wr.setType("2");
			wr.setRemark(Remark);
			wr.setPayStatus("0");
			if(this.walletRecordDAO.create(wr)){
				WalletRecord wRecord = this.walletRecordDAO.getByRecordSn(recordSn);
				Transfer transfer = null;
				Map<String,String> metadata = new HashMap<String,String>();
				metadata.put("orderType", "withdrawalsWallet");
				String description = "提现"+wr.getMoney()+"元";
				transfer = PingPPUtil.createTransfer(wRecord.getRecordSn(),
						channel, wr.getMoney(), recipient,
						description, metadata);
				if(transfer!=null){
					WalletLog wLog = new WalletLog();
					wLog.setRecordSn(wRecord.getRecordSn());
					wLog.setUserID(wallet.getUserID());
					wLog.setRemark(wRecord.getRemark());
					wLog.setChangeMoney("-"+wRecord.getMoney());
					wLog.setMoney(money+"");
					this.walletLogDAO.create(wLog);//生成日志
					this.walletRecordDAO.updateTransferID(transfer.getId(), wRecord.getRecordSn());
				}
				return getResponse(transfer);
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}

	/**
	 * 验证码
	 * @param time
	 * @param request
	 * @param response
	 * @param headers
	 */
	@GET
	@Path("/VerificationCode/{time}")
	public void VerificationCode(@PathParam("time") String time,@Context HttpServletRequest request, @Context HttpServletResponse response, @Context HttpHeaders headers){
		try {
			//定义随机数类
			Random r = new Random();
			//定义存储验证码的类
			StringBuilder builderCode = new StringBuilder();
			//定义画布
			BufferedImage buffImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			//得到画笔
			Graphics g = buffImg.getGraphics();
			//1.设置颜色,画边框
			g.setColor(Color.black);
			g.drawRect(0,0,width,height);
			//2.设置颜色,填充内部
			g.setColor(Color.white);
			g.fillRect(1,1,width-2,height-2);
			//3.设置干扰线
			g.setColor(Color.gray);
			for (int i = 0; i < lineCount; i++) {
				g.drawLine(r.nextInt(width),r.nextInt(width),r.nextInt(width),r.nextInt(width));
			}
			//4.设置验证码
			g.setColor(Color.blue);
			//4.1设置验证码字体
			g.setFont(new Font("宋体",Font.BOLD|Font.ITALIC,30));
			for (int i = 0; i < codeCount; i++) {
				char c = codeSequence[r.nextInt(codeSequence.length)];
				builderCode.append(c);
				g.drawString(c+"",11*(i*2),30);
			}
			//5.输出到屏幕
			ServletOutputStream sos = response.getOutputStream();
			ImageIO.write(buffImg,"png",sos);
			//6.保存到session中
			HttpSession session = request.getSession();
			session.setAttribute("codeValidate",builderCode.toString());
			//7.禁止图像缓存。
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/png");
			//8.关闭sos
			sos.close();
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
	}

}
