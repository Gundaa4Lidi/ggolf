package com.galaxy.ggolf.rest;

import java.util.Collection;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.dao.CoachCommentDAO;
import com.galaxy.ggolf.dao.CoachDAO;
import com.galaxy.ggolf.dao.CoachScoreDAO;
import com.galaxy.ggolf.dao.UserDAO;
import com.galaxy.ggolf.domain.Coach;
import com.galaxy.ggolf.domain.CoachComment;
import com.galaxy.ggolf.domain.CoachScore;
import com.galaxy.ggolf.domain.GalaxyLabException;

//@Consumes("multipart/form-data")
@Produces("application/json")
@Path("/Coach")
public class CoachService extends BaseService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private CoachDAO coachDAO;
	private UserDAO userDAO;
	private CoachScoreDAO coachScoreDAO;
	private CoachCommentDAO coachCommentDAO;
	
	public CoachService(CoachDAO coachDAO,
			UserDAO userDAO,
			CoachScoreDAO coachScoreDAO,
			CoachCommentDAO coachCommentDAO) {
		this.coachDAO = coachDAO;
		this.userDAO = userDAO;
		this.coachScoreDAO = coachScoreDAO;
		this.coachCommentDAO = coachCommentDAO;
	}
	
	/**
	 * 保存教练资料
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/saveCoach")
	public String saveCoach(String data, @Context HttpHeaders headers){
		try {
			Coach coach = super.fromGson(data,Coach.class);
			Coach c = this.coachDAO.getCoachByCoachID(coach.getCoachID());
			if(c!=null){
				if(c.getVerify().equals("审核通过")){
					String sqlString = update(coach);
					if(!this.coachDAO.update(coach.getCoachID(), sqlString)){
						return getErrormessage("修改教练信息失败");
					}
				}else{
					return getErrormessage("请完成教练申请之后操作");
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
			if(!Verify.equals("审批拒绝")&&!Verify.equals("审核通过")){
				if(!this.coachDAO.updateVerify(CoachID, Verify)){
					throw new GalaxyLabException("Error in update verify for coach");
				}
				Coach co = this.coachDAO.getCoachByCoachID(CoachID);
				if(co!=null){
					if(co.getVerify().equals("审核通过")){
						CoachScore cs = new CoachScore();
						cs.setCoachID(co.getCoachID());
						cs.setScore("0");
						cs.setTeachNo("0");
						if(!this.coachScoreDAO.create(cs)){
							throw new GalaxyLabException("Error in create CoachScore");
						}
					}
					
				}
			}else{
				return getErrormessage("审核失败");
			}
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
			Coach c = this.coachDAO.getCoachByCoachID(CoachID);
			if(c!=null){
				CoachScore cs = this.coachScoreDAO.getByCoachID(CoachID);//教练的评分
				Collection<CoachComment> coachComments= this.coachCommentDAO.getCommentByCoachID(CoachID);//教练的学员评价
				if(cs!=null){
					c.setCoachScore(cs);
				}
				if(coachComments.size()>0){
					c.setCoachComments(coachComments);
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
			return getResponse(coachs);
		} catch (Exception ex) {
			logger.error("Error occured", ex);
		}
		return getErrorResponse();
	}
	
	/**
	 * 根据关键字,页码,条数,学院编号获取教练(不传任何参数获取所有教练,如果只根据学院编号查找,不建议使用)
	 * @param keyword 
	 * @param pageNum
	 * @param rows
	 * @param ClubID
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getCoach")
	public String getCoach(@FormParam("keyword") String keyword, @FormParam("pageNum") String pageNum,
			@FormParam("rows") String rows, @FormParam("ClubID") String ClubID, @Context HttpHeaders headers) {
		try {
			String sqlString = search(keyword);
			String limit = limit(pageNum, rows);
			if (ClubID != null && !ClubID.equalsIgnoreCase("null") && !ClubID.equalsIgnoreCase("")) {
				sqlString += "and ClubID='" + ClubID + "' ";
			}
			Collection<Coach> coachs = this.coachDAO.getAll(limit, sqlString);
			return getResponse(coachs);

		} catch (Exception ex) {
			logger.error("Error occured", ex);
		}
		return getErrorResponse();
	}

	public String search(String keyword){
		String sqlString = "";
		if(keyword!=null&&!keyword.equals("")&&!keyword.equalsIgnoreCase("null")){
			sqlString = "and (CoachName like '%"
					+ keyword
					+ "%' or Age like '%"
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
		return sqlString;
	}
	
	public String limit(String pageNum,String rows){
		String limit = "";
		int page = 1;
		if(pageNum!=null&&!pageNum.equals("")&&!pageNum.equalsIgnoreCase("null")){
			page = Integer.parseInt(pageNum);
		}
		if(rows!=null&&!rows.equals("")&&!rows.equalsIgnoreCase("null")){
			limit += "limit "+(page-1)*Integer.parseInt(rows)+" , "+Integer.parseInt(rows);
		}
		return limit;
	}
	
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
	
	
}
