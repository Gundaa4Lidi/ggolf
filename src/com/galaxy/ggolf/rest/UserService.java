package com.galaxy.ggolf.rest;

import java.io.File;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.activation.DataHandler;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloopen.rest.sdk.utils.encoder.BASE64Decoder;
import com.galaxy.ggolf.dao.CollectDAO;
import com.galaxy.ggolf.dao.LikeDAO;
import com.galaxy.ggolf.domain.Collect;
import com.galaxy.ggolf.domain.Follow;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.domain.Likes;
import com.galaxy.ggolf.domain.PhoneCode;
import com.galaxy.ggolf.domain.User;
import com.galaxy.ggolf.domain.UserDetail;
import com.galaxy.ggolf.dto.FollowCount;
import com.galaxy.ggolf.dto.TokenResponse;
import com.galaxy.ggolf.dto.UserData;
import com.galaxy.ggolf.dto.UserRanData;
import com.galaxy.ggolf.jdbc.CommonConfig;
import com.galaxy.ggolf.manager.FollowManager;
import com.galaxy.ggolf.manager.OnlineManager;
import com.galaxy.ggolf.manager.PhoneCodeManager;
import com.galaxy.ggolf.manager.SessionManager;
import com.galaxy.ggolf.manager.UserDetailManager;
import com.galaxy.ggolf.manager.UserManager;
import com.galaxy.ggolf.tools.CipherUtil;
import com.galaxy.ggolf.tools.FileUtil;
import com.galaxy.ggolf.tools.ListUtil;
import com.galaxy.ggolf.tools.LocationUtil;
import com.galaxy.ggolf.tools.SDKTestSendTemplateSMS;
import com.spatial4j.core.shape.Rectangle;


