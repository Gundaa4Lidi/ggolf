package com.galaxy.ggolf.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.cache.GenericCache;
import com.galaxy.ggolf.dao.CommentDAO;
import com.galaxy.ggolf.dao.LikeDAO;
import com.galaxy.ggolf.dao.MessageDAO;
import com.galaxy.ggolf.dao.NotifyListDAO;
import com.galaxy.ggolf.dao.UserDAO;
import com.galaxy.ggolf.domain.Comment;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.domain.Likes;
import com.galaxy.ggolf.domain.Message;
import com.galaxy.ggolf.domain.NotifyList;
import com.galaxy.ggolf.domain.User;
import com.galaxy.ggolf.dto.CommentData;
import com.galaxy.ggolf.dto.GroupData;
import com.galaxy.ggolf.dto.LikeData;
import com.galaxy.ggolf.dto.MessageData;
import com.galaxy.ggolf.dto.MessageGroupData;
import com.galaxy.ggolf.dto.UserMessageData;
import com.galaxy.ggolf.jdbc.CommonConfig;
import com.galaxy.ggolf.tools.LocationUtil;
import com.galaxy.ggolf.tools.ListUtil;
import com.spatial4j.core.shape.Rectangle;

public class MessageManager {
	
	public GenericCache<String, Message> cache;
	public MessageDAO messageDAO;
	public UserDAO userDAO;
	public NotifyListDAO notifyListDAO;
	public CommentDAO commentDAO;
	public LikeDAO likeDAO;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public MessageManager(MessageDAO messageDAO, UserDAO userDAO,
			NotifyListDAO notifyListDAO,CommentDAO commentDAO,
			LikeDAO likeDAO){
		this.cache = new GenericCache<String,Message>();
		this.messageDAO = messageDAO;
		this.notifyListDAO = notifyListDAO;
		this.userDAO = userDAO;
		this.commentDAO = commentDAO;
		this.likeDAO = likeDAO;
	}
	/**
	 * 获取全部信息
	 * @param keyword
	 * @param rows
	 * @param pageNum
	 * @return
	 * @throws Exception
	 */
	public UserMessageData getAll(String keyword, String rows, String pageNum)throws Exception{
		String sqlString = "";
		if(keyword != null){
			sqlString += "and"
					+ "(SenderID like '%"
					+ keyword 
					+ "%' or SenderName like '%"
					+ keyword 
					+ "%' or Title like '%"
					+ keyword 
					+ "%' or Details like '%"
					+ keyword 
					+ "%' or Type like '%"
					+ keyword 
					+ "%' or date_format(`Created_TS`,'%Y-%m-%d') like '%"
					+ keyword 
					+ "%') ";
		}
		if(pageNum == null){
			pageNum = "1";
		}
		Collection<Message> message = this.messageDAO.getAll(sqlString, rows, pageNum);
		int count = this.messageDAO.getCount(sqlString);
		UserMessageData userMessageData = new UserMessageData(message, count);
		return userMessageData;
	}
	
