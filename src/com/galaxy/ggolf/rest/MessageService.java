package com.galaxy.ggolf.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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

import com.galaxy.ggolf.domain.Comment;
import com.galaxy.ggolf.domain.Likes;
import com.galaxy.ggolf.domain.Message;
import com.galaxy.ggolf.domain.User;
import com.galaxy.ggolf.dto.CommentData;
import com.galaxy.ggolf.dto.Location;
import com.galaxy.ggolf.manager.CommentManager;
import com.galaxy.ggolf.manager.LocationSessionManager;
import com.galaxy.ggolf.manager.MessageManager;
import com.galaxy.ggolf.manager.UserManager;
import com.galaxy.ggolf.tools.LocationUtil;
import com.spatial4j.core.shape.Rectangle;

//@Consumes("multipart/form-data")
@Produces("application/json")
@Path("/Message")
public class MessageService extends BaseService{
	
	private MessageManager manager;
	
	private UserManager userManager;
	
	private CommentManager commentManager;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public MessageService(LocationSessionManager locationSessionManager, MessageManager manager,UserManager userManager,
			CommentManager commentManager){
		super.setLocationSessionManager(locationSessionManager);
		this.manager = manager;
		this.userManager = userManager;
		this.commentManager = commentManager;
	}
	
	/**
	 * 保存动态,约球,系统信息
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/saveMessage")
	public String saveMessage(String data,@Context HttpHeaders headers){
		Message ms = fromGson(data, Message.class);
		try {
			if(ms.getType().equals("约球")){
				if(ms.getLongitude()!=null && ms.getLatitude()!=null && ms.getRadius()!=null){
					double lon = Double.parseDouble(ms.getLongitude());
					double lat = Double.parseDouble(ms.getLongitude());
					int radius = Integer.parseInt(ms.getRadius());
					Rectangle rec = LocationUtil.GetRange(lon, lat, radius); 
					Collection<User> nearUserList = this.userManager.getNearByUser(rec, null);
					Collection<User> userList = new ArrayList<User>();
					Collection<Location> locs = locationSessionManager.getLocationUsers();//获取在线人员编号
					Iterator<User> it = nearUserList.iterator();
					while(it.hasNext()){
						User user = it.next();
						for(Location loc : locs){
							if(user.getUserID().equals(loc.getUserID())){
								userList.add(user);
							}
						}
					}
					ms.setUserList(userList);
				}else{
					return getErrormessage("请输入经纬度和位置范围");
				}
				if(!ms.getClub().equals("球场")||!ms.getClub().equals("练习场")){
					return getErrormessage("请输入球场的类别");
				}
			}
			if(ms.getType().equals("系统")){
				if(ms.getReleaseOrNot()!=null && ms.getReleaseOrNot().equalsIgnoreCase("Y")){
					Collection<User> allUser = this.userManager.getAll();//目前先写成这样,若日后有订阅功能或选择用户发送再修改
					ms.setUserList(allUser);
				}
			}
			String result = this.manager.saveMsg(ms);
			if(result.equals("")){
				return getSuccessResponse();
			}else{
				return getResponse(result);
			}
			
		} catch (Exception e) {
			logger.error("Error", e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 获取相关类型信息
	 * @param keyword
	 * @param rows
	 * @param pageNum
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getMessages{days}")
	public String getMessages(@PathParam("days") int days,
			@FormParam("type") String type,
			@FormParam("keyword") String keyword,
			@FormParam("rows") String rows,
			@FormParam("pageNum") String pageNum, @Context HttpHeaders headers){
		try {
			return getResponse(this.manager.groupSearch(type, keyword, rows, days));
		} catch (Exception e) {
			logger.error("Error",e);
		}
		return getErrorResponse();
	}
	/**
	 * 根据关键字搜索相关类型的信息
	 * @param keyword
	 * @param rows
	 * @param type
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getSearch")
	public String getSearch(@FormParam("keyword") String keyword,
			@FormParam("rows") String rows, 
			@FormParam("type") String type,
			@Context HttpHeaders headers){
		try {
			return getResponse(this.manager.getSearch(type, keyword, rows));
		} catch (Exception e) {
			logger.error("Error",e);
		}
		return getErrorResponse();
	}
	
	
	/**
	 * 获取用户的信息列表
	 * @param UserID
	 * @param Type
	 * @param keyword
	 * @param rows
	 * @param pageNum
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getMessageByUserID")
	public String getMessageByUserID(@FormParam("UserID") String UserID,
			@FormParam("keyword") String keyword,
			@FormParam("rows") String rows,
			@FormParam("type") String type,
			@FormParam("pageNum") String pageNum,@Context HttpHeaders headers){
		try {
			return getResponse(this.manager.getByUserID(UserID, type, keyword, rows, pageNum));
		} catch (Exception e) {
			logger.error("Error",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 查看
	 * @param MsgID
	 * @return
	 */
	@GET
	@Path("/getMessageByMsgID")
	public String getMessageByMsgID(@FormParam("MsgID") String MsgID){
		try {
			return getResponse(this.manager.getMessage(MsgID));
		} catch (Exception e) {
			logger.error("Error",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 通知是否阅读(约球)
	 * @param NotifyID
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/checkRead")
	public String checkRead(@FormParam("NotifyID") String NotifyID, @Context HttpHeaders headers){
		try {
			if(this.manager.updateReadOrNot(NotifyID)){
				return getSuccessResponse();
			}
		} catch (Exception e) {
			logger.error("Error",e);
		}
		return getErrorResponse();
	}
	
	//删除信息
	@POST
	@Path("/removeMsg")
	public String remove(@FormParam("MsgID") String MsgID,@Context HttpHeaders headers){
		try {
			this.manager.delete(MsgID);
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error",e);
		}
		return getErrorResponse();
	}
	/**
	 * 获取当前信息评论
	 * @param rows
	 * @param MsgID
	 * @param Type
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getCommentByMsgID")
	public String getCommentByMsgID(@FormParam("rows") String rows,
			@FormParam("MsgID") String MsgID,
			@FormParam("Type") String Type,
			@Context HttpHeaders headers){
		try {
			return getResponse(this.commentManager.getByRoot(null, rows, MsgID, Type));
		} catch (Exception e) {
			logger.error("Error",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 对信息发表评论
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/saveComment")
	public String saveComment(String data,@Context HttpHeaders headers){
		try {
			Comment c = super.fromGson(data, Comment.class);
			this.commentManager.create(c);
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 为评论点赞
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/likeForComment")
	public String likeForComment(String data,@Context HttpHeaders headers){
		try {
			Likes like = super.fromGson(data, Likes.class);
			if(this.commentManager.like(like)){
				return getSuccessResponse();
			}else{
				return getErrormessage("你已点赞");
			}
		} catch (Exception e) {
			logger.error("Error",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 删除评论
	 * @param CommentID
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/removeComment")
	public String removeComment(@FormParam("CommentID") String CommentID,
			@Context HttpHeaders headers){
		try {
			this.commentManager.delete(CommentID);
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 用户获取相关评论(回复)下的回复
	 * @param rows
	 * @param UserID
	 * @param CommentID
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getReplyList")
	public String getReplyList(@FormParam("rows") String rows,
			@FormParam("UserID") String UserID,
			@FormParam("CommentID") String CommentID,
			@Context HttpHeaders headers){
		try {
			return getResponse(this.commentManager.getReplyList(rows, UserID, CommentID));
		} catch (Exception e) {
			logger.error("Error",e);
		}
		return getErrorResponse();
	}
	
}
