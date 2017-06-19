package com.galaxy.ggolf.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.dao.UmengDAO;
import com.galaxy.ggolf.domain.Umeng;

//@Consumes("multipart/form-data")
@Produces("application/json")
@Path("/Umeng")
public class UmengService extends BaseService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final UmengDAO umengDAO;
	
	public UmengService(UmengDAO umengDAO) {
		this.umengDAO = umengDAO;
	}
	
//	@POST
//	@Path("/test")
//	public String test(String data,
//			@Context HttpHeaders headers) {
//		try {
//			
//		} catch (Exception e){
//			e.printStackTrace();
//			logger.error("Error occured",e);
//		}
//		return getErrorResponse();
//	}
	
	/**
	 * 保存手机设备的token
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/saveToken")
	public String saveToken(String data,
			@Context HttpHeaders headers) {
		try {
			Umeng um = super.fromGson(data, Umeng.class);//客户端传递的token资料
			
			Umeng uToken = this.umengDAO.getByToken(um.getUmeng_Token());//查看有无相同token存在
			
			Umeng userToken = this.umengDAO.getByUserID(um.getUserID());//查看有无相同用户存在
			
			String result = "";
			
			if(uToken==null){//查无这个token时,根据以下条件作出创建或者修改
				
				if(userToken==null){//查无该用户存在时,直接创建设备token
					
					result = create(um.getUserID(),um.getUmeng_Token());
					
				}else{//用户在更换手机设备时,覆盖该用户之前设备token
					
					result = updateToken(um.getUserID(),um.getUmeng_Token());
				}
			}else{//当查出相匹配的token时,根据以下条件作出修改或创建
				if(!um.getUserID().equals(uToken.getUserID())){
					
					if(!StringUtils.isEmpty(um.getUserID())&&
							StringUtils.isEmpty(uToken.getUserID())){//当um的UserID不为空和uToken为空时,修改当前token的用户编号
						
						result = updateUserID(um.getUserID(),um.getUmeng_Token());
						
					}else if(!StringUtils.isEmpty(um.getUserID())&&
							!StringUtils.isEmpty(uToken.getUserID())){//当有两用户拥有相同token,另新创建一条数据
						
						if(userToken==null){
							
							result = create(um.getUserID(),um.getUmeng_Token());
							
						}
						
					}
					
				}
				
			}
			return getResponse(result);
		} catch (Exception e){
			e.printStackTrace();
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	private String create(String UserID,String Umeng_Token){
		String result = "";
		if(this.umengDAO.create(UserID, Umeng_Token)){
			result = "token创建成功";
		}else{
			result = "token创建失败";
		}
		return result;
	}
	
	private String updateUserID(String UserID,String Umeng_Token){
		String result = "";
		if(this.umengDAO.updateUserID(UserID, Umeng_Token)){
			result = "修改用户编号成功";
		}else{
			result = "修改用户编号失败";
		}
		return result;
	}
	
	private String updateToken(String UserID,String Umeng_Token){
		String result = "";
		if(this.umengDAO.updateToken(UserID, Umeng_Token)){
			result = "修改用户token成功";
		}else{
			result = "修改用户token失败";
		}
		return result;
	}
	
	@GET
	@Path("/getAll")
	public String getAll(@Context HttpHeaders headers) {
		try {
			return getResponse(this.umengDAO.getAll());
		} catch (Exception e){
			e.printStackTrace();
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	@GET
	@Path("/getByUserID")
	public String getByUserID(@FormParam("UserID") String UserID,
			@Context HttpHeaders headers) {
		try {
			return getResponse(this.umengDAO.getByUserID(UserID));
		} catch (Exception e){
			e.printStackTrace();
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
}
