package com.galaxy.ggolf.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.domain.User;
import com.galaxy.ggolf.domain.UserDetail;
import com.galaxy.ggolf.jdbc.CommonConfig;
import com.galaxy.ggolf.manager.UserDetailManager;
import com.galaxy.ggolf.manager.UserManager;
import com.galaxy.ggolf.tools.FileUtil;

//@Consumes("multipart/form-data")
@Produces("application/json")
@Path("/UserDetail")
public class UserDetailService extends BaseService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final UserDetailManager manager;
	
	private final UserManager userManager;
	
	public UserDetailService(UserDetailManager manager,UserManager userManager){
		this.manager = manager;
		this.userManager = userManager;
	}
	
	@GET
	@Path("/getUserDetail")
	public String getUserDetail(@FormParam("UserID") String userID,@Context HttpHeaders headers){
		try {
			UserDetail userDetail  = this.manager.getUserDetailByUserID(userID);
			if(userDetail != null){
				/*if(userDetail.getHeadPhoto()!=null){
					String photo = userDetail.getHeadPhoto();
					String imgFilePath = CommonConfig.FILE_UPLOAD_PATH + photo.substring(photo.lastIndexOf("/")+1, photo.length());
					Collection<String> photoList = new ArrayList<String>();
					photoList.add(ImageBase64.GetImageStr(imgFilePath));
					userDetail.setPhotoList(photoList);
				}*/
				return getResponse(userDetail);
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	@POST
	@Path("/saveUserDetail")
	public String saveUserDetail(String data, @Context HttpHeaders headers){
		logger.debug("data---{}",data);
		try {
			UserDetail userDetail = super.fromGson(data, UserDetail.class);
//			if(userDetail.getPhotoList()!=null&&userDetail.getPhotoList().size()>0){
//				Collection<String> photoList = getPhotoList(userDetail);
//				if(photoList!=null&&photoList.size()>0){
//					userDetail.setPhotoList(photoList);
//				}
//			}
			if(this.manager.getUserDetailByUserID(userDetail.getUserID())==null){
				this.manager.createUserDetail(userDetail);
			}else{
				this.manager.updateUserDetail(userDetail);
			}
			User user = new User(userDetail.getUserID(), userDetail.getPhoneNum(), userDetail.getUserName(), userDetail.getAge(), userDetail.getSex(), userDetail.getHeadPhoto());
			this.userManager.updateUser(user);
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	public Collection<String> getPhotoList(UserDetail user){
		Collection<String> pList = new ArrayList<String>();
		String userid = user.getUserID();
		String path = CommonConfig.USER_PATH + CommonConfig.UserPhotoList + "\\" + userid + "\\";
		String fileName = "";
		String filePath = "";
		String imageUrl = "";
		if(user.getPhotoList()!=null && user.getPhotoList().size()>0){
			for(String img : user.getPhotoList()){
				SimpleDateFormat sdf = new SimpleDateFormat(CommonConfig.DateFormatNum);
				fileName = CommonConfig.UserPhotoList + "_" + sdf.format(new Date()) + ".png";
				filePath = path;
				boolean result = FileUtil.GetImageUrl(img,fileName,filePath);
				if(result){
					imageUrl = CommonConfig.CONNECT + CommonConfig.FILE_DOWNLOAD + CommonConfig.UserPhotoList + "_" + userid + "_" + fileName ;
					pList.add(imageUrl);
				}
			}
			return pList;
		}
		return null;
	}
	
	/**
	 * 保存图片
	 * @param photo
	 * @param phone
	 * @return
	 */
	@POST
	@Path("/savePhotos")
	public String savePhoto(String data, @Context HttpHeaders headers){
		try {
			UserDetail userDetail = fromGson(data, UserDetail.class);
			Collection<String> photoList = getPhotoList(userDetail);
			if(photoList != null && photoList.size() > 0){
				userDetail.setPhotoList(userDetail.getPhotoList());
				if(this.manager.checkUserDetail(userDetail.getUserID())){
					this.manager.updatePhotoList(userDetail.getPhotoList(), userDetail.getUserID());
				}else{
					this.manager.createPhoto(userDetail.getPhotoList(), userDetail.getUserID());
				}
				return getSuccessResponse();
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	
	
	

}
