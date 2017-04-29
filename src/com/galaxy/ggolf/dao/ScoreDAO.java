package com.galaxy.ggolf.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.galaxy.ggolf.dao.mapper.ScoreRowMapper;
import com.galaxy.ggolf.domain.Score;

public class ScoreDAO extends GenericDAO<Score>{

	
    
	public ScoreDAO() {
		super(new ScoreRowMapper());
		// TODO Auto-generated constructor stub
	}

	//创建成绩单
	public boolean createScore(Score sc){
		
		Date now = new Date();
		String dt = new String(
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
		String sql="INSERT INTO score(UserId,PlayerName,ClubName,SiteOnePAR,SiteTwoPAR,ScoresOne,ScoresTwo,PuttersOne,PuttersTwo,Created_TS,Status) values(?,?,?,?,?,?,?,?,?,?,?)";
		
		return super.sqlUpdate(sql,sc.getUserId(),sc.getPlayerName(),sc.getClubName(),sc.getSiteOnePAR(),sc.getSiteTwoPAR(),sc.getScoresOne(),sc.getScoresTwo(),sc.getPuttersOne(),sc.getPuttersTwo(),dt,sc.getStatus());
		
	}
	
	//获取记分卡
    public Collection<Score> getScore(String UserId){
    	  
    	  String sql="SELECT * FROM score WHERE UserId='"+UserId+"'AND Status IN ('0','1')";
    	  ArrayList<Score> score=super.executeQuery(sql);
    	  Collection<Score> scoreList=new ArrayList<>();
    	  Score s=new Score();
    	  if (score.size() > 0) {
  			  for(Score sc:score){
  				  s=new Score(sc.getUserId(),sc.getScoreId(),sc.getPlayerName(),sc.getClubName(),sc.getSiteOnePAR(),sc.getSiteTwoPAR(),sc.getScoresOne(),sc.getScoresTwo(),sc.getPuttersOne(),sc.getPuttersTwo(),sc.getCreated_TS(),sc.getStatus());
  			     scoreList.add(s);
  			  }
  		} return scoreList;
    	  
    }

    //记分卡发布或删除
    public boolean delectScore(String UserId,String ScoreId){
    	String sql="DELETE FROM score WHERE UserId='"+UserId+"'AND ScoreId='"+ScoreId+"'";
    	return super.executeUpdate(sql);
    }
}