	/**
	 * 根据关键字搜索相关类型的信息
	 * @param Type
	 * @param keyword
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	public UserMessageData getSearch(String Type,String keyword, String rows)throws Exception{
		String sqlString = "";
		if(Type!=null&&Type.equalsIgnoreCase("null")){
			sqlString += "and Type='"+Type+"'";
		}
		if(keyword!=null&&keyword.equalsIgnoreCase("null")){
			sqlString += "and"
					+ "(SenderID like '%"
					+ keyword 
					+ "%' or SenderName like '%"
					+ keyword 
					+ "%' or Title like '%"
					+ keyword 
					+ "%' or Details like '%"
					+ keyword 
					+ "%' or date_format(`Created_TS`,'%Y-%m-%d') like '%"
					+ keyword 
					+ "%') ";
		}
		Collection<Message> message = this.messageDAO.getSearch(rows, sqlString);
		for(Message ms : message){
			if(ms.getType().equals(CommonConfig.MSG_TYPE_DYNAMIC)){
				String comRows = "5";
				Collection<Comment> com = commentDAO.getCommentByApp(comRows, "", ms.getMsgID(), ms.getType());
				int count = this.commentDAO.getCountByApp("", ms.getMsgID(),ms.getType());
				if(com!=null&&com.size()>0){
					CommentData cd = new CommentData(count, com);
					ms.setCommentData(cd);//添加评论
				}
				
			}
		}
		int count = this.messageDAO.getSearchCount(sqlString, Type);
		UserMessageData userMessageData = new UserMessageData(message, count);
		return userMessageData;
	}
	
	/**
	 * 按时间分组查询相关类型信息
	 * @param Type
	 * @param keyword
	 * @param rows
	 * @param days
	 * @return
	 * @throws Exception
	 */
	public MessageGroupData groupSearch(String Type, String keyword, String rows, int days)throws Exception{
		String sqlString = "";
		int row = Integer.parseInt(rows);
		if(Type!=null&&!Type.equalsIgnoreCase("null")){
			if(Type.equalsIgnoreCase(CommonConfig.MSG_TYPE_SYS)){
				sqlString += "and Type='"+Type+"'";
			}else if(Type.equalsIgnoreCase(CommonConfig.MSG_TYPE_NOTSYS)){
				sqlString += "and Type!='"+CommonConfig.MSG_TYPE_SYS+"'";
			}
		}
		Collection<GroupData<Message>> Data = new ArrayList<GroupData<Message>>();
		if(keyword!=null&&!keyword.equalsIgnoreCase("null")){
			sqlString += "and"
					+ "(SenderID like '%"
					+ keyword 
					+ "%' or SenderName like '%"
					+ keyword 
					+ "%' or Title like '%"
					+ keyword 
					+ "%' or Details like '%"
					+ keyword 
					+ "%' or date_format(`Created_TS`,'%Y-%m-%d') like '%"
					+ keyword 
					+ "%') ";
		}
		DateTime time = DateTime.now().minusDays(days);
		if(days > 0){
			sqlString += "and Created_TS > '"+time.toString("yyyy-MM-dd")+"'";
		}
		Collection<Message> ct = this.messageDAO.getDTGroup(sqlString,rows);
		int searchCount = this.messageDAO.getSearchCount(sqlString, Type);
		if(ct.size()>0){
			
			for(Message msg : ct){
				String date = msg.getCreated_TS().substring(0, 10);
				
				String dateFormat = "and date_format(Created_TS,'%Y-%m-%d')='"+date+"'";
				
				Collection<Message> result = this.messageDAO.getSearch(row+"", sqlString+dateFormat);
				if(result.size() <= row && row > 0){
					for(Message ms : result){
						if(ms.getType().equalsIgnoreCase(CommonConfig.MSG_TYPE_DYNAMIC)
								||ms.getType().equalsIgnoreCase(CommonConfig.MSG_TYPE_SYS)){
//							String comRows = "5";
//							Collection<Comment> com = commentDAO.getCommentByApp(comRows, "", ms.getMsgID(), ms.getType());
//							int count = this.commentDAO.getCountByApp("", ms.getMsgID(), ms.getType());
//							if(com!=null&&com.size()>0){
//								CommentData cd = new CommentData(count, com);
//								ms.setCommentData(cd);//添加评论
//							}
							Collection<User> likeUsers = this.userDAO.getLikeList(ms.getMsgID(), ms.getType());
							if(likeUsers!=null&&likeUsers.size() > 0){
								int likeCount = this.likeDAO.getCountByThemeID(ms.getType(), ms.getType());
								LikeData ld = new LikeData(likeCount, likeUsers);
								ms.setLikeData(ld);//添加点赞
							}
						}
					}
					row -=result.size();
					GroupData<Message> message = new GroupData<Message>(date,result);
					Data.add(message);
				}
			}
		}
		MessageGroupData mgd = new MessageGroupData(searchCount, Data);
		return mgd;
		
	}
	
	/**
	 * 获取当前信息评论
	 * @param rows
	 * @param MsgID
	 * @param Type
	 * @return
	 * @throws Exception
	 */
	public CommentData getCommentByMsgID(String rows,String MsgID,String Type)throws Exception{
		Collection<Comment> com = commentDAO.getCommentByApp(rows, "", MsgID, Type);
		for(Comment c : com){
			int likeCount = this.likeDAO.getCountByThemeID(c.getCommentID(), c.getAction());
			c.setLikeCount(likeCount);
		}
		int count = this.commentDAO.getCountByApp("", MsgID, Type);
		CommentData cd = new CommentData(count, com);
		return cd; 
	}
	
	/**
	 * 保存信息
	 * @param ms
	 * @throws Exception
	 */
	public String saveMsg(Message ms)throws Exception{
		String result = "";
		if(ms.getMsgID()!=null){
			if(!updateMessage(ms)){
				result = "此信息有误或已发布,不能修改";
			}
		}else{
			if(!createMessage(ms)){
				result = "创建信息失败";
			}
		}
		return result;
	}
	
