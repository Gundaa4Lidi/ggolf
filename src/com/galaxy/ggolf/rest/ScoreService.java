package com.galaxy.ggolf.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import com.galaxy.ggolf.domain.Follow;
import com.galaxy.ggolf.domain.FriendRank;
import com.galaxy.ggolf.domain.HistoryRank;
import com.galaxy.ggolf.domain.Rank;
import com.galaxy.ggolf.domain.Score;
import com.galaxy.ggolf.domain.Time;
import com.galaxy.ggolf.domain.Track;
import com.galaxy.ggolf.domain.User;
import com.galaxy.ggolf.dto.Year;
import com.galaxy.ggolf.dto.YearHistroyRank;
import com.galaxy.ggolf.manager.ScoreManager;

@Produces("application/json")

@Path("/Score")
public class ScoreService extends BaseService {

    private ScoreManager scoreManager;

    public ScoreService(ScoreManager scoreManager) {
	super();
	this.scoreManager = scoreManager;

    }

    @GET
    @Path("getHistoryRank")
    public String getHistoryRank(@FormParam("UserId") String UserId, @Context HttpHeaders headers) {
	List<String> times = new ArrayList<String>();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Collection<Time> time = scoreManager.getMinTs(UserId);
	String st = "";
	String tt = "";
	String ts = "";
	if (time.size() > 0) {
	    for (Time t : time) {
		if (t.getScreated_TS() != null && !t.getScreated_TS().equals("")) {
		    st = t.getScreated_TS();
		} else {
		    st = "2200-01-01";
		}
		if (t.getTcreated_TS() != null && !t.getTcreated_TS().equals("")) {
		    tt = t.getTcreated_TS();
		} else {
		    tt = "2200-01-01";
		}
	    }

	    if (st != null && !st.equals("") && tt != null && !tt.equals("")) {
		if (Integer.parseInt(st.substring(0, 4)) > Integer.parseInt(tt.substring(0, 4))) {
		    ts = tt;
		} else if (Integer.parseInt(st.substring(0, 4)) < Integer.parseInt(tt.substring(0, 4))) {
		    ts = st;
		} else if (Integer.parseInt(st.substring(0, 4)) == Integer.parseInt(tt.substring(0, 4))) {
		    if (Integer.parseInt(st.substring(5, 7)) > Integer.parseInt(tt.substring(5, 7))) {
			ts = tt;
		    } else {
			ts = st;
		    }
		}
	    } else if (st != null && !st.equals("")) {
		ts = st;
	    } else {
		ts = tt;
	    }

	    times.add(ts);

	}

	// 计算好友的最早时间
	Collection<Follow> friendId = scoreManager.getFenId(UserId);
	String fm = "";
	String friend = "";
	if (friendId.size() > 0) {
	    for (Follow f : friendId) {
		friend = f.getFenID();
	    }

	    String[] fid = friend.split(",");
	    for (int i = 0; i < fid.length; i++) {
		String st2 = "";
		String tt2 = "";
		String ts2 = "";
		String ft = "";
		Collection<Time> ftime = scoreManager.getMinTs(fid[i]);
		for (Time t : ftime) {
		    if (t.getScreated_TS() != null && !t.getScreated_TS().equals("")) {
			st2 = t.getScreated_TS();
		    } else {
			st2 = "2200-01-01";
		    }
		    if (t.getTcreated_TS() != null && !t.getTcreated_TS().equals("")) {
			tt2 = t.getTcreated_TS();
		    } else {
			tt2 = "2200-01-01";
		    }
		}
		if (st2 != null && !st2.equals("") && tt2 != null && !tt2.equals("")) {
		    if (Integer.parseInt(st2.substring(0, 4)) > Integer.parseInt(tt2.substring(0, 4))) {
			ts2 = tt2;
		    } else if (Integer.parseInt(st2.substring(0, 4)) < Integer.parseInt(tt2.substring(0, 4))) {
			ts2 = st2;
		    } else if (Integer.parseInt(st2.substring(0, 4)) == Integer.parseInt(tt2.substring(0, 4))) {
			if (Integer.parseInt(st2.substring(5, 7)) > Integer.parseInt(tt2.substring(5, 7))) {
			    ts2 = tt2;
			} else {
			    ts2 = st2;
			}
		    }
		} else if (st2 != null && !st2.equals("")) {
		    ts2 = st2;
		} else {
		    ts2 = tt2;
		}

		times.add(ts2);
		Collections.sort(times);

	    }
	    // 获取最早的时间
	    fm = times.get(0);
	} else {
	    fm = ts;
	}

	String year = fm.substring(0, 4);
	String month = fm.substring(5, 7);

	Date date = new Date();
	String ny = df.format(date).substring(0, 4);
	

	List<FriendRank> frk = new ArrayList();
	FriendRank fr = new FriendRank();

	Collection<Year<HistoryRank>> yhr = new ArrayList<Year<HistoryRank>>();
	Collection<HistoryRank> rk = null;

	for (int a = Integer.parseInt(year); a <= Integer.parseInt(ny); a++) {
	    rk = new ArrayList<HistoryRank>();
	    
	    if (a > Integer.parseInt(year)) {
		month = "1";
	    }
	   
	    for (int b = Integer.parseInt(month); b <= 12; b++) {

		HistoryRank hr = new HistoryRank(null, null, null);

		frk.clear();
		
		String Times = a + "-" + b + "-01";
		String Time = "AND Created_TS BETWEEN '" + a + "-" + b + "-01'  AND '" + a + "-" + b + "-31'";
		Collection<Score> grade = scoreManager.getBest(UserId, Time);

		// 先计算用户的信息
		fr.setUserId(UserId);
		Collection<User> us = scoreManager.getHeadPhoto(UserId);
		for (User u : us) {
		    fr.setName(u.getName());
		    fr.setHead_portrait(u.getHead_portrait());
		}
		    String Ub = "";
		if (grade.size() > 0) {
		    for (Score f : grade) {
			if (f.getUGrade() != null && !f.getUGrade().equals("")) {
			    Ub = f.getUGrade();
			} else {
			    Ub = "200";
			}
		    }
		    String Ub2 = "";
		    Collection<Track> tk = scoreManager.getTrackBest(UserId, Time);
		    if (tk.size() > 0) {
			for (Track t : tk) {
			    if (t.getUGrade() != null && !t.getUGrade().equals("")) {
				Ub2 = t.getUGrade();
			    } else {
				Ub2 = "200";
			    }
			}

			if (Integer.parseInt(Ub) <= Integer.parseInt(Ub2)) {
			    fr.setBest(Ub);
			} else {
			    fr.setBest(Ub2);
			}

		    } else {
			fr.setBest(Ub);
		    }

		    frk.add(fr);
		} else {
		    fr.setBest("200");
		    frk.add(fr);
		}

		// 计算好友的信息
		Collection<Follow> fenid = scoreManager.getFenId(UserId);
		String ff = "";

		for (Follow fol : fenid) {
		    ff = fol.getFenID();
		}
		if (ff != null && !ff.equals("")) {
		    String[] fid = ff.split(",");
		    String str = "";
		    String str2 = "";

		    for (int i = 0; i < fid.length; i++) {
			Collection<Score> fgrade = scoreManager.getBest(fid[i], Time);
			Collection<Track> tk2 = scoreManager.getTrackBest(fid[i], Time);
			FriendRank fr2 = new FriendRank();
			
			str = "";
			str2 = "";
			if (fgrade.size() > 0) {
			    for (Score s : fgrade) {
				if (s.getUGrade() != null && !s.getUGrade().equals("")) {
				    str = s.getUGrade();
				} else {
				    str = "200";
				}

			    }
			} else {
			    str = "200";
			}

			if (tk2.size() > 0) {
			    for (Track t : tk2) {
				if (t.getUGrade() != null && !t.getUGrade().equals("")) {
				    str2 = t.getUGrade();
				} else {
				    str2 = "200";
				}
			    }
			} else {
			    str2 = "200";
			}

			// 判断用户是否有成绩记录
			if (str != null && !str.equals("") && str2 != null && !str2.equals("")) {
			    if (Integer.parseInt(str) <= Integer.parseInt(str2)) {
				fr2.setBest(str);
			    } else {
				fr2.setBest(str2);
			    }
			    } else {
			        fr2.setBest("200");
			}

			fr2.setUserId(fid[i]);

			Collection<User> fs = scoreManager.getHeadPhoto(fid[i]);

			for (User u : fs) {
			    if (u.getName() != null && !u.getName().equals("")) {
				fr2.setName(u.getName());
			    } else {
				fr2.setName("");
			    }
			    if (u.getHead_portrait() != null && !u.getHead_portrait().equals("")) {
				fr2.setHead_portrait(u.getHead_portrait());
			    } else {
				fr2.setHead_portrait("");
			    }
			}
			frk.add(fr2);

		    }
		    // 进行排名
		    for (int f = 0; f < frk.size() - 1; f++) {
			for (int j = 0; j < frk.size() - f - 1; j++) {
			    if (Integer.parseInt(frk.get(j).getBest()) >= Integer.parseInt(frk.get(j + 1).getBest())) {
				FriendRank r = frk.get(j);
				frk.set(j, frk.get(j + 1));
				frk.set(j + 1, r);
			    }

			}
		    }

		}
		// 把本来分数为空的,去掉分数
		for (int i = 0; i < frk.size(); i++) {
		    if (frk.get(i).getBest().equals("200")) {
			frk.get(i).setBest("");
		    }
		}
		// 如果自己跟好友都没有打球的月份就不显示
		if (frk.get(0).getBest() != null && !frk.get(0).getBest().equals("")) {
		    hr.setHead_portrait(frk.get(0).getHead_portrait());
		    hr.setName(frk.get(0).getName());
		    hr.setTime(Times);
		    rk.add(hr);
		}
	    }

	       Year<HistoryRank> yh = new Year<HistoryRank>(a + "", rk);
	            yhr.add(yh);

	}

	       YearHistroyRank yhrk = new YearHistroyRank(yhr);
	            return getResponse(yhrk);
    }

    
    
    
    // 获取排行榜信息
    @GET
    @Path("/getFrRank")
    public String getFrRank(@FormParam("UserId") String UserId, @FormParam("Times") String Times,
	    @Context HttpHeaders headers) {
	List<FriendRank> frk = new ArrayList();
	FriendRank fr = new FriendRank();

	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
	String year = Times.substring(0, 4);
	String now = Times.substring(5, 7);
	String Time = "AND Created_TS BETWEEN '" + year + "-" + now + "-01'  AND '" + year + "-" + now + "-31'";
	Collection<Score> grade = scoreManager.getBest(UserId, Time);

	// 先计算用户的信息
	fr.setUserId(UserId);
	Collection<User> us = scoreManager.getHeadPhoto(UserId);
	for (User u : us) {
	    fr.setName(u.getName());
	    fr.setHead_portrait(u.getHead_portrait());
	}
	String Ub = "";
	if (grade.size() > 0) {
	    for (Score f : grade) {
		if (f.getUGrade() != null && !f.getUGrade().equals("")) {
		    Ub = f.getUGrade();
		} else {
		    Ub = "200";
		}
	    }
	    String Ub2 = "";
	    Collection<Track> tk = scoreManager.getTrackBest(UserId, Time);
	    if (tk.size() > 0) {
		for (Track t : tk) {
		    if (t.getUGrade() != null && !t.getUGrade().equals("")) {
			Ub2 = t.getUGrade();
		    } else {
			Ub2 = "200";
		    }
		}

		if (Integer.parseInt(Ub) <= Integer.parseInt(Ub2)) {
		    fr.setBest(Ub);
		} else {
		    fr.setBest(Ub2);
		}

	    } else {
		fr.setBest(Ub);
	    }

	    frk.add(fr);
	} else {
	    fr.setBest("200");
	    frk.add(fr);
	}

	// 计算好友的信息
	Collection<Follow> fenid = scoreManager.getFenId(UserId);
	String ff = "";

	for (Follow fol : fenid) {
	    ff = fol.getFenID();
	}
	if (ff != null && !ff.equals("")) {
	    String[] fid = ff.split(",");
	    String str = "";
	    String str2 = "";

	    for (int i = 0; i < fid.length; i++) {
		Collection<Score> fgrade = scoreManager.getBest(fid[i], Time);
		Collection<Track> tk2 = scoreManager.getTrackBest(fid[i], Time);
		FriendRank fr2 = new FriendRank();
		fr2.setBest("");
		fr2.setHead_portrait("");
		fr2.setRanking("");
		fr2.setUserId("");
		str = "";
		str2 = "";
		if (fgrade.size() > 0) {
		    for (Score s : fgrade) {
			if (s.getUGrade() != null && !s.getUGrade().equals("")) {
			    str = s.getUGrade();
			} else {
			    str = "200";
			}

		    }
		} else {
		    str = "200";
		}

		if (tk2.size() > 0) {
		    for (Track t : tk2) {
			if (t.getUGrade() != null && !t.getUGrade().equals("")) {
			    str2 = t.getUGrade();
			} else {
			    str2 = "200";
			}
		    }
		} else {
		    str2 = "200";
		}

		// 判断用户是否有成绩记录
		if (str != null && !str.equals("") && str2 != null && !str2.equals("")) {
		    if (Integer.parseInt(str) <= Integer.parseInt(str2)) {
			fr2.setBest(str);
		    } else {
			fr2.setBest(str2);
		    }
		} else {
		    fr2.setBest("200");
		}

		fr2.setUserId(fid[i]);

		Collection<User> fs = scoreManager.getHeadPhoto(fid[i]);

		for (User u : fs) {
		    if (u.getName() != null && !u.getName().equals("")) {
			fr2.setName(u.getName());
		    } else {
			fr2.setName("");
		    }
		    if (u.getHead_portrait() != null && !u.getHead_portrait().equals("")) {
			fr2.setHead_portrait(u.getHead_portrait());
		    } else {
			fr2.setHead_portrait("");
		    }
		}
		frk.add(fr2);

	    }
	    // 进行排名
	    for (int f = 0; f < frk.size() - 1; f++) {
		for (int j = 0; j < frk.size() - f - 1; j++) {
		    if (Integer.parseInt(frk.get(j).getBest()) >= Integer.parseInt(frk.get(j + 1).getBest())) {
			FriendRank r = frk.get(j);
			frk.set(j, frk.get(j + 1));
			frk.set(j + 1, r);
		    }

		}
	    }
	    // 设置名数
	    int ranking = 0;
	    for (int i = 0; i < frk.size(); i++) {
		ranking++;
		frk.get(i).setRanking(ranking + "");
		if (frk.get(i).getBest().equals("200")) {
		    frk.get(i).setBest("-");
		    frk.get(i).setRanking("-");
		}
	    }
	}
	return getResponse(frk);
    }

    
    
    
    
    
    
