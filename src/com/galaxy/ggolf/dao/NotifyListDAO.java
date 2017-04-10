package com.galaxy.ggolf.dao;

import java.util.ArrayList;
import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.NotifyListRowMapper;
import com.galaxy.ggolf.domain.NotifyList;
import com.galaxy.ggolf.domain.User;

public class NotifyListDAO extends GenericDAO<NotifyList> {

	public NotifyListDAO() {
		super(new NotifyListRowMapper());
		// TODO Auto-generated constructor stub
	}
	
	public boolean create(Collection<User> user,String MsgID){
		Collection<String> sqls = new ArrayList<String>();
		for(User u : user){
			String sql = "insert into notifylist(MsgID,UserID,UserName,HeadPhoto,ReadOrNot)values("+MsgID+","+u.getUserID()+","+u.getName()+","+u.getHead_portrait()+",'0')";
			sqls.add(sql);
		}
		return super.batchInsert(sqls);
	}
	
	public Collection<NotifyList> getByMsgID(String msgID){
		String sql = "select * from notifylist where MsgID='"+msgID+"'";
		return super.executeQuery(sql);
	}
	
	public boolean updateRead(String NotifyID){
		String sql = "update notifylist set ReadOrNot='1' where NotifyID='"+NotifyID+"'";
		return super.executeUpdate(sql);
	}
	
	public NotifyList getByNotifyID(String NotifyID){
		String sql = "select * from notifylist where NotifyID='"+NotifyID+"'";
		Collection<NotifyList> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (NotifyList) result.toArray()[0];
		}
		return null;
	}

}