	/**
	 * 创建约球,动态,系统信息
	 * @param ms
	 * @return
	 * @throws Exception
	 */
	public boolean createMessage(Message ms)throws Exception{
		boolean result = false;
		String msgID = messageDAO.getMsgID()+"";
		ms.setMsgID(msgID);
		if(messageDAO.create(ms)){
			if(ms.getReleaseOrNot()!=null && ms.getReleaseOrNot().equalsIgnoreCase("Y")){
				Collection<User> userLists = ms.getUserList();//信息要发送到的用户列表
				if(userLists!=null && userLists.size() > 0){
					if(!notifyListDAO.create(userLists, ms.getMsgID())){//创建通知列表
						throw new GalaxyLabException("Error in create NotifyList");
					}
				}
			}
			result = true;
		}
		return result;
	}
	/**
	 * 修改信息
	 * @param ms
	 * @return
	 * @throws Exception
	 */
	public boolean updateMessage(Message ms)throws Exception{
		boolean result = false;
		Message msg = this.messageDAO.getBy(ms.getMsgID());
		if(ms.getReleaseOrNot()!=null &&msg.getReleaseOrNot().equalsIgnoreCase("Y")){
			return result;
		}
		String sqlString = "";
		if(ms.getSenderID()!=null&&!ms.getSenderID().equalsIgnoreCase("null")){
			sqlString = sqlString + "SenderID='"+ms.getSenderID()+"',";
		}
		if(ms.getSenderName()!=null&&!ms.getSenderName().equalsIgnoreCase("null")){
			sqlString = sqlString + "SenderName='"+ms.getSenderName()+"',";
		}
		if(ms.getSenderPhoto()!=null&&!ms.getSenderPhoto().equalsIgnoreCase("null")){
			sqlString = sqlString + "SenderPhoto='"+ms.getSenderPhoto()+"',";
		}
		if(ms.getTitle()!=null&&!ms.getTitle().equalsIgnoreCase("null")){
			sqlString = sqlString + "Title='"+ms.getTitle()+"',";
		}
		if(ms.getDetails()!=null&&!ms.getDetails().equalsIgnoreCase("null")){
			sqlString = sqlString + "Details='"+ms.getDetails()+"',";
		}
		if(ms.getPhotoList()!=null&& ms.getPhotoList().size() > 0){
			String photoList = new ListUtil().ListToString(ms.getPhotoList());
			sqlString = sqlString + "PhotoList='"+photoList+"',";
		}
		if(ms.getVideo()!=null&&!ms.getVideo().equalsIgnoreCase("null")){
			sqlString = sqlString + "Video='"+ms.getVideo()+"',";
		}
		if(ms.getPeriod()!=null&&!ms.getPeriod().equalsIgnoreCase("null")){
			sqlString = sqlString + "Period='"+ms.getPeriod()+"',";
		}
		if(ms.getStatus()!=null&&!ms.getStatus().equalsIgnoreCase("null")){
			sqlString = sqlString + "Status='"+ms.getStatus()+"',";
		}
		if(ms.getType()!=null&&!ms.getType().equalsIgnoreCase("null")){
			sqlString = sqlString + "Type='"+ms.getType()+"',";
		}
		if((ms.getClub().equals("球场")||ms.getClub().equals("练习场"))&&ms.getType().equals("约球")){
			sqlString = sqlString + "Club='"+ms.getClub()+"'";
		}
		if(ms.getLongitude()!=null&&!ms.getLongitude().equalsIgnoreCase("null")){
			sqlString = sqlString + "Longitude='"+ms.getLongitude()+"',";
		}
		if(ms.getLatitude()!=null&&!ms.getLatitude().equalsIgnoreCase("null")){
			sqlString = sqlString + "Latitude='"+ms.getLatitude()+"',";
		}
		if(ms.getRadius()!=null&&!ms.getRadius().equalsIgnoreCase("null")){
			sqlString = sqlString + "Radius='"+ms.getRadius()+"',";
		}
		if(ms.getSite()!=null&&!ms.getSite().equalsIgnoreCase("null")){
			sqlString = sqlString + "Site='"+ms.getSite()+"',";
		}
		if(ms.getReleaseOrNot()!=null && ms.getReleaseOrNot().equalsIgnoreCase("null")){
			sqlString = sqlString + "ReleaseOrNot='"+ms.getReleaseOrNot()+"',";
		}
		if(this.messageDAO.updateMsg(ms, sqlString)){//修改信息表
			if(ms.getReleaseOrNot().equalsIgnoreCase("Y")&&ms.getUserList()!=null&&ms.getUserList().size()>0){
				Collection<User> userLists = ms.getUserList();//信息要发送到的用户列表
				if(!notifyListDAO.create(userLists, ms.getMsgID())){//创建通知列表
					throw new GalaxyLabException("Error in create NotifyList");
				}
			}
			result = true;
		}
		return result;
		
	}
	
