package com.galaxy.ggolf.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.galaxy.ggolf.domain.GalaxyLabError;
import com.galaxy.ggolf.jdbc.CommonConfig;
import com.galaxy.ggolf.manager.LocationSessionManager;
import com.galaxy.ggolf.manager.SessionManager;
import com.galaxy.ggolf.manager.StaffSessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class BaseService {

	private static final Gson gson = new GsonBuilder().setPrettyPrinting()
			.create();

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SessionManager sessionManager;
	
	protected StaffSessionManager staffSessionManager;
	
	protected LocationSessionManager locationSessionManager;

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}
	
	public void setStaffSessionManager(StaffSessionManager staffSessionManager){
		this.staffSessionManager = staffSessionManager;
	}
	
	public void setLocationSessionManager(LocationSessionManager locationSessionManager){
		this.locationSessionManager = locationSessionManager;
	}

	protected String getResponse(Object obj) {
		return gson.toJson(obj);
	}

	public <E> E fromGson(String data, Class<E> clazz) {
		return gson.fromJson(data, clazz);
	}

	public static <T> List<T> fromJsonArray(String json, Class<T> clazz) throws Exception {  
		List<T> lst =  new ArrayList<T>();    
		JsonArray array = new JsonParser().parse(json).getAsJsonArray();    
		for(final JsonElement elem : array){  
			lst.add(gson.fromJson(elem, clazz));  
			}       
		return lst;   
	}

	public String getSuccessResponse() {
		return gson.toJson(new Response("Success"));
	}

	public String getErrorResponse() {
		return gson.toJson(new Response("Error"));
	}
	
	public String getErrorResponse(String errorMsg) {
		GalaxyLabError e = new GalaxyLabError(errorMsg);
		return gson.toJson(e);
	}
	
	public String getErrormessage(String message) {
		return gson.toJson(new Response(message));
	}
	
	public void verifyUser(@Context HttpHeaders headers){
		String token = headers.getHeaderString("auth");
		if(token == null || sessionManager.getUser(token)==null ){
			throw new java.lang.IllegalStateException("Session is expired or System is being hacked");
		}
	}
	
	public void verifyStaff(@Context HttpHeaders headers){
		String token = headers.getHeaderString("auth");
		if(token == null || staffSessionManager.getStaff(token)==null ){
			throw new java.lang.IllegalStateException("Session is expired or System is being hacked");
		}
	}
	
	public String verify(@Context HttpHeaders headers){
		String token = headers.getHeaderString("auth");
		if(sessionManager.getUser(token)!=null || staffSessionManager.getStaff(token)!=null){
			return token;
		}else{
			throw new java.lang.IllegalStateException("Session is expired or System is being hacked");
		}
	}
	
	public boolean verifyApp(@Context HttpHeaders headers){
		boolean result = false;
		String AppName = headers.getHeaderString("user-agent");
		if(AppName.indexOf(CommonConfig.APP_NAME)!=-1){
			result = true;
		}
		return result;
	}

	public String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(StringUtils.isEmpty(ip) || "unKnown".equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(StringUtils.isEmpty(ip) || "unKnown".equalsIgnoreCase(ip)){
			ip = request.getHeader("WL-PrOXY-Client-IP");
		}
		if(StringUtils.isEmpty(ip) || "unKnown".equalsIgnoreCase(ip)){
			ip = request.getRemoteAddr();
		}
		logger.info("本次访问的用户IP地址:"+ip);
		return ip;
	}
	

}



class Response {
	public String status;
	public String message;

	public Response(String status) {
		this.status = status;
	}
}