    @GET
    @Path("/getRank")
    public String getRank(@FormParam("UserId") String UserId, @Context HttpHeaders headers) {
	Rank rk = new Rank();
	// 获取足迹总数
	int usCount = scoreManager.getCount(UserId);
	// 计算超越好友的百分比
	Collection<Follow> fenid = scoreManager.getFenId(UserId);
	int num = 0;
	String ff = "";
	for (Follow fol : fenid) {
	    logger.info("followID:------{}", fol.getFenID());
	    ff = fol.getFenID();
	}
	String[] fid = ff.split(",");
	for (int i = 0; i < fid.length; i++) {
	    int u = scoreManager.getCount(fid[i]);
	    if (usCount > u) {
		num++;
	    }
	}

	float pre = (float) (num) / (float) (fid.length);
	String percent = " " + (pre * 100) + "";
	String p = percent.substring(percent.indexOf(" "), percent.indexOf("."));
	rk.setTrankRank(p.trim() + "%");
	rk.setTrackCount(usCount);
	// 获取去过的球场总数
	int num2 = 0;
	int clubCount = scoreManager.getClubCount(UserId);
	for (int i = 0; i < fid.length; i++) {
	    int u = scoreManager.getClubCount(fid[i]);
	    if (clubCount > u) {
		num2++;
	    }
	}
	float pre2 = (float) (num2) / (float) (fid.length);
	String clubPercent = " " + (pre2 * 100) + "";
	String clubRank = clubPercent.substring(clubPercent.indexOf(" "), clubPercent.indexOf("."));
	rk.setClubCount(clubCount);
	rk.setClubRank(clubRank.trim() + "%");

	// 获取本月最好成绩
	Date date = new Date();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
	String year = df.format(date).substring(2, 4);
	String now = df.format(date).substring(5, 7);
	String Time = "AND Created_TS BETWEEN '20" + year + "-" + now + "-01'  AND '20" + year + "-" + now + "-31'";
	Collection<Score> grade = scoreManager.getBest(UserId, Time);
	Collection<Track> tk = scoreManager.getTrackBest(UserId, Time);
	String best = "";
	String HIO = "";
	String Eagle = "";
	String Bird = "";
	String Handicap = "";
	String best2 = "";
	if (grade.size() > 0) {
	    for (Score gr : grade) {
		if (gr.getUGrade() != null && !gr.equals("")) {
		    best = gr.getUGrade();
		}

		HIO = gr.getHIO();
		Eagle = gr.getEagle();
		Bird = gr.getBird();
		Handicap = gr.getHandicap();
	    }
	}
	// 获取参考差点跟最高杆数
	rk.setHandicap(Handicap);
	if (tk.size() > 0) {
	    for (Track t : tk) {
		if (t.getUGrade() != null && !t.getUGrade().equals("")) {
		    best2 = t.getUGrade();
		}
	    }

	    if (best != null && !best.equals("") && best2 != null && !best2.equals("")) {
		if (Integer.parseInt(best) <= Integer.parseInt(best2)) {
		    rk.setBestScore(best);

		} else {
		    rk.setBestScore(best2);

		    best = best2;
		}
	    } else if (best != null && !best.equals("")) {
		rk.setBestScore(best);
	    } else if (best2 != null && !best2.equals("")) {
		rk.setBestScore(best2);
		best = best2;
	    }

	} else {
	    rk.setBestScore(best);

	}

	// 获取最好的得分球
	if (HIO != null && !HIO.equals("")) {
	    rk.setBest("一杆进洞:" + HIO);
	} else if (Eagle != null && !Eagle.equals("")) {
	    rk.setBest("老鹰球:" + Eagle);
	} else if (Bird != null && !Bird.equals("")) {
	    rk.setBest("小鸟球:" + Bird);
	} else {
	    rk.setBest("一杆进洞:0");
	}

	if (best != null && !best.equals("")) {
	    // 获取好友排名
	    int frank = 1;
	    String best3 = "";
	    String best4 = "";
	    for (int i = 0; i < fid.length; i++) {
		best3 = "";
		best4 = "";
		Collection<Score> fgrade = scoreManager.getBest(fid[i], Time);
		Collection<Track> tk3 = scoreManager.getTrackBest(fid[i], Time);
		if (fgrade.size() > 0) {
		    for (Score gr : fgrade) {
			best3 = gr.getUGrade();
		    }
		}
		if (tk3.size() > 0) {
		    for (Track t : tk3) {
			best4 = t.getUGrade();
		    }
		}
		if (best3 != null && !best3.equals("") && best4 != null && !best4.equals("")) {
		    if (Integer.parseInt(best3) <= Integer.parseInt(best4)) {
			if (Integer.parseInt(best) > Integer.parseInt(best3)) {
			    frank++;
			}
		    } else {
			if (Integer.parseInt(best) > Integer.parseInt(best4)) {
			    frank++;
			}
		    }
		} else if (best3 != null && !best3.equals("")) {
		    if (Integer.parseInt(best) > Integer.parseInt(best3)) {
			frank++;
		    }
		} else if (best4 != null && !best4.equals("")) {
		    if (Integer.parseInt(best) > Integer.parseInt(best4)) {
			frank++;
		    }
		}

	    }
	    rk.setFriendRank(frank + "");
	} else {
	    rk.setFriendRank("-");
	}

	return getResponse(rk);
    }
   
    
    // 获取记分卡详情
    @GET
    @Path("/getScoreDetail")
    public String getScoreDetail(@FormParam("UserId") String UserId, @FormParam("ScoreId") String ScoreId,
	    @Context HttpHeaders headers) {
	try {
	    if (UserId != null && !UserId.equals("") && ScoreId != null && !ScoreId.equals("")) {
		Collection<Score> gsd = scoreManager.getScoreDetail(UserId, ScoreId);

		return getResponse(gsd);
	    }
	} catch (Exception e) {
	    logger.error("Error occured", e);
	}
	return getErrorResponse();
    }

