package com.galaxy.ggolf.manager;

import java.util.Collection;

import com.galaxy.ggolf.dao.FollowDAO;
import com.galaxy.ggolf.dao.ScoreDAO;
import com.galaxy.ggolf.dao.TimeDAO;
import com.galaxy.ggolf.dao.TrackDAO;
import com.galaxy.ggolf.dao.UserDAO;
import com.galaxy.ggolf.domain.Follow;
import com.galaxy.ggolf.domain.Score;
import com.galaxy.ggolf.domain.Time;
import com.galaxy.ggolf.domain.Track;
import com.galaxy.ggolf.domain.User;

public class ScoreManager {
   private ScoreDAO scoreDao;
   private FollowDAO followDao;
   private UserDAO userDao; 
   private TrackDAO trackDao;
   private TimeDAO timeDao;
public ScoreManager(ScoreDAO scoreDao ,FollowDAO followDao,UserDAO userDao,TrackDAO trackDao,TimeDAO timeDao) {
	
	this.scoreDao = scoreDao;
	this.followDao=followDao;
	this.userDao=userDao;
        this.trackDao=trackDao;
        this.timeDao=timeDao;
}

   //获取记分卡详情
   public Collection<Score> getScoreDetail(String UserId,String ScoreId){
      return scoreDao.getScoreDetail(UserId, ScoreId);
	      
   }


   //创建成绩单
   public boolean createScore(Score sc){
       if(sc.getUserId()!=null && !sc.getUserId().equals("")){
    	 return  scoreDao.createScore(sc);
       }
	   
	     return false;
   }
   
    //获取记分卡
    public Collection<Score> getScore(String UserId){
	  
	   
	   return   scoreDao.getScore(UserId);
   }
   
   //记分卡发布成足迹或删除
   public boolean deleteScore(String UserId,String ScoreId){
	 
         	return  scoreDao.delectScore(UserId, ScoreId);
   }
   
   //更新记分卡
   public boolean updateScore(Score sc){
	   return scoreDao.updateScore(sc);
   }
   //获取用户足迹总数
   public int getCount(String UserId){
	   return scoreDao.getCount(UserId);
   }
   
   //获取好友id
   public Collection<Follow> getFenId(String UserId){
	    return followDao.getFrdId(UserId);      
   }
   //获取去过的球场数量
   public int getClubCount(String UserId){
	   return scoreDao.getClubCount(UserId);
   }
   //获取最高杆数
   public Collection<Score> getBest(String UserId,String Time){
	   return scoreDao.getBest(UserId,Time);
			   
   }
   //获取用户头像
   public Collection<User> getHeadPhoto(String UserId){
	   return userDao.getHead(UserId);
   }
   //获取来自足记的最好成绩
   public Collection<Track> getTrackBest(String UserId,String Time){
	   return trackDao.getBest(UserId,Time);
   }
   //根据时间段获取最好的计分板成绩
   public Collection<Score> getTime(String UserId ,String Time){
	   return scoreDao.getTime(UserId,Time);
   }
   //获取最早的计分板纪录
   public Collection<Score> getMinTime(String UserId){
	   return scoreDao.getMinTime(UserId);
   }
   //获取最早的足迹纪录
  public Collection<Track>getTrackMinTime(String UserId){
	  return trackDao.getTrackMinTime(UserId);
  }
  //根据时间段获取最好的足迹成绩
 public Collection<Track> getTrackTime(String UserId,String Time){
	 return trackDao.getTrackTime(UserId, Time);
 }
 public Collection<Time> getMinTs(String UserId){
     return timeDao.getmin(UserId);
 }
 
}