	/**
	 * 获取这条信息的通知列表
	 * @param MsgID
	 * @return
	 * @throws Exception
	 */
	public MessageData getMessage(String MsgID)throws Exception{
		Message message = messageDAO.getBy(MsgID);
		Collection<NotifyList> notifyLists = notifyListDAO.getByMsgID(MsgID);
		MessageData md = new MessageData(message,notifyLists);
		return md;
	}
	
	
	
	/**
	 * 用户获取所收到的信息
	 * @param UserID
	 * @param Type
	 * @param keyword
	 * @param rows
	 * @param pageNum
	 * @return
	 * @throws Exception
	 */
	public UserMessageData getByUserID(String UserID, String Type, String keyword, String rows, String pageNum)throws Exception{
		String sqlString = "";
		if(Type != null){
			sqlString += "and Type='"+Type+"'";
		}
		if(keyword != null){
			sqlString += "and (Title like '%"
					+ keyword 
					+ "%' or Details like '%"
					+ keyword 
					+ "%' or Type like '%"
					+ keyword 
					+ "%' or date_format(`Created_TS`,'%Y-%m-%d') like '%"
					+ keyword
					+ "%')";
		}
		if(pageNum == null){
			pageNum = "1";
		}
		Collection<Message> message = this.messageDAO.getMessageByUserID(UserID, sqlString, rows, pageNum);
		int count = this.messageDAO.getCountByUserID(UserID,sqlString);
		UserMessageData userMessageData = new UserMessageData(message, count);
		return userMessageData;
	}
	
	/**
	 * 是否已读
	 * @param NotifyID
	 * @return
	 * @throws Exception
	 */
	public boolean updateReadOrNot(String NotifyID)throws Exception{
		NotifyList list = this.notifyListDAO.getByNotifyID(NotifyID);
		if(list != null){
			if(list.getReadOrNot() == "0"){
				return this.notifyListDAO.updateRead(list.getNotifyID());
			}
		}
		return false;
	}
	/**
	 * 删除信息 
	 * @param MsgID
	 * @throws Exception
	 */
	public void delete(String MsgID)throws Exception{
		if(!this.messageDAO.delete(MsgID)){
			throw new GalaxyLabException("Error in delete Message");
		}
	}
	
	/**
	 * 获取附近的约球信息
	 * @param rec
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	public UserMessageData getNearMessage(Rectangle rec, String rows)throws Exception{
		String sqlString = "and Status!='已关闭'";
		if(rec!=null){
			sqlString += "and (longitude between '"+rec.getMinX()+"' and '"+rec.getMaxX()+"') "
						+ "and (latitude between '"+rec.getMinY()+"' and '"+rec.getMaxY()+"')";
		}
		String type = CommonConfig.MSG_TYPE_INVITED;
		Collection<Message> msgList = this.messageDAO.getSearch(rows, sqlString);
		msgList = getDistance(rec.getMinX(), rec.getMinY(), msgList);
		int count = this.messageDAO.getSearchCount(sqlString, type);
		UserMessageData umd = new UserMessageData(msgList, count);
		return umd;
		
	}
	
	/**
	 * 获取距离
	 * @param lon1
	 * @param lat1
	 * @param msgList
	 * @return
	 */
	public Collection<Message> getDistance(double lon1, double lat1, Collection<Message> msgList){
		List<Message> msgs = (List<Message>) msgList;
		if(msgs.size()>0){
			for(Message msg : msgs){
				if(msg.getLongitude()!=null && msg.getLatitude()!=null){
					double lon2 = Double.parseDouble(msg.getLongitude());//用户的经度
					double lat2 = Double.parseDouble(msg.getLatitude());//用户的纬度
					double distance = LocationUtil.GetDistance(lon1, lat1, lon2, lat2);//获取距离
					msg.setDistance(String.valueOf(distance));
				}
				
			}
			long bt = System.nanoTime();//开始排序
			Collections.sort(msgs,new Comparator<Message>() {

				@Override
				public int compare(Message o1, Message o2) {
					if(o1.getDistance() != null && o2.getDistance() != null){
						double distance1 = Double.parseDouble(o1.getDistance());
						double distance2 = Double.parseDouble(o2.getDistance());
						if(distance1 < distance2){
							return -1;
						}else if(distance1 > distance2){
							return 1;
						}else{
							return o1.getSenderName().compareTo(o2.getSenderName());
						}
					}else{
						return 0;
					}
				}
			});
			long et = System.nanoTime();//结束排序
			logger.info("耗时----{}to{}",bt,et);
			return msgs;
			
		}
		return null;
	}
}
