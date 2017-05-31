package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.MessageRowMapper;
import com.galaxy.ggolf.domain.Message;
import com.galaxy.ggolf.domain.User;
import com.galaxy.ggolf.tools.ListUtil;
import com.spatial4j.core.shape.Rectangle;

public class MessageDAO extends GenericDAO<Message> {

	public MessageDAO() {
		super(new MessageRowMapper());
		// TODO Auto-generated constructor stub
	}
	//创建信息
	public boolean create(Message ms){
		String sql = "insert into message(MsgID,"
				+ "SenderID,"
				+ "SenderName,"
				+ "SenderPhoto,"
				+ "Title,"
				+ "Details,"
				+ "PhotoList,"
				+ "Period,"
				+ "Status,"
				+ "Type,"
				+ "Club,"
				+ "Longitude,"
				+ "Latitude,"
				+ "Radius,"
				+ "Site,"
				+ "ReleaseOrNot,"
				+ "Created_TS)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String photoList = "";
		if(ms.getPhotoList()!=null && ms.getPhotoList().size() > 0){
			photoList = new ListUtil().ListToString(ms.getPhotoList());
		}
		return super.sqlUpdate(sql, ms.getMsgID(),ms.getSenderID(),ms.getSenderName(),ms.getSenderPhoto(),ms.getTitle(),
				ms.getDetails(),photoList,ms.getPeriod(),ms.getStatus(),ms.getType(),ms.getClub(),ms.getLongitude(),
				ms.getLatitude(),ms.getRadius(),ms.getSite(),ms.getReleaseOrNot(),Time());
	}
	
	/*public boolean update(Message ms){
		String sql = "update message set SenderID=?,SenderName=?,Title=?,Details=?,Period=?,Status=?,Type=?,"
				+ "Longitude=?,Latitude=?,Site=?,Updated_TS=? where MsgID=? AND DeletedFlag is null";
		return super.sqlUpdate(sql, ms.getSenderID(),ms.getSenderName(),ms.getTitle(),ms.getDetails(),ms.getPeriod(),ms.getStatus(),
							ms.getType(),ms.getLongitude(),ms.getLatitude(),ms.getSite(),Time(),ms.getMsgID());
				
	}*/
	//修改信息
	public boolean updateMsg(Message ms,String sqlString){
		String sql = "update message set "+sqlString+" Updated_TS='"+Time()+"'"
				+ " where MsgID='"+ms.getMsgID()+"' and DeletedFlag is null";
		return super.executeUpdate(sql);
	}
	
	//生成信息编号
	public int getMsgID(){
		String[] s = new String[2];
		s[0] = "insert into messagecount(`Num`)values(NULL)";
		s[1] = "select max(Num) as a  from messagecount";
		return super.getId(s);
	}
	
	//根据关键字搜索
	public Collection<Message> getSearch(String rows,String sqlString){
		String limit = super.limit(null, rows);
		String sql = "select * from message where DeletedFlag is null "+sqlString+" order by Created_TS desc "+limit+"";
		return super.executeQuery(sql);
	}
	
	//关键字搜索的数量
	public int getSearchCount(String sqlString, String type){
		String sql = "select count(*) from message where DeletedFlag is null "+sqlString+""; 
		return super.count(sql);
	}
	
	
	//获取用户收到的信息列表(分页,关键字搜索)
	public Collection<Message> getMessageByUserID(String userID, String sqlString, String rows, String pageNum){
		String sql = "select * from message where DeletedFlag is null and MsgID in(select MsgID from notifyList where UserID='"+userID+"')"
				+ " or SenderID='"+userID+"' "+ sqlString 
				+" order by Created_TS desc limit"
				+ ((Integer.parseInt(pageNum) - 1) * Integer.parseInt(rows)) + " ," + Integer.parseInt(rows)+"";
		return super.executeQuery(sql);
	}
	
	
	//获取用户收到信息的数量
	public int getCountByUserID(String userID, String sqlString){
		String sql = "select * from message where DeletedFlag is null and MsgID in(select MsgID from notifyList where UserID='"+userID+"')"
				+ " or SenderID='"+ userID +"' "+ sqlString +"";
		return super.count(sql);
	}
	
	//获取全部信息
	public Collection<Message> getAll(String sqlString, String rows, String pageNum){
		String sql = "select * from message where DeletedFlag is null "+sqlString+" order by Created_TS desc limit"
				+ ((Integer.parseInt(pageNum) - 1) * Integer.parseInt(rows)) + " ," + Integer.parseInt(rows)+"";
		return super.executeQuery(sql);
	}
	
	//获取全部信息的数量
	public int getCount(String sqlString){
		String sql ="select count(*) from message where DeletedFlag is null "+sqlString+"";
		return super.count(sql);
	}
	
	//查看信息是否存在
	public Message getBy(String MsgID){
		String sql = "select * from message where DeletedFlag is null and MsgID='"+MsgID+"'";
		Collection<Message> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (Message) result.toArray()[0];
		}
		return null;
	}
	
	//删除
	public boolean delete(String MsgID){
		String sql = "update message set DeletedFlag='Y',Updated_TS='"+Time()+"' where MsgID='"+MsgID+"'";
		return super.executeUpdate(sql);
	}
	
	
	//关闭超过期限的约球信息
	public void closeMessage(String dateTime){ 
		String sql = "update message set Status ='已关闭',Updated_TS='"+Time()+"' where Type='约球' and DeletedFlag is null and Created_TS <'"+dateTime+"'";
		if(super.executeUpdate(sql)){
			logger.debug("close message is {}",true);
		}
	}
	
	//获取时间分组
	public Collection<Message> getDTGroup(String sqlString,String rows){
		String sql = "select * from message where DeletedFlag is null"
				+ " "+sqlString+" GROUP BY date_format(`Created_TS`,'%Y-%m-%d')"
				+ " order by Created_TS desc limit 0 , "+Integer.parseInt(rows)+"";
		return super.executeQuery(sql);
	}
	
	
	

}
