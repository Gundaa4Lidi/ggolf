package com.galaxy.ggolf.manager;

import java.util.Collection;

import com.galaxy.ggolf.dao.ScoreDAO;
import com.galaxy.ggolf.domain.Score;

public class ScoreManager {
   private ScoreDAO scoreDao;

public ScoreManager(ScoreDAO scoreDao) {
	super();
	this.scoreDao = scoreDao;
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
   
 //记分卡发布或删除
   public boolean deleteScore(String UserId,String ScoreId){
	   if(UserId!=null && !UserId.equals("") && ScoreId !=null && !ScoreId.equals("")){
         	return  scoreDao.delectScore(UserId, ScoreId);
		   
	   }
	        return false;
   }
   
}
