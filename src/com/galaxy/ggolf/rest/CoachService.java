package com.galaxy.ggolf.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.server.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.apache.cxf.common.util.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.dao.CoachCommentDAO;
import com.galaxy.ggolf.dao.CoachCourseDAO;
import com.galaxy.ggolf.dao.CoachDAO;
import com.galaxy.ggolf.dao.CoachScoreDAO;
import com.galaxy.ggolf.dao.CourseOrderDAO;
import com.galaxy.ggolf.dao.CourseTimeDAO;
import com.galaxy.ggolf.dao.CourseVideoDAO;
import com.galaxy.ggolf.dao.UmengDAO;
import com.galaxy.ggolf.dao.UserDAO;
import com.galaxy.ggolf.domain.Coach;
import com.galaxy.ggolf.domain.CoachComment;
import com.galaxy.ggolf.domain.CoachCourse;
import com.galaxy.ggolf.domain.CoachScore;
import com.galaxy.ggolf.domain.CourseOrder;
import com.galaxy.ggolf.domain.CourseTime;
import com.galaxy.ggolf.domain.CourseVideo;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.domain.Message;
import com.galaxy.ggolf.domain.Staff;
import com.galaxy.ggolf.domain.Umeng;
import com.galaxy.ggolf.domain.User;
import com.galaxy.ggolf.dto.CoachCourseData;
import com.galaxy.ggolf.dto.CoachData;
import com.galaxy.ggolf.dto.GenericData;
import com.galaxy.ggolf.dto.LiveEvent;
import com.galaxy.ggolf.dto.PushOption;
import com.galaxy.ggolf.dto.UserData;
import com.galaxy.ggolf.jdbc.CommonConfig;
import com.galaxy.ggolf.manager.MessageManager;
import com.galaxy.ggolf.push.PushManager;
import com.galaxy.ggolf.tools.CipherUtil;

