package com.galaxy.ggolf.rest;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.domain.User;
import com.galaxy.ggolf.dto.LBSInfo;
import com.galaxy.ggolf.dto.Location;
import com.galaxy.ggolf.jdbc.CommonConfig;
import com.galaxy.ggolf.manager.LocationSessionManager;
import com.galaxy.ggolf.manager.MessageManager;
import com.galaxy.ggolf.manager.UserManager;


//@Consumes("multipart/form-data")
@Produces("application/json")
@Path("/Invite")
public class InviteService extends BaseService{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private UserManager manager;
	
	private MessageManager messageManager;
	
	public InviteService(LocationSessionManager locationSessionManager,UserManager manager,MessageManager messageManager){
		super.setLocationSessionManager(locationSessionManager);
		this.manager = manager;
		this.messageManager = messageManager;
	}
	
	//保存地理位置
	@POST
	@Path("/saveLocation")
	public String saveLocation(@FormParam("UserID") String userID,
					@FormParam("Location") String Location,
					@Context HttpHeaders headers){
		String result = "";
		try {
			try{ verifyUser(headers); } catch (Exception e) {return getErrorResponse("登录已过期,请重新登录");}
			User user = this.manager.getUserByUserID(userID);
			String token = headers.getHeaderString("auth");
			if (user != null) {
				result = "位置信息保存成功";
				Location location = new Location(userID,Location,result);
				locationSessionManager.putAuth(token, location);
				return getResponse(location);
			} else {
				result = "该用户不存在,请尝试退出,重新登录";
				return getErrorResponse(result);
			}
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			result = "获取位置失败";
			return getErrorResponse(result);
		}
	}
	//手动清除地理位置
	@POST
	@Path("/cleanLocation")
	public String cleanLocation(@Context HttpHeaders headers){
		String result = "";
		try {
			try{ 
				if(verify(headers)!=null){
					String token = verify(headers);
				}
			} catch (Exception e) {
				return getErrorResponse("登录已过期,请重新登录");
			}
			String token = headers.getHeaderString("auth");
			locationSessionManager.clean(token);
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error occured", e);
			result = "清除位置失败";
			return getErrorResponse(result);
		}
	}
	
	@POST
	@Path("/getMessageByLocation")
	public String getMessageByLocation(@FormParam("Longitude") String Longitude,
					@FormParam("Latitude") String Latitude,
					@FormParam("Radius") String Radius,
					@Context HttpHeaders headers){
		String result = "";
		try{ 
			if(verify(headers)!=null){
				String token = verify(headers);
			}
		} catch (Exception e) {
			return getErrorResponse("登录已过期,请重新登录");
		}
		try {
			Collection<Location> locList = locationSessionManager.getLocationUsers();
			for(Location loc : locList){
				User user = this.manager.getUserByUserID(loc.getUserID());
			}
			String site = getLocation(Longitude+","+Latitude);
		} catch (Exception e) {
			result="获取信息失败";
			return getErrorResponse(result);
		}
		return getErrorResponse();
	}
	
	public String getLocation(String gps) {
		String result = "";
		BufferedReader in = null;
		try {
			String requestUrl = CommonConfig.GAODEURL.replaceAll("<gps>", gps);
			URL realUrl = new URL(requestUrl);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			logger.debug("gpsinfo:{}", result);
			LBSInfo lbsInfo = fromGson(result, LBSInfo.class);
			if (lbsInfo.getInfo().equals("OK"))
				return lbsInfo.getRegeocode().getFormatted_address();
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return "";
	}
	
}
