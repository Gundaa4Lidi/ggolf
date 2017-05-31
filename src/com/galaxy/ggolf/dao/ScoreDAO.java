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

	//获取记分卡详情
	public Collection<Score> getScoreDetail(String UserId ,String ScoreId){
	    String sql="SELECT * FROM Score WHERE UserId='"+UserId+"' AND ScoreId='"+ScoreId+"'";
	    return super.executeQuery(sql);
	     
	}
	
	
	//创建成绩单
	public boolean createScore(Score sc){
		
		Date now = new Date();
		String dt = new String(
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
		String sql="INSERT INTO score(UserId,PlayerName,ClubName,SiteOnePAR,SiteTwoPAR,ScoresOne,ScoresTwo,PuttersOne,PuttersTwo,UGrade,Grade,Putter,HIO,Eagle,Bird,PARGrade,Bogey,DBogey,AVGThree,AVGFour,AVGFive,Handicap,Created_TS,Status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		return super.sqlUpdate(sql,sc.getUserId(),sc.getPlayerName(),sc.getClubName(),sc.getSiteOnePAR(),sc.getSiteTwoPAR(),sc.getScoresOne(),sc.getScoresTwo(),sc.getPuttersOne(),sc.getPuttersTwo(),sc.getUGrade(),sc.getGrade(),sc.getPutter(),sc.getHIO(),sc.getEagle(),sc.getBird(),sc.getPARGrade(),sc.getBogey(),sc.getDBogey(),sc.getAVGThree(),sc.getAVGFour(),sc.getAVGFive(),sc.getHandicap(),dt,sc.getStatus());
		
	}
	
	//获取记分卡
        public Collection<Score> getScore(String UserId) {

	String sql = "SELECT * FROM score WHERE UserId='" + UserId + "'AND Status IN ('0','1')";
	ArrayList<Score> score = super.executeQuery(sql);
	Collection<Score> scoreList = new ArrayList<>();
	Score s = new Score();
	if (score.size() > 0) {
	    for (Score sc : score) {
		s = new Score(sc.getUserId(), sc.getScoreId(), sc.getPlayerName(), sc.getClubName(), sc.getSiteOnePAR(),
			sc.getSiteTwoPAR(), sc.getScoresOne(), sc.getScoresTwo(), sc.getPuttersOne(),
			sc.getPuttersTwo(), sc.getUGrade(), sc.getGrade(), sc.getPutter(), sc.getHIO(), sc.getEagle(),
			sc.getBird(), sc.getPARGrade(), sc.getBogey(), sc.getDBogey(), sc.getAVGThree(),
			sc.getAVGFour(), sc.getAVGFive(), sc.getHandicap(), sc.getCreated_TS(), sc.getStatus());
		scoreList.add(s);
	    }
	}
	return scoreList;

    }

    //记分卡发布或删除
    public boolean delectScore(String UserId,String ScoreId){
    	String sql="DELETE FROM score WHERE UserId='"+UserId+"'AND ScoreId='"+ScoreId+"'";
    	return super.executeUpdate(sql);
    }
    
    //更新记分卡
    public boolean updateScore(Score sc) {
	String sql = "UPDATE  score SET " + "ScoresOne='" + sc.getScoresOne() + "',ScoresTwo='" + sc.getScoresTwo()
		+ "',PuttersOne='" + sc.getPuttersOne() + "',";

	if (sc.getPuttersTwo() != null && !sc.getPuttersTwo().equals("")) {
	    sql += "PuttersTwo='" + sc.getPuttersTwo() + "',";
	}
	sql += "Grade='" + sc.getGrade() + "',HIO='" + sc.getHIO() + "',Putter='" + sc.getPutter() + "',Status='"
		+ sc.getStatus() + "',Eagle='" + sc.getEagle() + "',Bird='" + sc.getBird() + "',PARGrade='"
		+ sc.getPARGrade() + "',Bogey='" + sc.getBogey() + "',DBogey='" + sc.getDBogey() + "',AVGThree='"
		+ sc.getAVGThree() + "',AVGFour='" + sc.getAVGFour() + "',AVGFive='" + sc.getAVGFive() + "',Handicap='"
		+ sc.getHandicap() + "' WHERE UserId='" + sc.getUserId() + "' AND ScoreId='" + sc.getScoreId() + "'";
	return super.executeUpdate(sql);
    }
    //查足迹总数
    public int getCount(String UserId){
    	String sql="SELECT count(*) FROM `track` WHERE UserId='"+UserId+"'";
         return super.count(sql);
    }
    //查询去过的球场总数
    public int getClubCount(String UserId){
    	String sql="SELECT count(distinct score.ClubName,track.ClubName) FROM score  join track  on score.UserId=track.UserId WHERE score.UserId='"+UserId+"'";
		return super.count(sql);
    }
    //查高杆数
    public Collection<Score> getBest(String UserId ,String Time){
    	String sql="SELECT * FROM score WHERE UGrade=(SELECT MIN(UGrade) FROM score WHERE UserId='"+UserId+"' AND ScoresTwo is not null AND ScoresOne is not null and UGrade>60 "+Time+" ) and  UserId='"+UserId+"' "+Time+"";//order by UGrade desc
    	return super.executeQuery(sql);
    }
   
    //按时间段查找计分板记录
    public Collection<Score> getTime(String UserId,String Time){
    	String sql="SELECT * FROM score WHERE UGrade=(SELECT MIN(UGrade) FROM score WHERE UserId='"+UserId+"' AND ScoresTwo is not null AND ScoresOne is not null AND UGrade>60 AND Created_TS BETWEEN '"+Time+"') and  UserId='"+UserId+"'";
    	//String sql = "select * from score where UserId='"+UserId+"'and ScoresTwo is not null and Min(UGrade)>60 Group by date_format(`Created_TS`,'%Y-%m')";
    	return super.executeQuery(sql);
    }
    public Collection<Score> getMinTime(String UserId){
	Date now = new Date();
	String dt = new String(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
    	String sql="select * from score where Created_TS =(SELECT min(Created_TS) FROM score where UserId='"+UserId+"' AND ScoresTwo is not null AND ScoresOne is not null AND UGrade>60) AND UserId='"+UserId+"'";
    	return super.executeQuery(sql);
    }
   
}