//@Consumes("multipart/form-data")
@Produces("application/json")
@Path("/Coach")
public class CoachService extends BaseService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final CoachDAO coachDAO;
	private final UserDAO userDAO;
	private final CoachScoreDAO coachScoreDAO;
	private final CoachCommentDAO coachCommentDAO;
	private final CoachCourseDAO coachCourseDAO;
	private final CourseTimeDAO courseTimeDAO;
	private final CourseVideoDAO courseVideoDAO;
	private final CourseOrderDAO courseOrderDAO;
	private final UmengDAO umengDAO;
	private final MessageManager messageManager;
	
	public CoachService(CoachDAO coachDAO,
			UserDAO userDAO,
			CoachScoreDAO coachScoreDAO,
			CoachCommentDAO coachCommentDAO,
			CoachCourseDAO coachCourseDAO,
			CourseTimeDAO courseTimeDAO,
			CourseVideoDAO courseVideoDAO,
			CourseOrderDAO courseOrderDAO,
			UmengDAO umengDAO,
			MessageManager messageManager) {
		this.coachDAO = coachDAO;
		this.userDAO = userDAO;
		this.coachScoreDAO = coachScoreDAO;
		this.coachCommentDAO = coachCommentDAO;
		this.coachCourseDAO = coachCourseDAO;
		this.courseTimeDAO = courseTimeDAO;
		this.courseVideoDAO = courseVideoDAO;
		this.courseOrderDAO = courseOrderDAO;
		this.umengDAO = umengDAO;
		this.messageManager = messageManager;
	}
	
	/**
	 * 申请创建教练或修改教练资料
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/saveCoach")
	public String saveCoach(String data, @Context HttpHeaders headers){
		try {
			Coach coach = super.fromGson(data,Coach.class);
			Coach c = this.coachDAO.getCoachByCoachID(coach.getCoachID(),null);
			if(c!=null){
				if(c.getVerify().equals("1")){
					String sqlString = update(coach);
					if(!this.coachDAO.update(coach.getCoachID(), sqlString)){
						return getErrormessage("修改教练信息失败");
					}
				}else if(c.getVerify().equals("0")){
					return getErrormessage("申请中,请等待审核结果");
				}else if(c.getVerify().equals("2")){
					return getErrormessage("申请已被拒绝,请联系客服");
				}
			}else{
				if(!this.coachDAO.create(coach)){
					return getErrormessage("教练申请创建失败");
				}
			}
			return getSuccessResponse();
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	
	public String update(Coach c){
		String sqlString = "";
		if(c.getSeniority()!=null&&!c.getSeniority().equalsIgnoreCase("null")){
			sqlString += "Seniority="+c.getSeniority()+",";
		}
		if(c.getIntro()!=null&&!c.getIntro().equalsIgnoreCase("null")){
			sqlString += "Intro="+c.getIntro()+",";
		}
		if(c.getBirthday()!=null&&!c.getBirthday().equalsIgnoreCase("null")){
			sqlString += "Birthday="+c.getBirthday()+",";
		}
		if(c.getSex()!=null&&!c.getSex().equalsIgnoreCase("null")){
			sqlString += "Sex="+c.getSex()+",";
		}
		if(c.getACHV()!=null&&!c.getACHV().equalsIgnoreCase("null")){
			sqlString += "ACHV="+c.getACHV()+",";
		}
		if(c.getTeachACHV()!=null&&!c.getTeachACHV().equalsIgnoreCase("null")){
			sqlString += "TeachACHV="+c.getTeachACHV()+",";
		}
		if(c.getTeachLocation()!=null&&!c.getTeachLocation().equalsIgnoreCase("null")){
			sqlString += "TeachLocation="+c.getTeachLocation()+",";
		}
		if(c.getTeachCollege()!=null&&!c.getTeachCollege().equalsIgnoreCase("null")){
			sqlString += "TeachCollege="+c.getTeachCollege()+",";
		}
		if(c.getTeachAddress()!=null&&!c.getTeachAddress().equalsIgnoreCase("null")){
			sqlString += "TeachAddress="+c.getTeachAddress()+",";
		}
		return sqlString;
	}
	
	/**
	 * 教练审核
	 * @param Verify
	 * @param CoachID
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/verifyCoach")
	public String verifyCoach(@FormParam("Verify") String Verify,
			@FormParam("CoachID") String CoachID,
			@Context HttpHeaders headers){
		try {
			Coach coach = this.coachDAO.getCoachByCoachID(CoachID,null);
			if(coach.getVerify().equals("0")){
				if(Verify.equals("1")||Verify.equals("2")){
					if(!this.coachDAO.updateVerify(CoachID, Verify)){
						return getErrormessage("审核失败");
					}
					Coach co = this.coachDAO.getCoachByCoachID(CoachID,null);
					if(co!=null){
						if(co.getVerify().equals("1")){
							this.userDAO.IsCoach(CoachID, "1");
							CoachScore cs = this.coachScoreDAO.getByCoachID(CoachID);
							if(cs==null){
								cs = new CoachScore();
								cs.setCoachID(co.getCoachID());
								cs.setScore("0");
								cs.setTeachNo("0");
								if(!this.coachScoreDAO.create(cs)){
									throw new GalaxyLabException("Error in create CoachScore");
								}
							}
						}
						Umeng u = this.umengDAO.getByUserID(co.getCoachID());
						boolean flag = false;
						if(co.getVerify().equals("1")){
							flag = true;
						}else if(co.getVerify().equals("2")){
							flag = false;
						}
						PushManager.sendVerifyCoach(u.getUmeng_Token(), flag);
						return getSuccessResponse();
					}
				}else{
					return getErrormessage("审核失败");
				}
			}else{
				if(coach.getVerify().equals("1")){
					return getErrormessage("已审核成功");
				}else if(coach.getVerify().equals("2")){
					return getErrormessage("已拒绝该用户的教练申请");
				}
				
			}
			
		} catch (Exception ex) {
			logger.error("Error occured", ex);
		}
		return getErrorResponse();
	}
	
	@POST
	@Path("/removeCoach")
	public String removeCoach(
			@FormParam("CoachID") String CoachID,
			@Context HttpHeaders headers){
		try {
			if(!this.coachDAO.delete(CoachID)){
				return getErrormessage("删除教练失败,查无此人或正在申请中");
			}
			return getSuccessResponse();
		} catch (Exception ex) {
			logger.error("Error occured", ex);
		}
		return getErrorResponse();
	}
	
	/**
	 * 获取教练详细信息
	 * @param CoachID
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getCoachDetail")
	public String getCoachDetail(@FormParam("CoachID") String CoachID,
			@Context HttpHeaders headers){
		try {
			Coach c = this.coachDAO.getCoachByCoachID(CoachID,null);
			if(c!=null){
				if(c.getVerify().equals("1")){
					CoachScore cs = this.coachScoreDAO.getByCoachID(CoachID);//教练的评分
					Collection<CoachComment> coachComments= this.coachCommentDAO.getCommentByCoachID(CoachID);//教练的学员评价
					String sqlString = "and CoachID='"+CoachID+"' ";
					Collection<CoachCourse> coachCourses = this.coachCourseDAO.getCourse(sqlString, null, "5");
					if(cs!=null){
						c.setCoachScore(cs);
					}
					if(coachComments.size()>0){
						c.setCoachComments(coachComments);
					}
					if(coachCourses.size() > 0){
						c.setCoachCourses(coachCourses);
					}
				}
				// TODO 
				return getResponse(c);
			}
			
		} catch (Exception ex) {
			logger.error("Error occured", ex);
		}
		return getErrorResponse();
	}
	
	/**
	 * 根据学院编号获取教练
	 * @param ClubID
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getCoachByClubID")
	public String getCoachByClubID(@FormParam("ClubID") String ClubID,
			@Context HttpHeaders headers){
		try {
			Collection<Coach> coachs = this.coachDAO.getCoachByClubID(ClubID);
			int count = this.coachDAO.getCountByClubID(ClubID);
			CoachData data = new CoachData(count,coachs);
			return getResponse(data);
		} catch (Exception ex) {
			logger.error("Error occured", ex);
		}
		return getErrorResponse();
	}
	
	/**
	 * 根据关键字,页码,条数,学院编号获取教练(不传任何参数获取所有教练,如果只根据学院编号查找,不建议使用)
	 * @param keyword  关键字
	 * @param pageNum  页码
	 * @param rows     条数
	 * @param ClubID   学院编号
	 * @param IsVerify 是否获取已验证的教练  (1:是;其他为否)
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getCoach")
	public String getCoach(@FormParam("days") String days,
			@FormParam("keyword") String keyword,
			@FormParam("pageNum") String pageNum,
			@FormParam("rows") String rows, 
			@FormParam("ClubID") String ClubID, 
			@FormParam("IsVerify") String IsVerify,
			@Context HttpHeaders headers) {
		try {
			String sqlString = search(keyword,"0");
			if(days!=null&&!days.equalsIgnoreCase("null")&&!days.equals("")){
				int Days = Integer.parseInt(days);
				if(Days > 0){
					DateTime time = DateTime.now().minusDays(Days);
					sqlString += "and Created_TS > '"+time.toString("yyyy-MM-dd")+"' ";
				}
			}
			if(ClubID != null && !ClubID.equalsIgnoreCase("null") && !ClubID.equalsIgnoreCase("")) {
				sqlString += "and ClubID='" + ClubID + "' ";
			}
			if(IsVerify!=null&&!IsVerify.equalsIgnoreCase("null")&&!IsVerify.equals("")){
				if(IsVerify.equals("1")){
					sqlString += "and Verify='"+IsVerify+"' ";
				}
			}
			Collection<Coach> coachs = this.coachDAO.getAll(rows, sqlString);
			int count = this.coachDAO.getAllCount(sqlString);
			CoachData data = new CoachData(count,coachs);
			return getResponse(data);

		} catch (Exception ex) {
			logger.error("Error occured", ex);
		}
		return getErrorResponse();
	}
	
	//搜索关键字
	public String search(String keyword,String type){
		String sqlString = "";
		if(keyword!=null&&!keyword.equals("")&&!keyword.equalsIgnoreCase("null")){
			if(type.equals("0")){//教练
				sqlString = "and (UserName like '%"
						+ keyword
						+ "%' or CoachName like '%"
						+ keyword
						+ "%' or Birthday like '%"
						+ keyword 
						+ "%' or Sex like '%"
						+ keyword 
						+ "%' or ClubName like '%"
						+ keyword 
						+ "%' or Seniority like '%"
						+ keyword 
						+ "%' or Intro like '%"
						+ keyword
						+ "%' or ACHV like '%"
						+ keyword
						+ "%' or TeachACHV like '%"
						+ keyword
						+ "%' or date_format(`Created_TS`,'%Y-%m-%d') like '%"
						+ keyword +"%') ";
			}
			if(type.equals("1")){//课程
				sqlString = "and (Title like '%"
						+ keyword 
						+ "%' or Price like '%"
						+ keyword 
						+ "%' or MaxPeople like '%"
						+ keyword
						+ "%' or ContainExplain like '%"
						+ keyword
						+ "%' or date_format(`Created_TS`,'%Y-%m-%d') like '%"
						+ keyword +"%') ";
			}
		}
		return sqlString;
	}
	
//	public String limit(String pageNum,String rows){
//		String limit = "";
//		int page = 1;
//		if(pageNum!=null&&!pageNum.equals("")&&!pageNum.equalsIgnoreCase("null")){
//			page = Integer.parseInt(pageNum);
//		}
//		if(rows!=null&&!rows.equals("")&&!rows.equalsIgnoreCase("null")){
//			limit += "limit "+(page-1)*Integer.parseInt(rows)+" , "+Integer.parseInt(rows);
//		}
//		return limit;
//	}
	
	/**
	 * 学员给教练发表评论
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/saveCoachComment")
	public String saveCoachComment(String data,@Context HttpHeaders headers){
		try {
			CoachComment cc = super.fromGson(data, CoachComment.class);
			if(!this.coachCommentDAO.create(cc)){
				throw new GalaxyLabException("Error in create Comment for coach");
			}else{
				double score = this.coachCommentDAO.getAvgScore(cc.getCoachID());
				if(score > 0){
					String sqlString = "Score='"+score+"'";
					if(!this.coachScoreDAO.update(sqlString, cc.getCoachID())){
						throw new GalaxyLabException("Error correcting coaching score");
					}
				}
			}
			
		} catch (Exception ex) {
			logger.error("Error occured", ex);
		}
		return getErrorResponse();
	}
	
	
	/**
	 * 获取教练的球员评价
	 * @param CoachID
	 * @return
	 */
	@GET
	@Path("/getCoachComment")
	public String getCoachComment(@FormParam("CoachID") String CoachID,
			@Context HttpHeaders headers){
		try {
			Collection<CoachComment> coachComments = this.coachCommentDAO.getCommentByCoachID(CoachID);
			return getResponse(coachComments);
		} catch (Exception ex) {
			logger.error("Error occured", ex);
		}
		return getErrorResponse();
	}
	
	/**
	 * 教练申请开设课程
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/ApplyCourse")
	public String ApplyCourse(String data,@Context HttpHeaders headers){
		try {
			CoachCourse cc = super.fromGson(data, CoachCourse.class);
			if(!this.coachCourseDAO.create(cc)){
				throw new GalaxyLabException("Error in create coach course");
			}
			return getSuccessResponse();
		} catch (Exception ex) {
			logger.error("Error occured", ex);
		}
		return getErrorResponse();
	}
	
	/**
	 * 确认课程是否可通过审核
	 * @param CourseID
	 * @param Verify
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/CourseVerify")
	public String CourseVerify(@FormParam("CourseID") String CourseID,
			@FormParam("Verify") String Verify,
			@Context HttpHeaders headers){
		try {
			if(!this.coachCourseDAO.CourseVerify(CourseID, Verify)){
				throw new GalaxyLabException("Error in verify Course");
			}
			//审核结果进行推送
			CoachCourse coachCourse = this.coachCourseDAO.getByCourseID(CourseID);
			if(coachCourse!=null){
				Umeng um = this.umengDAO.getByUserID(CourseID);
				if(um!=null){
					PushOption po = new PushOption();
					String ticker = "教练申请开通课程";
					String title = ticker;
					String text = "课程审核已通过,点击查看详情";
					String text1 = "课程审核已拒绝,若有疑问,请联系客服.";
					String type = CommonConfig.Send_Unicast;
					String after_open = PushManager.GoApp;
					String display_type = PushManager.Display_Type_Notify;
					po.setDevice_tokens(um.getUmeng_Token());
					po.setTicker(ticker);
					po.setTitle(title);
					po.setAfter_open(after_open);
					po.setDisplay_type(display_type);
					po.setType(type);
					if(coachCourse.getVerify().equals("1")){
						if(coachCourse.getIsVideo().equals("1")){
							text = "直播教学课程审核已通过,点击开通直播间";
						}
						po.setText(text);
					}else if(coachCourse.getVerify().equals("2")){
						po.setText(text1);
					}
					PushManager.sendUnicast(po);
				}
				return getSuccessResponse();
			}
		} catch (Exception ex) {
			logger.error("Error occured", ex);
		}
		return getErrorResponse();
	}
	
	/**
	 * 根据教练编号,关键字,页码,条数 获取教练的课程
	 * @param CoachID
	 * @param keyword
	 * @param pageNum
	 * @param rows
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getCourseByCoachID")
	public String getCourseByCoachID(@FormParam("CoachID") String CoachID,
			@FormParam("keyword") String keyword,
			@FormParam("pageNum") String pageNum,
			@FormParam("rows") String rows,
			@Context HttpHeaders headers){
		try {
			String sqlString = "";
			if(this.coachDAO.getCoachByCoachID(CoachID,"1")!=null){//查询验证通过的有没这个教练
				sqlString += "and CoachID='"+CoachID+"' ";
				sqlString += search(keyword, "1");
				Collection<CoachCourse> coachCourses = this.coachCourseDAO.getCourse(sqlString, pageNum, rows);
				int count = this.coachCourseDAO.getCourseCount(sqlString);
				if(coachCourses.size() > 0){
					for(CoachCourse ccs : coachCourses){
							Coach coach = this.coachDAO.getCoachByCoachID(ccs.getCoachID(), null);
							if(coach != null){
								ccs.setCoachName(coach.getCoachName());
								ccs.setCoachHead(coach.getCoachHead());
								ccs.setCoachPhone(coach.getCoachPhone());
							}
//						}
					}
				}
				CoachCourseData courseData = new CoachCourseData(count, coachCourses);
				return getResponse(courseData);
			}
		} catch (Exception ex) {
			logger.error("Error occured", ex);
		}
		return getErrorResponse();
	}
	
	/**
	 * 根据关键字,页码,条数获取课程
	 * @param keyword
	 * @param pageNum
	 * @param rows
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getCourse")
	public String getCourse(
			@FormParam("days") String days,
			@FormParam("keyword") String keyword,
			@FormParam("pageNum") String pageNum,
			@FormParam("rows") String rows,
			@Context HttpHeaders headers){
		try {
			int Days = Integer.parseInt(days);
			String sqlString = search(keyword, "1");
			if(Days > 0){
				DateTime time = DateTime.now().minusDays(Days);
				sqlString += "and Created_TS > '"+time.toString("yyyy-MM-dd")+"' ";
			}
			Collection<CoachCourse> coachCourses = this.coachCourseDAO.getCourse(sqlString, pageNum, rows);
			int count = this.coachCourseDAO.getCourseCount(sqlString);
			if(coachCourses.size() > 0){
				for(CoachCourse ccs : coachCourses){
					Coach coach = this.coachDAO.getCoachByCoachID(ccs.getCoachID(), null);
					if(coach != null){
						ccs.setCoachName(coach.getCoachName());
						ccs.setCoachHead(coach.getCoachHead());
						ccs.setCoachPhone(coach.getCoachPhone());
					}
				}
			}
			CoachCourseData courseData = new CoachCourseData(count, coachCourses);
			return getResponse(courseData);
		} catch (Exception ex) {
			logger.error("Error occured", ex);
		}
		return getErrorResponse();
	}
	
	//修改课程内容
	public String updateCourse(CoachCourse cc){
		String sqlString = "";
		if(cc.getTitle()!=null&&!cc.getTitle().equalsIgnoreCase("null")){
			sqlString += "Title="+cc.getTitle()+",";
		}
		if(cc.getPrice()!=null&&!cc.getPrice().equalsIgnoreCase("null")){
			sqlString += "="+cc.getPrice()+",";
		}
		if(cc.getValid()!=null&&!cc.getValid().equalsIgnoreCase("null")){
			sqlString += "Valid="+cc.getValid()+",";
		}
		if(cc.getIsOpen()!=null&&!cc.getIsOpen().equalsIgnoreCase("null")){
			sqlString += "IsOpen="+cc.getIsOpen()+",";
		}
		if(cc.getMaxPeople()!=null&&!cc.getMaxPeople().equalsIgnoreCase("null")){
			sqlString += "MaxPeople="+cc.getMaxPeople()+",";
		}
		if(cc.getContainExplain()!=null&&!cc.getContainExplain().equalsIgnoreCase("null")){
			sqlString += "ContainExplain="+cc.getContainExplain()+",";
		}
		if(cc.getIsVideo()!=null&&!cc.getIsVideo().equalsIgnoreCase("null")){
			sqlString += "IsVideo="+cc.getIsVideo()+",";
		}
		return sqlString;
	}
	
	/**
	 * 修改课程描述内容
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/updateCourse")
	public String updateCourse(String data,@Context HttpHeaders headers){
		try {
			CoachCourse course = super.fromGson(data, CoachCourse.class);
			String sqlString = updateCourse(course);
			if(!this.coachCourseDAO.update(sqlString, course.getCourseID())){
				throw new GalaxyLabException("Error in update course");
			}
		} catch (Exception ex) {
			logger.error("Error occured", ex);
		}
		return getErrorResponse();
	}
	
	/**
	 * 设置课程的开放时间
	 * @param json
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/IsOpenTime")
	public String IsOpenTime(String json,@Context HttpHeaders headers){
		try {
			Collection<CourseTime> courseTimes = super.fromJsonArray(json, CourseTime.class);
			if(!this.courseTimeDAO.IsOpenTime(courseTimes)){
				throw new GalaxyLabException("Error in create CourseTime");
			}
		} catch (Exception ex) {
			logger.error("Error occured", ex);
		}
		return getErrorResponse();
	}
	
	/**
	 * 获取课程的开放时间
	 * @param CourseID
	 * @param dateTime 
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getCourseOpenTime")
	public String getCourseOpenTime(@FormParam("CourseID") String CourseID,
			@FormParam("dateTime") String dateTime,
			@Context HttpHeaders headers){
		try {
			Collection<CourseTime> courseTimes = this.courseTimeDAO.getTimebyCourseID(CourseID);
			if(dateTime!=null&&!dateTime.equals("")&&!dateTime.equalsIgnoreCase("null")){
				courseTimes = this.courseTimeDAO.getTimeByDate(CourseID, dateTime);
			}
			return getResponse(courseTimes);
		} catch (Exception ex) {
			logger.error("Error occured", ex);
		}
		return getErrorResponse();
	}
	
	/**
	 * 创建直播间
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/createCourseVideo")
	public String createCourseVideo(String data,@Context HttpHeaders headers){
		try {
			CourseVideo courseVideo = super.fromGson(data, CourseVideo.class);
			CoachCourse coachCourse = this.coachCourseDAO.getByCourseID(courseVideo.getCourseID());
			if(coachCourse!=null){
				if(!coachCourse.getIsVideo().equals("1")){
					return getErrormessage("该课程不是视频直播教学.");
				}else if(!coachCourse.getVerify().equals("1")){
					return getErrormessage("该课程还未通过审核,请等候通知.");
				}
			}else{
				return getErrormessage("没有该课程存在,请重新申请课程.");
			}
			CourseVideo cv = this.courseVideoDAO.getByCourseID(courseVideo.getCourseID());
			if(cv!=null){
				return getErrormessage("该课程房间已存在!");
			}
			if(!this.courseVideoDAO.create(courseVideo)){
				throw new GalaxyLabException("Error in create createCourseVideo");
			}else{
				cv = this.courseVideoDAO.getByCourseID(courseVideo.getCourseID());
				return getResponse(cv);
			}
			
		} catch (Exception ex) {
			logger.error("Error occured", ex);
		}
		return getErrorResponse();
	}
	
	/**
	 * 修改房间名称
	 * @param RoomName
	 * @param UID
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/updateRoomName")
	public String updateRoomName(@FormParam("RoomName") String RoomName,
			@FormParam("UID") String UID,
			@Context HttpHeaders headers){
		try {
			if(!this.courseVideoDAO.updateRoomName(RoomName, UID)){
				throw new GalaxyLabException("Error in update roomname");
			}
			return getSuccessResponse();
		} catch (Exception ex) {
			logger.error("Error occured", ex);
		}
		return getErrorResponse();
		
	}
	/**
	 * 修改直播间的房间密码
	 * @param password 旧密码
	 * @param password1 新密码
	 * @param CourseID 课程编号
	 * @param StartDateTime 新密码的上课时间
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/updateRoomPwd")
	public String updateRoomPwd(@FormParam("password") String password,
			@FormParam("password1") String password1,
			@FormParam("CourseID") String CourseID,
			@FormParam("StartDateTime") String StartDateTime,
			@Context HttpHeaders headers){
		try {
			CourseVideo cv = this.courseVideoDAO.getByCourseID(CourseID);
			if(cv!=null){
				if(password.equals(cv.getPassword())){
					if(!this.courseVideoDAO.updatePassword(password1, CourseID)){
						return getErrormessage("房间密码修改失败");
					}else{
						String tokens = "";
						String sqlString = "and CourseID='"+CourseID+"' and StartDateTime='"+StartDateTime+"' ";
						CoachCourse cc = this.coachCourseDAO.getByCourseID(CourseID);
						Collection<CourseOrder> corder = this.courseOrderDAO.getCourseOrder(sqlString, null, null);
						Collection<User> userList = new ArrayList<User>();
						if(corder.size()>0){
							for(CourseOrder co : corder){
								Umeng um = this.umengDAO.getByUserID(co.getUserID());
								if(um!=null){
									tokens += um.getUmeng_Token()+",";
								}
								User user = this.userDAO.getUserByUserID(co.getUserID());
								userList.add(user);
							}
							String ticker = "上课通知";
							String title = ticker;
							String text = "你订阅的"+cc.getTitle()+"直播教学将在"+StartDateTime+"开始,房间密码为:"+password1+"." ;
							String type = CommonConfig.Send_Listcast;
							String after_open = PushManager.GoApp;
							String display_type = PushManager.Display_Type_Notify;
							//保存消息
							Message msg = new Message();
							msg.setSenderID(CommonConfig.SuperAdminID);
							msg.setSenderName(CommonConfig.SuperAdminName);
							msg.setSenderPhoto(CommonConfig.SuperAdminHead);
							msg.setReleaseOrNot("Y");
							msg.setTitle(title);
							msg.setDetails(text);
							msg.setType(CommonConfig.MSG_TYPE_SYS);
							msg.setUserList(userList);
							String msgResult = this.messageManager.saveMsg(msg);
							
							tokens = tokens.substring(0,tokens.length() - 1);
							if(msgResult.equals("")){
								//开始推送
								PushOption po = new PushOption();
								po.setDevice_tokens(tokens);
								po.setTicker(ticker);
								po.setTitle(title);
								po.setText(text);
								po.setAfter_open(after_open);
								po.setDisplay_type(display_type);
								po.setType(type);
								PushManager.sendListcast(po);
								return getResponse("密码修改成功,并通知该时段的学员");
							}
						}
						
					}
				}else{
					return getErrormessage("房间原密码不一致");
				}
			}
		} catch (Exception ex) {
			logger.error("Error occured", ex);
		}
		return getErrorResponse();
		
	}
	
//	@POST
//	@Path("/test")
//	public String test(String data,
//			@FormParam("test") String test,
//			@Context HttpHeaders headers){
//		try {
//			
//		} catch (Exception e) {
//			logger.error("Error occured",e);
//		}
//		return getErrorResponse();
//	}
	
	/**
	 * 获取教练的学生(我的学生)
	 * @param CoachID
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getStudentsByCoach")
	public String getStudentsByCoach(@FormParam("CoachID") String CoachID,
			@Context HttpHeaders headers){
		try {
			Coach c = this.coachDAO.getCoachByCoachID(CoachID, null);
			Collection<User> students = new ArrayList<User>();
			if(c!=null){
				String sqlString = "and CoachID='"+CoachID+"' and State='3' Group by UserID ";
				Collection<CourseOrder> orders = this.courseOrderDAO.getCourseOrder(sqlString, null, null);
				int count = this.courseOrderDAO.getOrderCount(sqlString);
				if(orders.size() > 0 ){
					for(CourseOrder order : orders){
						User user = this.userDAO.getUserByUserID(order.getUserID());
						if(user!=null){
							students.add(user);
						}
					}
				}
				UserData userData = new UserData(count, students);
				return getResponse(userData);
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 获取用户的教练(我的教练)
	 * @param UserID
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getCoachByUserID")
	public String getCoachByUserID(@FormParam("UserID") String UserID,
			@Context HttpHeaders headers){
		try {
			User u = this.userDAO.getUserByUserID(UserID);
			Collection<Coach> coachs = new ArrayList<Coach>();
			if(u!=null){
				String sqlString = "and UserID='"+UserID+"' and State='3' Group by CoachID";
				Collection<CourseOrder> orders = this.courseOrderDAO.getCourseOrder(sqlString, null, null);
				int count = this.courseOrderDAO.getOrderCount(sqlString);
				if(orders.size()>0){
					for(CourseOrder co : orders){
						Coach coach = this.coachDAO.getCoachByCoachID(co.getCoachID(), "1");
						if(coach!=null){
							coachs.add(coach);
						}
					}
				}
				CoachData data = new CoachData(count,coachs); 
				return getResponse(data);
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 获取该课程下的所有学生
	 * @param CourseID
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getStudentByCourseID")
	public String getStudentByCourseID(@FormParam("CourseID") String CourseID,
			@Context HttpHeaders headers){
		try {
			Collection<User> students = new ArrayList<User>();
			String sqlString = "and CourseID='"+CourseID+"' and State='3' Group by UserID";
			Collection<CourseOrder> orders = this.courseOrderDAO.getCourseOrder(sqlString, null, null);
			int count = this.courseOrderDAO.getOrderCount(sqlString);
			if(orders.size() > 0){
				for(CourseOrder co : orders){
					User user = this.userDAO.getUserByUserID(co.getUserID());
					if(user!=null){
						students.add(user);
					}
				}
			}
			UserData data = new UserData(count, students);
			return getResponse(data);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 根据时段获取学生
	 * @param CourseID 课程编号
	 * @param StartDateTime 开课时间(yyyy-MM-dd格式获取当天的学生,yyyy-MM-dd HH:mm格式获取当前时间点的学生)
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getStudentByDateTime")
	public String getStudentByDateTime(@FormParam("CourseID") String CourseID,
			@FormParam("StartDateTime") String StartDateTime,
			@Context HttpHeaders headers){
		try {
			String sqlString = "";
			Collection<User> students = new ArrayList<User>();
			CoachCourse course = this.coachCourseDAO.getByCourseID(CourseID);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setLenient(false);
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			sdf1.setLenient(false);
			if(course.getIsBatch().equals("0")){
				if(sdf.parse(StartDateTime)!=null){
					sqlString = "and CourseID='"+CourseID+"' and State='3' and date_format(`StartDateTime`,'%Y-%m-%d')='"+StartDateTime+"' Group by UserID";
				}
				if(sdf1.parse(StartDateTime)!=null){
					sqlString = "and CourseID='"+CourseID+"' and State='3' and StartDateTime='"+StartDateTime+"' Group by UserID";
				}
				Collection<CourseOrder> orders = this.courseOrderDAO.getCourseOrder(sqlString, null, null);
				int count = this.courseOrderDAO.getOrderCount(sqlString);
				if(orders!=null&&orders.size() > 0){
					for(CourseOrder co : orders){
						User user = this.userDAO.getUserByUserID(co.getUserID());
						if(user!=null){
							students.add(user);
						}
					}
				}
				UserData data = new UserData(count, students);
				return getResponse(data);
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 获取所有直播间
	 * @param days
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getCourseVideo")
	public String getCourseVideo(
			@FormParam("days") int days,
			@Context HttpHeaders headers){
		try {
			String sqlString = "";
			if(days > 0){
				DateTime time = DateTime.now().minusDays(days);
				sqlString += "and Created_TS > '"+time.toString("yyyy-MM-dd")+"' ";
			}
			Collection<CourseVideo> data = this.courseVideoDAO.getBySearch(sqlString, null, null);
			int count = this.courseVideoDAO.getBySearchCount(sqlString);
			if(data.size()>0){
				for(CourseVideo cv : data){
					cv = setCourseVideo(cv);//添加用户的相关属性
				}
			}
			GenericData<CourseVideo> result = new GenericData<CourseVideo>(count, data);
			return getResponse(result);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 获取教练的直播间
	 * @param CoachID
	 * @param pageNum
	 * @param rows
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getCVbyCoachID")
	public String getCVbyCoachID(@FormParam("CoachID") String CoachID,
			@FormParam("pageNum") String pageNum,
			@FormParam("rows") String rows,
			@Context HttpHeaders headers){
		try {
			if(!StringUtils.isEmpty(CoachID)&&!CoachID.equalsIgnoreCase("null")){
				String sqlString = "and CreatorID = '"+CoachID+"'";
				Collection<CourseVideo> data = this.courseVideoDAO.getBySearch(sqlString, rows, pageNum);
				if(data.size()>0){
					for(CourseVideo cv : data){
						cv = setCourseVideo(cv);
					}
				}
				int count = this.courseVideoDAO.getBySearchCount(sqlString);
				GenericData<CourseVideo> result = new GenericData<CourseVideo>(count, data);
				return getResponse(result);
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 删除直播间
	 * @param UID
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/removeRoom")
	public String removeRoom(
			@FormParam("RoomID") String RoomID,
			@Context HttpHeaders headers){
		try {
			String result = "";
			if(this.courseVideoDAO.delete(RoomID)){
				result = "删除成功";
			}else{
				result = "删除失败";
			}
			return getResponse(result);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	
	
	/**
	 * 用户进入直播间
	 * @param UserName
	 * @param UserID
	 * @param UID
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/intoRoom")
	public String test(@FormParam("UserName") String UserName,
			@FormParam("UserID") String UserID,
			@FormParam("UID") String UID,
			@Context HttpHeaders headers){
		try {
			String result = "";
			User user = this.userDAO.getUserByUserID(UserID);
			if(user!=null){
				if(this.courseVideoDAO.intoRoom(UserName, UserID, UID)){
					result = "用户 "+UserName+"进入房间";
				}
			}else{
				result = "该用户不存在.";
			}
			return getResponse(result);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 添加用户的相关属性
	 * @param cv
	 * @return
	 */
	public CourseVideo setCourseVideo(CourseVideo cv){
		Coach coach = this.coachDAO.getCoachByCoachID(cv.getCreatorID(), "1");
		CoachCourse cc = this.coachCourseDAO.getByCourseID(cv.getCourseID());
		if(coach!=null&&cc!=null){
			cv.setCreatorName(coach.getCoachName());
			cv.setCreatorPhoto(coach.getCoachHead());
			cv.setCourseName(cc.getTitle());
		}
		Collection<LiveEvent> event = cv.getLiveEvent();
		Collection<User> users = new ArrayList<User>();
		Map<String,String> userMap = new HashMap<String,String>();
		if(event.size()>0){
			for(LiveEvent le : event){
				if(!le.getUserID().equals("00")){
					if(!userMap.containsKey(le.getUserID())){
						User user = this.userDAO.getUserByUserID(le.getUserID());
						if(user!=null){
							users.add(user);
							userMap.put(le.getUserID(), le.getUserID());
						}
					}
				}
			}
		}
		if(users.size()>0){
			cv.setUsers(users);
		}
		return cv;
	}
	
	@GET
	@Path("/getCV")
	public String getCV(@FormParam("UID") String UID,
			@Context HttpHeaders headers){
		try {
			return getResponse(this.courseVideoDAO.getByUID(UID));
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
}