//@Consumes("multipart/form-data")
@Produces("application/json")
@Path("/User")
public class UserService extends BaseService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final UserManager manager;
	
	private final UserDetailManager userDetailManager;
	
	private final PhoneCodeManager phonecode;
	
	private final FollowManager followManage;
	
	private final OnlineManager onlineManager;
	
	private final LikeDAO likeDAO;
	
	private final CollectDAO collectDAO;
	
	
	
	public UserService(UserManager manager,UserDetailManager userDetailManager, SessionManager sessionManager, 
			PhoneCodeManager phonecode,FollowManager followManager,
			OnlineManager onlineManager,LikeDAO likeDAO,CollectDAO collectDAO) {
		super.setSessionManager(sessionManager);
		this.manager = manager;
		this.userDetailManager = userDetailManager;
		this.phonecode = phonecode;
		this.followManage = followManager;
		this.onlineManager = onlineManager;
		this.likeDAO = likeDAO;
		this.collectDAO = collectDAO;
	}

	long min = 3;
	
	/**
	 * 发送验证码
	 * @param phone
	 * @return
	 */
	@POST
	@Path("/sendcode")
	public String sendcode(@FormParam("phone") String phone, @Context HttpHeaders headers) {
		String result = "";
		try {
			logger.info("手机号码----------",phone);
			if (manager.checkUser(phone)) {
				result = "该号码已注册,请直接登陆";
				return getResponse(result);
			} else if (phonecode.getcodeByPhone(phone) != null) {
				PhoneCode pcode = phonecode.getcodeByPhone(phone);
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date lasttime = format.parse(pcode.getDatetime());
				Date now = new Date();
				long diff = now.getTime() - lasttime.getTime();
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffDays = diff / (24 * 60 * 60 * 1000);
				logger.info("" + diffDays + diffHours + diffMinutes);
				if (diffDays <= 0 && diffHours <= 0 && diffMinutes < min) {
					result = "验证码已发送，耐心等待！";
					return getResponse(result);
				} else {
					String code = game(4);
					SDKTestSendTemplateSMS sms = new SDKTestSendTemplateSMS();
					String msg = sms.getcode(phone, code,min);
					phonecode.createPhoneCode(phone, code);
					logger.info(msg);
					return getResponse(msg);
				}
			} else {
				String code = game(4);
				SDKTestSendTemplateSMS sms = new SDKTestSendTemplateSMS();
				String msg = sms.getcode(phone, code,min);
				phonecode.createPhoneCode(phone, code);
				logger.info(msg);
				return getResponse(msg);
			}
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	
	
	/**
	 * 发送验证码(用于忘记密码)
	 * @param phone
	 * @return
	 */
	@POST
	@Path("/sendcode2")
	public String sendcode2(@FormParam("phone") String phone, @Context HttpHeaders headers) {
		String result = "";
		try {
			logger.info("手机号码----------{}",phone);
			if (manager.getUserByPhone(phone)==null) {
				result = "该号码不存在,请重新输入";
				return getResponse(result);
			} else if (phonecode.getcodeByPhone(phone) != null) {
				PhoneCode pcode = phonecode.getcodeByPhone(phone);
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date lasttime = format.parse(pcode.getDatetime());
				Date now = new Date();
				long diff = now.getTime() - lasttime.getTime();
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffDays = diff / (24 * 60 * 60 * 1000);
				logger.info("" + diffDays + diffHours + diffMinutes);
				if (diffDays <= 0 && diffHours <= 0 && diffMinutes < min) {
					result = "验证码已发送，耐心等待！";
					return getResponse(result);
				} else {
					String code = game(6);
					SDKTestSendTemplateSMS sms = new SDKTestSendTemplateSMS();
					String msg = sms.getcode(phone, code,min);
					phonecode.createPhoneCode(phone, code);
					logger.info(msg);
					return getResponse(msg);
				}
			} else {
				String code = game(6);
				SDKTestSendTemplateSMS sms = new SDKTestSendTemplateSMS();
				String msg = sms.getcode2(phone, code,min);
				phonecode.createPhoneCode(phone, code);
				logger.info(msg);
				return getResponse(msg);
			}
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	
	/**
	 * 普通登录
	 * @param phone
	 * @param password
	 * @return
	 */
	@POST
	@Path("/logon")
	public String logon(@FormParam("phone") String phone,
			@FormParam("password") String password,@Context HttpHeaders headers) {
		String result = "";
		try {
			User user = this.manager.getUserByPhone(phone);
			if (user != null) {
				if (CipherUtil.validatePassword(user.getPassword(), password)) {
					SecureRandom random = new SecureRandom();
					byte bytes[] = new byte[20];
					random.nextBytes(bytes);
					String token = bytes.toString()
							+ CipherUtil.generatePassword(user.getUserID());
					sessionManager.putAuth(token, user);
					return super.getResponse(new TokenResponse(token,user.getUserID(), user
							.getPhone(), user.getName(), user.getHead_portrait()));
				} else {
					result = "密码错误";
					return getErrorResponse(result);
				}
			} else {
				result = "该用户不存在";
				return getErrorResponse(result);
			}
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			result = "登录失败";
			return getErrorResponse(result);
		}
	}
	
	/**
	 * 微信账号创建用户账号并登陆
	 * @param openID
	 * @param name
	 * @param head
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/logonByWechat")
	public String loginByWechat(@FormParam("openid") String openid,
			@FormParam("name") String name,
			@FormParam("head") String head, @Context HttpHeaders headers){
		try {
			User user = this.manager.getUserByWechat(openid);
			if(user==null){
				this.manager.createUserByWechat(new User(openid,name,head));
				user = this.manager.getUserByWechat(openid);
				if(user!=null){//创建用户详细资料
					UserDetail userDetail = new UserDetail(user.getUserID(), user.getPhone(), user.getHead_portrait(), user.getName(), "男");
					this.userDetailManager.createUserDetail(userDetail);
				}
			}
			SecureRandom random = new SecureRandom();
			byte bytes[] = new byte[20];
			random.nextBytes(bytes);
			String token = bytes.toString()
					+ CipherUtil.generatePassword(user.getUserID());
			sessionManager.putAuth(token, user);
			return getResponse(new TokenResponse(token, user.getUserID(),
					user.getWechat(), user.getName(), user.getHead_portrait()));
		} catch (GalaxyLabException ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	
	@POST
	@Path("/loginWechat")
	public String login(@FormParam("openid") String openid,
			@FormParam("name") String name,
			@FormParam("head") String head,
			@FormParam("longitude") String longitude,
			@FormParam("latitude") String latitude,
			@FormParam("login_place") String login_place, @Context HttpHeaders headers) {
		String result = "";
		try {
			User user = this.manager.getUserByWechat(openid);
			if(user==null){
				this.manager.createUserByWechat(new User(openid,name,head));
				user = this.manager.getUserByWechat(openid);
			}
			if(longitude != null && latitude != null && login_place != null){
				this.manager.updateLocation(longitude, latitude,  user.getUserID());
				this.manager.loginRecord(longitude, latitude, login_place,user.getUserID());
			}
			SecureRandom random = new SecureRandom();
			byte bytes[] = new byte[20];
			random.nextBytes(bytes);
			String token = bytes.toString()
					+ CipherUtil.generatePassword(user.getUserID());
			sessionManager.putAuth(token, user);
			return getResponse(new TokenResponse(token, user.getUserID(),
					user.getWechat(), user.getName(), user.getHead_portrait()));
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			result = "登录失败";
			return getErrorResponse(result);
		}
	}
	
	/**
	 * 
	 * 登录获取位置经纬度
	 * @param phone
	 * @param password
	 * @param longitude
	 * @param latitude
	 * @param login_place
	 * @return
	 */
	@POST
	@Path("/login")
	public String login(@FormParam("phone") String phone,
			@FormParam("password") String password,
			@FormParam("longitude") String longitude,
			@FormParam("latitude") String latitude,
			@FormParam("login_place") String login_place, @Context HttpHeaders headers) {
		String result = "";
		try {
			User user = this.manager.getUserByPhone(phone);
			if (user != null) {
				if(longitude != null && latitude != null && login_place != null){
					this.manager.updateLocation(longitude, latitude,  user.getUserID());
					this.manager.loginRecord(longitude, latitude, login_place,user.getUserID());
				}
				if (CipherUtil.validatePassword(user.getPassword(), password)) {
					SecureRandom random = new SecureRandom();
					byte bytes[] = new byte[20];
					random.nextBytes(bytes);
					String token = bytes.toString()
							+ CipherUtil.generatePassword(user.getUserID());
					sessionManager.putAuth(token, user);
					return super.getResponse(new TokenResponse(token,user.getUserID(), user
							.getPhone(), user.getName(), user.getHead_portrait()));
				} else {
					result  = "密码错误";
					return getErrorResponse(result);
				}
			} else {
				result = "该用户不存在";
				return getErrorResponse(result);
			}
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			result = "登录失败";
			return getErrorResponse(result);
		}
	}
	
	
	
	/**
	 * 更换、绑定手机
	 * @param phone
	 * @param code
	 * @param userID
	 * @return
	 */
	@POST
	@Path("/bindingPhone")
	public String bindingPhone(@FormParam("phone") String phone,
			@FormParam("code") String code,
			@FormParam("userID") String userID, @Context HttpHeaders headers) {
		
		String result = "";
		try {verifyUser(headers);} catch (Exception e) {return getErrormessage("登陆已过期，请重新登陆");}
		try {
			PhoneCode pcode = phonecode.getcodeByPhone(phone);
			if (pcode.getCode().equals(code)) {
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date lasttime = format.parse(pcode.getDatetime());
				Date now = new Date();
				long diff = now.getTime() - lasttime.getTime();

				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffDays = diff / (24 * 60 * 60 * 1000);
				logger.info("" + diffDays + diffHours + diffMinutes
						+ diffSeconds);
				if (diffDays > 0 || diffHours > 0 || diffMinutes >= min) {
					result = "验证码已超时，请重新获取！";
					return getErrorResponse(result);
				} else {
					this.manager.bindingPhone(phone, userID);
					if(this.userDetailManager.checkUserDetail(userID)){
						this.userDetailManager.updateUserDetailPhone(phone,userID);
					}
					return getSuccessResponse();
				}
			} else {
				result = "验证码错误";
				return getErrorResponse(result);
			}
		} catch (GalaxyLabException ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	
	/**
	 * 创建用户
	 * @param phone
	 * @param password
	 * @param code
	 * @param name
	 * @return
	 */
	@POST
	@Path("/create")
	public String createUser(@FormParam("phone") String phone,
			@FormParam("password") String password,
			@FormParam("code") String code,
			@FormParam("Name") String name, @Context HttpHeaders headers) {
		String result = "";
		try {
			PhoneCode pcode = phonecode.getcodeByPhone(phone);
			if (pcode.getCode().equals(code)) {
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date lasttime = format.parse(pcode.getDatetime());
				Date now = new Date();
				long diff = now.getTime() - lasttime.getTime();

				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffDays = diff / (24 * 60 * 60 * 1000);
				logger.info("" + diffDays + diffHours + diffMinutes
						+ diffSeconds);
				if (diffDays > 0 || diffHours > 0 || diffMinutes >= min) {
					result = "验证码已超时，请重新获取！";
					return getErrorResponse(result);
				} else {
					CipherUtil cipher = new CipherUtil();
					password = cipher.generatePassword(password);// md5加密后的密码
					this.manager.createUser(phone, password, name);
					User user = this.manager.getUserByPhone(phone);
					if(user!=null){
						UserDetail userDetail = new UserDetail(user.getUserID(), user.getPhone(), user.getHead_portrait(), user.getName(), "男");
						this.userDetailManager.createUserDetail(userDetail);
					}
					return getSuccessResponse();
				}
			} else {
				result = "验证码错误";
				return getErrorResponse(result);
			}
		} catch (GalaxyLabException ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	
	@POST
	@Path("/updatePassword")
	public String updatePassword(@FormParam("phone") String phone,
			@FormParam("password") String password,
			@FormParam("code") String code, @Context HttpHeaders headers) {
		String result = "";
		try {
			PhoneCode pcode = phonecode.getcodeByPhone(phone);
			if (pcode.getCode().equals(code)) {
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date lasttime = format.parse(pcode.getDatetime());
				Date now = new Date();
				long diff = now.getTime() - lasttime.getTime();

				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffDays = diff / (24 * 60 * 60 * 1000);
				logger.info("" + diffDays + diffHours + diffMinutes
						+ diffSeconds);
				if (diffDays > 0 || diffHours > 0 || diffMinutes >= min) {
					result = "验证码已超时，请重新获取！";
					return getErrorResponse(result);
				} else {
					CipherUtil cipher = new CipherUtil();
					password = cipher.generatePassword(password);// md5加密后的密码
					this.manager.updatepassword(phone, password);
					return getSuccessResponse();
				}
			} else {
				result = "验证码错误";
				return getErrorResponse(result);
			}
		} catch (GalaxyLabException ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	
	/**
	 * 保存用户
	 * @param data
	 * @return
	 */
	@POST
	@Path("/saveUser")
	public String saveUser(String data, @Context HttpHeaders headers){
		User user = fromGson(data, User.class);
		try {
			this.manager.updateUser(user);
			return getSuccessResponse();
		} catch (GalaxyLabException ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	/**
	 * 保存用户头像
	 * @param head
	 * @param phone
	 * @return
	 */
	@POST
	@Path("/saveHead")
	public String saveHead(String data, @Context HttpHeaders headers){
		User user = super.fromGson(data, User.class);
		String fileName = CommonConfig.UserHead + "@" + user.getUserID() + ".png";
		String filePath = CommonConfig.USER_PATH + CommonConfig.UserHead + "\\";
		try {
			if(user.getHead_portrait()!=null){
				File fs = new File(filePath+fileName);
				if(fs.exists()){
					FileUtil.deleteFile(fs);
				}
				boolean result = FileUtil.GetImageUrl(user.getHead_portrait(),fileName,filePath);
				if(result){
					SimpleDateFormat sdf = new SimpleDateFormat(CommonConfig.DateFormatNum);
					String date = sdf.format(new Date());
					String url = CommonConfig.CONNECT + CommonConfig.FILE_DOWNLOAD +  "user_"
							+ CommonConfig.UserHead + "_" + fileName + "?t=" + date;
					user.setHead_portrait(url);
					this.manager.saveHead(user.getHead_portrait(), user.getUserID());
					User user1 = this.manager.getUserByUserID(user.getUserID());
					return getResponse(user1);
				}else{
					this.manager.saveHead(user.getHead_portrait(), user.getUserID());
					User user2 = this.manager.getUserByUserID(user.getUserID());
					return getResponse(user2);
				}
			}
		} catch (GalaxyLabException ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
		return getErrorResponse();
	}
	
	
	
	@GET
	@Path("/getUserByUserID")
	public String getUserByPhone(@FormParam("UserID") String userID,@Context HttpHeaders headers){
		try {
			User user = this.manager.getUserByUserID(userID);
			/*if(verifyApp(headers)){
				user.setHead_portrait(ImageBase64.GetImageStr(user.getHead_portrait()));
			}*/
			User user1 = new User(user.getUserID(), user.getPhone(), user.getName(), user.getAge(), user.getSex(), user.getHead_portrait());
			return getResponse(user1);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	
	/**
	 * 获取全部用户
	 * @return
	 */
	@GET
	@Path("/getAll")
	public String getAll(@Context HttpHeaders headers){
		try {
			return getResponse(this.manager.getAll());
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 搜索用户
	 * @return
	 */
	@GET
	@Path("/getUserInfo")
	public String getUserInfo(@FormParam("PageNum") String PageNum,
			@FormParam("Rows") String Rows,
			@FormParam("Keyword") String Keyword,
			@Context HttpHeaders headers){
		try {
			Collection<User> userList = this.manager.getUser(PageNum, Rows, Keyword, null,null); 
			int count = this.manager.getSearchCount(Keyword);
			UserData data = new UserData(count,userList);
			return getResponse(data);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 搜索用户,根据经纬度获取距离
	 * @return
	 */
	@GET
	@Path("/getUserInfo2")
	public String getUserInfo2(@FormParam("PageNum") String PageNum,
			@FormParam("Rows") String Rows,
			@FormParam("Keyword") String Keyword,
			@FormParam("Lon") String Lon,
			@FormParam("Lat") String Lat,
			@Context HttpHeaders headers){
		try {
			Collection<User> userList = this.manager.getUser(PageNum, Rows, Keyword,Lon,Lat); 
			int count = this.manager.getSearchCount(Keyword);
			UserData data = new UserData(count,userList);
			return getResponse(data);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 搜索附近的人
	 * @param Lon
	 * @param Lat
	 * @param Radius
	 * @param Rows
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getNearByUser")
	public String getNearByUser(@FormParam("Lon") String Lon,
			@FormParam("Lat") String Lat,
			@FormParam("Radius") String Radius,
			@FormParam("Rows") String Rows,
			@Context HttpHeaders headers){
		try {
			if(Rows == null || Rows.equals("") || Rows.equalsIgnoreCase("null")){
				Rows = null;
			}
			double lon = Double.parseDouble(Lon);
			double lat = Double.parseDouble(Lat);
			int radius = Integer.parseInt(Radius);
			Rectangle rec = LocationUtil.GetRange(lon, lat, radius);
			Collection<User> userList = this.manager.getNearByUser(rec, Rows);
			int count = userList.size();
			UserData data = new UserData(count, userList);
			return getResponse(data);
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
		
	}
	
	public static String game(int count) {
		StringBuffer sb = new StringBuffer();
		String str = "0123456789";
		Random r = new Random();
		for (int i = 0; i < count; i++) {
			int num = r.nextInt(str.length());
			sb.append(str.charAt(num));
			str = str.replace((str.charAt(num) + ""), "");
		}
		return sb.toString();
	}
	
	/**
	 *获取全部用户的登录记录 
	 */
	@GET
	@Path("/getAllUserLogRec")
	public String getAllUserLogRec(@Context HttpHeaders headers){
		try {
			return getResponse(this.manager.getAllUserLogRec());
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 根据用户获取登录记录
	 * @param userID
	 * @return
	 */
	@GET
	@Path("/getUserLogRecByUserID")
	public String getUserLogRec(@FormParam("userID") String userID, @Context HttpHeaders headers ){
		try {
			return getResponse(this.manager.getUserLogRec(userID));
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	@POST
	@Path("/remove")
	public String removeUser(@FormParam("phone") String phone, @Context HttpHeaders headers ){
		try {
			if(this.manager.removeUser(phone)){
				return getSuccessResponse();
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
		
	}
	
	
	//获取好友列表
	@GET
	@Path("/getFollow")
	public String getFollow(@FormParam("userID") String userID, @Context HttpHeaders headers){
		try {
			return getResponse(this.followManage.getFriendList(userID));
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	//获取已被他人关注的列表
	@GET
	@Path("/getNewFriendList")
	public String getNewFriendList(@FormParam("userID") String userID,
			@Context HttpHeaders headers){
		try {
			return getResponse(this.followManage.getNewFriendList(userID));
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	//获取黑名单
	@GET
	@Path("/getBlackList")
	public String getBlackList(@FormParam("userID") String userID,
			@Context HttpHeaders headers){
		try {
			return getResponse(this.followManage.getBlackList(userID));
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	//修改好友备注
	@POST
	@Path("/saveFenRemark")
	public String saveFenRemark(String data,@Context HttpHeaders headers){
		try {
			Follow fol = super.fromGson(data, Follow.class);
			this.followManage.updateRemark(fol.getRemark(), fol.getUID());
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	//拉入黑名单
	@POST
	@Path("/DrawInBlackList")
	public String DrawInBlackList(@FormParam("UID") String UID,@Context HttpHeaders headers){
		try {
			this.followManage.DrawInBlackList(UID);
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	//移出黑名单
	@POST
	@Path("/removeBlackList")
	public String removeBlackList(@FormParam("UID") String UID,@Context HttpHeaders headers){
		try {
			this.followManage.removeBlackList(UID);
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	} 
	
	
	//添加关注
	@POST
	@Path("/follow")
	public String follow(String data, @Context HttpHeaders headers){
		Follow follow = fromGson(data,Follow.class);
		try {
			return getResponse(this.followManage.follow(follow));
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	//获取关注、粉丝的数量
	@GET
	@Path("/CountFollow")
	public String getCountFollow(@FormParam("UserID") String userID, @Context HttpHeaders headers){
		try {
			int fens = this.followManage.getFensCount(userID);
			int follows = this.followManage.getFollowCount(userID);
			FollowCount count = new FollowCount(fens, follows);
			return getResponse(count);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	//取消关注
	@POST
	@Path("/cancelFollow")
	public String cancelFollow(String data, @Context HttpHeaders headers){
		Follow follow = fromGson(data,Follow.class);
		try {
			if(this.followManage.cancel(follow)){
				return getSuccessResponse();
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	//获取地理位置
	@POST
	@Path("/addLocation")
	public String addLocation(@FormParam("UserID") String UserID, @FormParam("Location") String Location, 
			@Context HttpHeaders headers){
		try{
			this.onlineManager.addLocation(UserID,Location);
			return getSuccessResponse();
		} catch(Exception e){
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	//清除地理位置
	@POST
	@Path("/cleanLocation")
	public String cleanLocation(@FormParam("UserID") String UserID, @Context HttpHeaders headers){
		try {
			this.onlineManager.cleanLocation(UserID);
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 点赞
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/like")
	public String like(String data,@Context HttpHeaders headers){
		try {
			Likes like = fromGson(data, Likes.class);
			String result = "";
			if(this.likeDAO.getLikeByUserID(like.getUserID(), like.getThemeID(), like.getType())!=null){
				result = "您已点赞!";
				return getResponse(result);
			}else{
				if(this.likeDAO.create(like)){
					return getSuccessResponse();
				}
			}
		} catch (Exception e) {
			logger.error("error occured",e);
		}
		return getErrorResponse();
	}
	/**
	 * 添加/取消收藏
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/collect")
	public String collect(String data, @Context HttpHeaders headers){
		try {
			String result = "";
			Collect col = fromGson(data, Collect.class);
			Collect col1 = this.collectDAO.getCollectByUserID(col.getUserID(), col.getThemeID(), col.getType());
			if(col1 != null){
				if(col1.getStatus().equals("1")){
					this.collectDAO.update(col1.getUID(), "0");
					result = "取消收藏";
				}else if(col1.getStatus().equals("0")){
					this.collectDAO.update(col1.getUID(), "1");
					result = "收藏成功";
				}
			}else{
				this.collectDAO.create(col);
				result = "收藏成功";
			}
			return getResponse(result);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
		
	}
	
	/**
	 * 随机获取未添加关注的用户
	 * @param UserID
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/findFriend")
	public String findFriend(@FormParam("UserID") String UserID,
			@Context HttpHeaders headers){
		try {
			Collection<User> users = this.manager.getRandomUser(UserID);
			Collection<UserDetail> userDetails = new ArrayList<UserDetail>(); 
			for(User user : users){
				UserDetail ud = this.userDetailManager.getUserDetailByUserID(user.getUserID());
				if(ud!=null){
					userDetails.add(ud);				
				}
			}
			return getResponse(userDetails);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	 
	/**
	 * 查询电话簿是否有注册用户
	 * @param phones
	 * @param UserID
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/haveRegister")
	public String haveRegister(@FormParam("phones") String phones,
			@FormParam("UserID") String UserID,
			@Context HttpHeaders headers){
		try {
			if(phones!=null){
				Collection<UserDetail> userDetails = new ArrayList<UserDetail>();
				Collection<User> users = this.manager.haveRegister(phones, UserID);
				if(users.size() > 0){
					for(User u : users){
						UserDetail ud = this.userDetailManager.getUserDetailByUserID(u.getUserID());
						if(ud!=null){
							userDetails.add(ud);
						}
					}
					return getResponse(userDetails);
				}
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}

}

/**                                                                    
 *            .,,       .,:;;iiiiiiiii;;:,,.     .,,                   
 *          rGB##HS,.;iirrrrriiiiiiiiiirrrrri;,s&##MAS,                
 *         r5s;:r3AH5iiiii;;;;;;;;;;;;;;;;iiirXHGSsiih1,               
 *            .;i;;s91;;;;;;::::::::::::;;;;iS5;;;ii:                  
 *          :rsriii;;r::::::::::::::::::::::;;,;;iiirsi,               
 *       .,iri;;::::;;;;;;::,,,,,,,,,,,,,..,,;;;;;;;;iiri,,.           
 *    ,9BM&,            .,:;;:,,,,,,,,,,,hXA8:            ..,,,.       
 *   ,;&@@#r:;;;;;::::,,.   ,r,,,,,,,,,,iA@@@s,,:::;;;::,,.   .;.      
 *    :ih1iii;;;;;::::;;;;;;;:,,,,,,,,,,;i55r;;;;;;;;;iiirrrr,..       
 *   .ir;;iiiiiiiiii;;;;::::::,,,,,,,:::::,,:;;;iiiiiiiiiiiiri         
 *   iriiiiiiiiiiiiiiii;;;::::::::::::::::;;;iiiiiiiiiiiiiiiir;        
 *  ,riii;;;;;;;;;;;;;:::::::::::::::::::::::;;;;;;;;;;;;;;iiir.       
 *  iri;;;::::,,,,,,,,,,:::::::::::::::::::::::::,::,,::::;;iir:       
 * .rii;;::::,,,,,,,,,,,,:::::::::::::::::,,,,,,,,,,,,,::::;;iri       
 * ,rii;;;::,,,,,,,,,,,,,:::::::::::,:::::,,,,,,,,,,,,,:::;;;iir.      
 * ,rii;;i::,,,,,,,,,,,,,:::::::::::::::::,,,,,,,,,,,,,,::i;;iir.      
 * ,rii;;r::,,,,,,,,,,,,,:,:::::,:,:::::::,,,,,,,,,,,,,::;r;;iir.      
 * .rii;;rr,:,,,,,,,,,,,,,,:::::::::::::::,,,,,,,,,,,,,:,si;;iri       
 *  ;rii;:1i,,,,,,,,,,,,,,,,,,:::::::::,,,,,,,,,,,,,,,:,ss:;iir:       
 *  .rii;;;5r,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,sh:;;iri        
 *   ;rii;:;51,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,.:hh:;;iir,        
 *    irii;::hSr,.,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,.,sSs:;;iir:         
 *     irii;;:iSSs:.,,,,,,,,,,,,,,,,,,,,,,,,,,,..:135;:;;iir:          
 *      ;rii;;:,r535r:...,,,,,,,,,,,,,,,,,,..,;sS35i,;;iirr:           
 *       :rrii;;:,;1S3Shs;:,............,:is533Ss:,;;;iiri,            
 *        .;rrii;;;:,;rhS393S55hh11hh5S3393Shr:,:;;;iirr:              
 *          .;rriii;;;::,:;is1h555555h1si;:,::;;;iirri:.               
 *            .:irrrii;;;;;:::,,,,,,,,:::;;;;iiirrr;,                  
 *               .:irrrriiiiii;;;;;;;;iiiiiirrrr;,.                    
 *                  .,:;iirrrrrrrrrrrrrrrrri;:.                        
 *                        ..,:::;;;;:::,,.                             
 */ 
