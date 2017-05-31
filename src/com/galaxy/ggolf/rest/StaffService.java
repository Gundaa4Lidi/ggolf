package com.galaxy.ggolf.rest;

import java.io.File;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.domain.Staff;
import com.galaxy.ggolf.dto.StaffData;
import com.galaxy.ggolf.dto.StaffSessionData;
import com.galaxy.ggolf.jdbc.CommonConfig;
import com.galaxy.ggolf.manager.StaffManager;
import com.galaxy.ggolf.manager.StaffSessionManager;
import com.galaxy.ggolf.tools.CipherUtil;
import com.galaxy.ggolf.tools.FileUtil;

//@Consumes("multipart/form-data")
@Produces("application/json")
@Path("/Staff")
public class StaffService extends BaseService{

	private final StaffManager manager;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public StaffService(StaffManager manager, StaffSessionManager staffSessionManager) {
		super.setStaffSessionManager(staffSessionManager);
		this.manager = manager;
	}
	/**
	 * 保存管理员
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/")
	public String saveStaff(String data, @Context HttpHeaders headers) {
		Staff staff = super.fromGson(data, Staff.class);
		try {
//			logger.debug("头像---------{}",staff.getHead());
			staff.setHead(getHeadUrl(staff));
			this.manager.saveStaff(staff);
			return getSuccessResponse();
		} catch (GalaxyLabException ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	@POST
	@Path("/saveHead")
	public String saveHead(String data, @Context HttpHeaders headers){
		Staff staff = super.fromGson(data, Staff.class);
		try {
			staff.setHead(getHeadUrl(staff));
			this.manager.saveHead(staff);
			Staff staff1 = this.manager.getStaff(staff.getStaffID());
			return getResponse(staff1);
		} catch (GalaxyLabException ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	
	
	public String getHeadUrl(Staff staff){
		try {
			String fileName = CommonConfig.StaffHead + "@" + staff.getStaffID() + ".png";
			String filePath = CommonConfig.STAFF_PATH + CommonConfig.StaffHead + "\\";
			String head = staff.getHead();
			if(head != null){
				File img = new File(filePath + fileName);
				FileUtil.deleteFile(img);
				boolean result = FileUtil.GetImageUrl(head, fileName, filePath);
				if(result){
					SimpleDateFormat sdf = new SimpleDateFormat(CommonConfig.DateFormatNum);
					String url = CommonConfig.CONNECT + CommonConfig.FILE_DOWNLOAD + "staff_" +
							CommonConfig.StaffHead + "_" + fileName +"?t="+sdf.format(new Date());
					return url;
 				}else{
 					return head;
 				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@POST
	@Path("/update")
	public String updatepassword(@FormParam("StaffID") String StaffID,
			@FormParam("password") String Password,
			@FormParam("newpassword") String newPassword,
			@Context HttpHeaders headers) {
		
		try {
			logger.info("headers--------{}",headers.getHeaderString("auth"));
			Staff staff = this.manager.logonStaff(StaffID);
			String result = "";
			if(staff!=null){
			if(CipherUtil.validatePassword(staff.getPassword(),Password)){
				this.manager.updateStaffPassword(StaffID, newPassword);
				result = "密码更新成功";
				return getResponse(result);
			}else{
				result = "密码错误";
				return getResponse(result);
			}
			}else{
				result = "该用户不存在";
				return getResponse(result);
			}
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	
	@POST
	@Path("/logon")
	public String logon(@FormParam("StaffID") String StaffID,
				@FormParam("password") String Password, @Context HttpHeaders headers) {
		String result = "";
		try {
			Staff staff =  this.manager.logonStaff(StaffID);
			if(staff!=null){
				if(CipherUtil.validatePassword(staff.getPassword(),Password)){
					SecureRandom random = new SecureRandom();
					byte bytes[] = new byte[20];
					random.nextBytes(bytes);
					String token = bytes.toString()
							+ CipherUtil.generatePassword(staff.getStaffID());
					this.staffSessionManager.putAuth(token, staff);
					StaffSessionData ssd = new StaffSessionData(token, staff.getStaffID(), staff.getPosition(), staff.getStaffName(), staff.getHead(), staffSessionManager.getCount()+"", staffSessionManager.getOnlineStaff());
					//return "{\"token\":\""+token+"\",\"StaffId\":\""+staff.getStaffID()+"\",\"Position\":\""+staff.getPosition()+"\",\"Staffname\":\""+staff.getStaffName()+"\",\"StaffHead\":\""+staff.getHead()+"\",\"OnlineCount\":\""+staffSessionManager.getCount()+"\",\"OnlineStaff\":\""+staffSessionManager.getOnlineStaff()+"\"}";
					return getResponse(ssd);
				}else{
					result = "密码错误";
					return getResponse(result);
				}
			}else{
				result = "该用户不存在";
				return getResponse(result);
			}

		}  catch (Exception ex) {
			logger.error("Error occured", ex);
			result = "登录失败";
			return getErrorResponse(result);
		}
		
	}
	
	@POST
	@Path("/delete/{StaffID}")
	public String deleteStaff(@PathParam("StaffID") String StaffID, @Context HttpHeaders headers) throws Exception{
		
		if(this.manager.deleteStaff(StaffID)){
			return getSuccessResponse();
		}
		return getErrorResponse();
	}
	
	
	@GET
	@Path("/CheckStaffID")
	public String CheckStaffID(@FormParam("StaffID") String StaffID, @Context HttpHeaders headers) {
		return getResponse(this.manager.getStaff(StaffID));
	}
	
	@GET
	@Path("/GetAll")
	public String GetAll(
			@FormParam("keyword") String keyword,
			@FormParam("rows") String rows,
			@FormParam("pageNum") String pageNum,
			@Context HttpHeaders headers) {
		try {
			String sqlString = search(keyword);
			Collection<Staff> data = this.manager.getAll(sqlString, rows, pageNum);
			int count = this.manager.getCount(sqlString);
			StaffData staffData = new StaffData(count,data);
			return getResponse(staffData);
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
		
	}
	
	//搜索关键字
	public String search(String keyword){
		String sqlString = "";
		if(keyword!=null&&!keyword.equals("")&&!keyword.equalsIgnoreCase("null")){
			sqlString = "and (StaffName like '%"
					+ keyword
					+ "%' or Phone like '%"
					+ keyword
					+ "%' or StaffID like '%"
					+ keyword 
					+ "%' or Position like '%"
					+ keyword 
					+ "%' or WorkPlace like '%"
					+ keyword 
					+ "%' or date_format(`Created_TS`,'%Y-%m-%d') like '%"
					+ keyword +"%') ";
		}
		return sqlString;
	}
}