    @GET
    @Path("/getScores")
    public String getScore(@FormParam("UserId") String UserId, @Context HttpHeaders headers) {
	try {
	    if (UserId != null && !UserId.equals("")) {
		Collection<Score> scores = scoreManager.getScore(UserId);

		return getResponse(scores);
	    }
	} catch (Exception e) {
	    logger.error("Error cured", e);
	}
	return getErrorResponse();
    }

    @GET
    @Path("/deleteScore")
    public String delectScore(@FormParam("UserId") String UserId, @FormParam("ScoreId") String ScoreId,
	    @Context HttpHeaders headers) {
	try {
	    if (UserId != null && !UserId.equals("") && ScoreId != null && !ScoreId.equals("")) {
		scoreManager.deleteScore(UserId, ScoreId);
		return getSuccessResponse();
	    }
	} catch (Exception e) {
	    logger.error("Error occured", e);
	}
	return getErrorResponse();
    }

    
    @POST
    @Path("/create")
    public String CreateScore(String data, @Context HttpHeaders headers) {
	try {
	    Score sc = super.fromGson(data, Score.class);
	    String ScoreOne = sc.getScoresOne();
	    String ScoreTwo = sc.getScoresTwo();
	    String status = "1";

	    // 设置记分是否完成状态码，0：未完成，1：完成
	    String[] Scores = ScoreOne.split("&");
	    for (int i = 0; i < Scores.length; i++) {
		String[] str = Scores[i].split(",");
		for (int j = 0; j < str.length; j++) {
		    if (str[j].equals("0")) {
			status = "0";
			break;
		    }
		}
	    }
	    if (ScoreTwo != null && !ScoreTwo.equals("")) {
		String[] Scores2 = ScoreTwo.split("&");
		for (int i = 0; i < Scores2.length; i++) {
		    String[] str = Scores2[i].split(",");
		    for (int j = 0; j < str.length; j++) {
			if (str[j].equals("0")) {
			    status = "0";
			    break;
			}
		    }
		}

	    }
	    sc.setStatus(status);

	    // 计算总杆数跟推杆总数
	    int Grades[] = { 0, 0, 0, 0 };
	    int Putters[] = { 0, 0, 0, 0 };
	    int Grade = 0;
	    int Putter = 0;
	    int ug = 0;
	    String grade = "";
	    for (int i = 0; i < Scores.length; i++) {
		String[] ScoreOnes = Scores[i].split(",");
		for (int j = 0; j < ScoreOnes.length; j++) {
		    Grade += Integer.parseInt(ScoreOnes[j]);
		}
		Grades[i] = Grade;
		if (i == 0) {
		    ug = Grade;
		    sc.setUGrade(Grade + "");
		}
		grade += Grades[i] + ",";

		Grade = 0;

	    }

	    sc.setGrade(grade);

	    if (ScoreTwo != null && !ScoreTwo.equals("")) {

		grade = "";
		String[] ScoreTwos = ScoreTwo.split("&");
		for (int i = 0; i < ScoreTwos.length; i++) {
		    String[] Score2s = ScoreTwos[i].split(",");
		    for (int j = 0; j < Score2s.length; j++) {
			Grade += Integer.parseInt(Score2s[j]);
		    }
		    if (i == 0) {
			sc.setUGrade(Grade + ug + "");

		    }
		    Grades[i] += Grade;
		    grade += Grades[i] + ",";

		    Grade = 0;

		}
		sc.setGrade(grade);
	    }
	    String put = "";
	    if (sc.getPuttersOne() != null && !sc.getPuttersOne().equals("")) {
		String putterOne = sc.getPuttersOne();
		String[] putterOnes = putterOne.split("&");
		for (int i = 0; i < putterOnes.length; i++) {
		    String[] putter = putterOnes[i].split(",");
		    for (int j = 0; j < putter.length; j++) {
			Putter += Integer.parseInt(putter[j]);
		    }
		    Putters[i] = Putter;
		    put += Putters[i] + ",";
		    Putter = 0;
		}
		sc.setPutter(put);
	    }

	    if (sc.getPuttersTwo() != null && !sc.getPuttersTwo().equals("")) {
		put = "";
		String PutterTwo = sc.getPuttersTwo();
		String[] puttwo = PutterTwo.split("&");
		for (int i = 0; i < puttwo.length; i++) {
		    String[] putte = puttwo[i].split(",");
		    for (int j = 0; j < putte.length; j++) {
			Putter += Integer.parseInt(putte[j]);
		    }

		    Putters[i] = Putter;
		    put += Putters[i] + ",";
		    Putter = 0;
		}
		sc.setPutter(put);
	    }
	    // 计算得分球
	    int HIO = 0;
	    int Eagle = 0;
	    int Bird = 0;
	    int PARGrade = 0;
	    int Bogey = 0;
	    int DBogey = 0;
	    float AVGThree = 0;
	    float AVGFour = 0;
	    float AVGFive = 0;

	    String PAROne = sc.getSiteOnePAR();
	    String[] pars = PAROne.split(",");
	    String[] sco1 = ScoreOne.split("&");
	    String[] scores1 = sco1[0].split(",");
	    for (int i = 0; i < scores1.length; i++) {
		if (Integer.parseInt(pars[i]) - Integer.parseInt(scores1[i]) == 0) {
		    PARGrade++;
		} else if (Integer.parseInt(pars[i]) - Integer.parseInt(scores1[i]) == 1) {
		    Bird++;
		} else if (Integer.parseInt(pars[i]) - Integer.parseInt(scores1[i]) == 2) {
		    Eagle++;
		} else if (Integer.parseInt(pars[i]) - Integer.parseInt(scores1[i]) == -1) {
		    Bogey++;
		} else if (Integer.parseInt(pars[i]) - Integer.parseInt(scores1[i]) == -2) {
		    DBogey++;
		} else if (Integer.parseInt(pars[i]) - Integer.parseInt(scores1[i]) == Integer.parseInt(pars[i]) - 1) {
		    HIO++;
		}
	    }
	    sc.setHIO(HIO + "");
	    sc.setDBogey(DBogey + "");
	    sc.setBogey(Bogey + "");
	    sc.setBird(Bird + "");
	    sc.setPARGrade(PARGrade + "");
	    if (sc.getSiteTwoPAR() != null && !sc.getSiteTwoPAR().equals("")) {
		String PARTwo = sc.getSiteTwoPAR();
		String[] pars2 = PARTwo.split(",");
		String[] sco2 = ScoreTwo.split("&");
		String[] scores2 = sco2[0].split(",");
		for (int i = 0; i < scores2.length; i++) {
		    if (Integer.parseInt(pars2[i]) - Integer.parseInt(scores2[i]) == 0) {
			PARGrade++;
		    } else if (Integer.parseInt(pars2[i]) - Integer.parseInt(scores2[i]) == 1) {
			Bird++;
		    } else if (Integer.parseInt(pars2[i]) - Integer.parseInt(scores2[i]) == 2) {
			Eagle++;
		    } else if (Integer.parseInt(pars2[i]) - Integer.parseInt(scores2[i]) == -1) {
			Bogey++;
		    } else if (Integer.parseInt(pars2[i]) - Integer.parseInt(scores2[i]) == -2) {
			DBogey++;
		    } else if (Integer.parseInt(pars2[i]) - Integer.parseInt(scores2[i]) == Integer.parseInt(pars[i])
			    - 1) {
			HIO++;
		    }
		}
		sc.setHIO(HIO + "");
		sc.setDBogey(DBogey + "");
		sc.setBogey(Bogey + "");
		sc.setBird(Bird + "");
		sc.setPARGrade(PARGrade + "");
	    }
	    // 计算平均杆数
	    String three = "";
	    String four = "";
	    String five = "";
	    float th = 0;
	    float th2 = 0;
	    float th3 = 0;
	    for (int i = 0; i < pars.length; i++) {
		if (pars[i].equals("3")) {
		    three += i + ",";
		}
		if (pars[i].equals("4")) {
		    four += i + ",";
		}
		if (pars[i].equals("5")) {
		    five += i + ",";
		}
	    }
	    String[] thr = three.split(",");
	    for (int i = 0; i < thr.length; i++) {
		int a = Integer.parseInt(thr[i].trim());
		th += (float) Integer.parseInt(scores1[a]);
	    }
	    AVGThree = th / (float) (thr.length);
	    sc.setAVGThree((AVGThree + "").substring(0, 3));

	    String[] fou = four.split(",");
	    for (int i = 0; i < fou.length; i++) {
		int a = Integer.parseInt(fou[i].trim());
		th2 += (float) Integer.parseInt(scores1[a]);
	    }
	    AVGFour = th2 / (float) (fou.length);
	    sc.setAVGFour((AVGFour + "").substring(0, 3));

	    String[] fiv = five.split(",");
	    for (int i = 0; i < fiv.length; i++) {
		int a = Integer.parseInt(fiv[i].trim());
		th3 += (float) Integer.parseInt(scores1[a]);
	    }
	    AVGFive = th3 / (float) (fiv.length);
	    sc.setAVGFive((AVGFive + "").substring(0, 3));

	    if (sc.getSiteTwoPAR() != null && !sc.getAVGThree().equals("")) {
		three = "";
		four = "";
		five = "";

		float AVGThree2 = 0;
		float AVGFour2 = 0;
		float AVGFive2 = 0;
		String[] Scores2 = ScoreTwo.split("&");
		String[] scores2 = Scores2[0].split(",");
		String PARTwo = sc.getSiteTwoPAR();
		String[] pars2 = PARTwo.split(",");
		for (int i = 0; i < pars2.length; i++) {
		    if (pars2[i].equals("3")) {
			three += i + ",";
		    }
		    if (pars2[i].equals("4")) {
			four += i + ",";
		    }
		    if (pars2[i].equals("5")) {
			five += i + ",";
		    }
		}
		String[] thre = three.split(",");
		for (int i = 0; i < thre.length; i++) {
		    int a = Integer.parseInt(thre[i].trim());
		}
		AVGThree2 = th / (float) (thre.length + thr.length);

		sc.setAVGThree((AVGThree2 + "").substring(0, 3));

		String[] fo = four.split(",");
		for (int i = 0; i < fo.length; i++) {
		    int a = Integer.parseInt(fo[i].trim());
		    th += (float) Integer.parseInt(scores2[a]);
		}
		AVGFour2 = th / (float) (fo.length + fou.length);
		sc.setAVGFour((AVGFour2 + "").substring(0, 3));

		String[] fv = five.split(",");
		for (int i = 0; i < fv.length; i++) {
		    int a = Integer.parseInt(fv[i].trim());
		    th += (float) Integer.parseInt(scores2[a]);
		}
		AVGFive2 = th / (float) (fv.length + fiv.length);
		sc.setAVGFive((AVGFive2 + "").substring(0, 3));
	    }
	    // 计算参考差点
	    three = "";
	    four = "";
	    five = "";
	    for (int i = 0; i < pars.length; i++) {
		if (pars[i].equals("3")) {
		    three += i + ",";
		}
		if (pars[i].equals("4")) {
		    four += i + ",";
		}
		if (pars[i].equals("5")) {
		    five += i + ",";
		}
	    }
	    String[] tr = three.split(",");
	    String[] fr = four.split(",");
	    String[] fi = five.split(",");
	    int sum1 = 0;
	    for (int i = 0; i < tr.length - 1; i++) {
		if (Integer.parseInt(scores1[Integer.parseInt(tr[i])]) >= 5) {
		    sum1 += 5;
		} else {
		    sum1 += Integer.parseInt(scores1[Integer.parseInt(tr[i])]);
		}
	    }
	    for (int i = 0; i < fr.length - 1; i++) {
		if (Integer.parseInt(scores1[Integer.parseInt(fr[i])]) >= 5) {
		    sum1 += 7;
		} else {
		    sum1 += Integer.parseInt(scores1[Integer.parseInt(fr[i])]);
		}
	    }
	    for (int i = 0; i < fi.length - 1; i++) {
		if (Integer.parseInt(scores1[Integer.parseInt(fi[i])]) >= 5) {
		    sum1 += 9;
		} else {
		    sum1 += Integer.parseInt(scores1[Integer.parseInt(fi[i])]);
		}
	    }

	    if (sc.getSiteTwoPAR() != null && !sc.getSiteTwoPAR().equals("")) {
		String siteTwo = sc.getSiteTwoPAR();
		String[] pars2 = siteTwo.split(",");
		String[] Scores2 = ScoreTwo.split("&");
		String[] scores2 = Scores2[0].split(",");
		three = "";
		four = "";
		five = "";
		for (int i = 0; i < pars2.length; i++) {
		    if (pars2[i].equals("3")) {
			three += i + ",";
		    }
		    if (pars2[i].equals("4")) {
			four += i + ",";
		    }
		    if (pars2[i].equals("5")) {
			five += i + ",";
		    }
		}
		String[] tr2 = three.split(",");
		String[] fr2 = four.split(",");
		String[] fi2 = five.split(",");

		int sum2 = 0;
		for (int i = 0; i < tr2.length - 1; i++) {
		    if (Integer.parseInt(scores2[Integer.parseInt(tr2[i])]) >= 5) {
			sum2 += 5;
		    } else {
			sum2 += Integer.parseInt(scores2[Integer.parseInt(tr2[i])]);
		    }
		}
		for (int i = 0; i < fr2.length - 1; i++) {
		    if (Integer.parseInt(scores2[Integer.parseInt(fr2[i])]) >= 5) {
			sum2 += 7;
		    } else {
			sum2 += Integer.parseInt(scores2[Integer.parseInt(fr2[i])]);
		    }
		}
		for (int i = 0; i < fi.length - 1; i++) {
		    if (Integer.parseInt(scores2[Integer.parseInt(fi2[i])]) >= 5) {
			sum2 += 9;
		    } else {
			sum2 += Integer.parseInt(scores2[Integer.parseInt(fi2[i])]);
		    }
		}
		double sum = ((sum1 + sum2) * (double) 1.5 - 72) * (double) 0.8;
		String s = ".";
		if ((sum + "").length() >= 4) {
		    sc.setHandicap((sum + "").substring(s.indexOf(""), s.indexOf(".") + 4));
		} else {
		    sc.setHandicap((sum + "").substring(s.indexOf(""), s.indexOf(".") + 3));
		}
	    }
	    if (scoreManager.createScore(sc)) {
		return getSuccessResponse();
	    }
	} catch (Exception e) {
	    logger.error("Error cured", e);
	}
	return getErrorResponse();
    }

    
    
    
    
    // 更新记分卡
    @POST
    @Path("/update")
    public String UpdateScore(String data, @Context HttpHeaders headers) {
	try {
	    Score sc = super.fromGson(data, Score.class);
	    String ScoreOne = sc.getScoresOne();
	    String ScoreTwo = sc.getScoresTwo();
	    String status = "1";

	    // 设置记分是否完成状态码，0：未完成，1：完成
	    String[] Scores = ScoreOne.split("&");
	    for (int i = 0; i < Scores.length; i++) {
		String[] str = Scores[i].split(",");
		for (int j = 0; j < str.length; j++) {
		    if (str[j].equals("0")) {
			status = "0";
			break;
		    }
		}
	    }
	    if (ScoreTwo != null && !ScoreTwo.equals("")) {
		String[] Scores2 = ScoreTwo.split("&");
		for (int i = 0; i < Scores2.length; i++) {
		    String[] str = Scores2[i].split(",");
		    for (int j = 0; j < str.length; j++) {
			if (str[j].equals("0")) {
			    status = "0";
			    break;
			}
		    }
		}

	    }
	    sc.setStatus(status);
	    // 计算总杆数跟推杆总数
	    int Grades[] = { 0, 0, 0, 0 };
	    int Putters[] = { 0, 0, 0, 0 };
	    int Grade = 0;
	    int Putter = 0;
	    int ug = 0;
	    String grade = "";
	    for (int i = 0; i < Scores.length; i++) {
		String[] ScoreOnes = Scores[i].split(",");
		for (int j = 0; j < ScoreOnes.length; j++) {
		    Grade += Integer.parseInt(ScoreOnes[j]);
		}
		Grades[i] = Grade;
		if (i == 0) {
		    ug = Grade;
		    sc.setUGrade(Grade + "");
		}
		grade += Grades[i] + ",";

		Grade = 0;

	    }

	    sc.setGrade(grade);

	    if (ScoreTwo != null && !ScoreTwo.equals("")) {

		grade = "";
		String[] ScoreTwos = ScoreTwo.split("&");
		for (int i = 0; i < ScoreTwos.length; i++) {
		    String[] Score2s = ScoreTwos[i].split(",");
		    for (int j = 0; j < Score2s.length; j++) {
			Grade += Integer.parseInt(Score2s[j]);
		    }
		    if (i == 0) {
			sc.setUGrade(Grade + ug + "");

		    }
		    Grades[i] += Grade;
		    grade += Grades[i] + ",";

		    Grade = 0;

		}
		sc.setGrade(grade);
	    }
	    String put = "";
	    if (sc.getPuttersOne() != null && !sc.getPuttersOne().equals("")) {
		String putterOne = sc.getPuttersOne();
		String[] putterOnes = putterOne.split("&");
		for (int i = 0; i < putterOnes.length; i++) {
		    String[] putter = putterOnes[i].split(",");
		    for (int j = 0; j < putter.length; j++) {
			Putter += Integer.parseInt(putter[j]);
		    }
		    Putters[i] = Putter;
		    put += Putters[i] + ",";
		    Putter = 0;
		}
		sc.setPutter(put);
	    }

	    if (sc.getPuttersTwo() != null && !sc.getPuttersTwo().equals("")) {
		put = "";
		String PutterTwo = sc.getPuttersTwo();
		String[] puttwo = PutterTwo.split("&");
		for (int i = 0; i < puttwo.length; i++) {
		    String[] putte = puttwo[i].split(",");
		    for (int j = 0; j < putte.length; j++) {
			Putter += Integer.parseInt(putte[j]);
		    }

		    Putters[i] += Putter;
		    put += Putters[i] + ",";
		    Putter = 0;
		}
		sc.setPutter(put);
	    }
	    // 计算
	    int HIO = 0;
	    int Eagle = 0;
	    int Bird = 0;
	    int PARGrade = 0;
	    int Bogey = 0;
	    int DBogey = 0;
	    float AVGThree = 0;
	    float AVGFour = 0;
	    float AVGFive = 0;
	    String PAROne = sc.getSiteOnePAR();
	    String[] pars = PAROne.split(",");
	    String[] sco1 = ScoreOne.split("&");
	    String[] scores1 = sco1[0].split(",");
	    for (int i = 0; i < scores1.length; i++) {
		if (Integer.parseInt(pars[i]) - Integer.parseInt(scores1[i]) == 0) {
		    PARGrade++;
		} else if (Integer.parseInt(pars[i]) - Integer.parseInt(scores1[i]) == 1) {
		    Bird++;
		} else if (Integer.parseInt(pars[i]) - Integer.parseInt(scores1[i]) == 2) {
		    Eagle++;
		} else if (Integer.parseInt(pars[i]) - Integer.parseInt(scores1[i]) == -1) {
		    Bogey++;
		} else if (Integer.parseInt(pars[i]) - Integer.parseInt(scores1[i]) == -2) {
		    DBogey++;
		} else if (Integer.parseInt(pars[i]) - Integer.parseInt(scores1[i]) == Integer.parseInt(pars[i]) - 1) {
		    HIO++;
		}
	    }
	    sc.setHIO(HIO + "");
	    sc.setDBogey(DBogey + "");
	    sc.setBogey(Bogey + "");
	    sc.setBird(Bird + "");
	    sc.setPARGrade(PARGrade + "");
	    if (sc.getSiteTwoPAR() != null && !sc.getSiteTwoPAR().equals("")) {
		String PARTwo = sc.getSiteTwoPAR();
		String[] pars2 = PARTwo.split(",");
		String[] sco2 = ScoreTwo.split("&");
		String[] scores2 = sco2[0].split(",");
		for (int i = 0; i < scores2.length; i++) {
		    if (Integer.parseInt(pars2[i]) - Integer.parseInt(scores2[i]) == 0) {
			PARGrade++;
		    } else if (Integer.parseInt(pars2[i]) - Integer.parseInt(scores2[i]) == 1) {
			Bird++;
		    } else if (Integer.parseInt(pars2[i]) - Integer.parseInt(scores2[i]) == 2) {
			Eagle++;
		    } else if (Integer.parseInt(pars2[i]) - Integer.parseInt(scores2[i]) == -1) {
			Bogey++;
		    } else if (Integer.parseInt(pars2[i]) - Integer.parseInt(scores2[i]) == -2) {
			DBogey++;
		    } else if (Integer.parseInt(pars2[i]) - Integer.parseInt(scores2[i]) == Integer.parseInt(pars[i])
			    - 1) {
			HIO++;
		    }
		}
		sc.setHIO(HIO + "");
		sc.setDBogey(DBogey + "");
		sc.setBogey(Bogey + "");
		sc.setBird(Bird + "");
		sc.setPARGrade(PARGrade + "");
		sc.setEagle(Eagle + "");
	    }
	    // 计算平均杆数
	    String three = "";
	    String four = "";
	    String five = "";
	    float th = 0;
	    float th2 = 0;
	    float th3 = 0;
	    for (int i = 0; i < pars.length; i++) {
		if (pars[i].equals("3")) {
		    three += i + ",";
		}
		if (pars[i].equals("4")) {
		    four += i + ",";
		}
		if (pars[i].equals("5")) {
		    five += i + ",";
		}
	    }
	    String[] thr = three.split(",");
	    for (int i = 0; i < thr.length; i++) {
		int a = Integer.parseInt(thr[i].trim());
		th += (float) Integer.parseInt(scores1[a]);
	    }
	    AVGThree = th / (float) (thr.length);
	    sc.setAVGThree((AVGThree + "").substring(0, 3));

	    String[] fou = four.split(",");
	    for (int i = 0; i < fou.length; i++) {
		int a = Integer.parseInt(fou[i].trim());
		th2 += (float) Integer.parseInt(scores1[a]);
	    }
	    AVGFour = th2 / (float) (fou.length);
	    sc.setAVGFour((AVGFour + "").substring(0, 3));

	    String[] fiv = five.split(",");
	    for (int i = 0; i < fiv.length; i++) {
		int a = Integer.parseInt(fiv[i].trim());
		th3 += (float) Integer.parseInt(scores1[a]);
	    }
	    AVGFive = th3 / (float) (fiv.length);
	    sc.setAVGFive((AVGFive + "").substring(0, 3));

	    if (sc.getSiteTwoPAR() != null && !sc.getAVGThree().equals("")) {
		three = "";
		four = "";
		five = "";

		float AVGThree2 = 0;
		float AVGFour2 = 0;
		float AVGFive2 = 0;
		String[] Scores2 = ScoreTwo.split("&");
		String[] scores2 = Scores2[0].split(",");
		String PARTwo = sc.getSiteTwoPAR();
		String[] pars2 = PARTwo.split(",");
		for (int i = 0; i < pars2.length; i++) {
		    if (pars2[i].equals("3")) {
			three += i + ",";
		    }
		    if (pars2[i].equals("4")) {
			four += i + ",";
		    }
		    if (pars2[i].equals("5")) {
			five += i + ",";
		    }
		}
		String[] thre = three.split(",");
		for (int i = 0; i < thre.length; i++) {
		    int a = Integer.parseInt(thre[i].trim());
		}
		AVGThree2 = th / (float) (thre.length + thr.length);

		sc.setAVGThree((AVGThree2 + "").substring(0, 3));

		String[] fo = four.split(",");
		for (int i = 0; i < fo.length; i++) {
		    int a = Integer.parseInt(fo[i].trim());
		    th += (float) Integer.parseInt(scores2[a]);
		}
		AVGFour2 = th / (float) (fo.length + fou.length);
		sc.setAVGFour((AVGFour2 + "").substring(0, 3));

		String[] fv = five.split(",");
		for (int i = 0; i < fv.length; i++) {
		    int a = Integer.parseInt(fv[i].trim());
		    th += (float) Integer.parseInt(scores2[a]);
		}
		AVGFive2 = th / (float) (fv.length + fiv.length);
		sc.setAVGFive((AVGFive2 + "").substring(0, 3));
	    }

	    if (scoreManager.updateScore(sc)) {
		return getSuccessResponse();
	    }
	} catch (Exception e) {
	    logger.error("Error ccured", e);
	}
	return getErrorResponse();
    }

}
