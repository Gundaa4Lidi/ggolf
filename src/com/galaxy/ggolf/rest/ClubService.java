package com.galaxy.ggolf.rest;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.domain.Club;
import com.galaxy.ggolf.domain.ClubDetail;
import com.galaxy.ggolf.domain.ClubFairway;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.dto.ClubData;
import com.galaxy.ggolf.manager.ClubDetailManager;
import com.galaxy.ggolf.manager.ClubManager;
import com.galaxy.ggolf.manager.CommentManager;

@Consumes("application/json")
@Produces("application/json")
@Path("/Club")
public class ClubService extends BaseService {
	
	private ClubManager manager;
	private ClubDetailManager clubDetailManager; 
	private CommentManager commentManager;
	
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public ClubService(ClubManager manager, ClubDetailManager clubDetailManager,
			CommentManager commentManager) {
		this.manager = manager;
		this.clubDetailManager = clubDetailManager;
		this.commentManager = commentManager;
	}
	
	@POST
	@Path("/saveClub")
	public String saveClub(String data, @Context HttpHeaders headers){
		try {
			Club club = super.fromGson(data,Club.class);
			this.manager.saveClub(club);
			return getSuccessResponse();
		} catch (GalaxyLabException ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	
	@GET
	@Path("/getAll")
	public String getAll(@Context HttpHeaders headers)throws Exception{
		return getResponse(this.manager.getAll());
	}
	
	@GET
	@Path("/getCount")
	public String getCount(@Context HttpHeaders headers)throws Exception{
		return getResponse(this.manager.getCount());
	}
	
	@GET
	@Path("/getClubByKeyword")
	public String getClubByKeyword(@FormParam("keyword") String keyword,
						@FormParam("rows") String rows,
						@FormParam("pageNum") String pageNum, 
						@FormParam("clubType") String clubType,
						@Context HttpHeaders headers){
		try {
			String sqlString = "";
			if(keyword!=null&&!keyword.equalsIgnoreCase("null")){
				sqlString += "and (ClubName like '%"
						+ keyword
						+"%' or ClubPhoneNumber like '%"
						+ keyword
						+"%' or ClubAddress like '%"
						+ keyword
						+"%' or Province like '%"
						+ keyword
						+"%' or City like '%"
						+ keyword
						+ "%' or date_format(`Created_TS`,'%Y-%m-%d') like '%"
						+ keyword 
						+"%' or Area like '%"
						+ keyword +"%')";
			}
			if(clubType != null && !clubType.equalsIgnoreCase("null")){
				sqlString += "and ClubType= '"+clubType+"'";
			}
			if(pageNum==null){
				pageNum = "1";
			}
			Collection<Club> data = this.manager.getSearchClub(sqlString, rows, pageNum);
			int pageCount = this.manager.getSearchCount(sqlString);
			ClubData clubData = new ClubData(data,pageCount);
			return getResponse(clubData);
			
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	@GET
	@Path("/getCountByKeyword")
	public String getCountByKeyword(@FormParam("keyword") String keyword, @Context HttpHeaders headers){
		try {
			int count = this.manager.getSearchCount(keyword);
			return getResponse(count);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return getErrorResponse();
	}
	
	
	@POST
	@Path("/removeClub")
	public String removeClub(@FormParam("ClubID") String clubID, @Context HttpHeaders headers){
		try {
			this.manager.deleteClub(clubID);
			if(this.clubDetailManager.getClubDetailByClubID(clubID)!=null){
				this.clubDetailManager.deleteDetail(clubID);
			}
			if(this.clubDetailManager.getClubFairwayByClubID(clubID)!=null){
				Collection<ClubFairway> clubFairway = this.clubDetailManager.getClubFairwayByClubID(clubID);
				for(ClubFairway cf : clubFairway){
					this.clubDetailManager.deleteFairway(cf.getUID());
				}
			}
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrormessage("删除球场失败");
	}
	
	
	@GET
	@Path("/getClubDetail")
	public String getClubDetailByClubID(@FormParam("ClubID") String clubID, @Context HttpHeaders headers){
		try {
			ClubDetail result = this.clubDetailManager.getClubDetailByClubID(clubID);
			return getResponse(result);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	@GET
	@Path("/getClubFairway")
	public String getClubFairwayByClubID(@FormParam("ClubID") String clubID, @Context HttpHeaders headers){
		try {
			Collection<ClubFairway> result = this.clubDetailManager.getClubFairwayByClubID(clubID);
			return getResponse(result);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	@POST
	@Path("/saveClubDetail")
	public String saveClubDetail(String data, @Context HttpHeaders headers){
		try {
			ClubDetail cd = fromGson(data, ClubDetail.class);
			this.clubDetailManager.saveClubDetail(cd);
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrormessage("保存失败");
	}
	@POST
	@Path("/saveClubFairway")
	public String saveClubFairway(String data, @Context HttpHeaders headers){
		try {
			ClubFairway cf = fromGson(data, ClubFairway.class);
			this.clubDetailManager.saveClubFairway(cf);
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrormessage("保存失败");
	}
	
	@POST
	@Path("/removeClubFairway")
	public String removeClubFairway(@FormParam("ClubID") String clubID, @Context HttpHeaders headers){
		try {
			Collection<ClubFairway> clubFairway = this.clubDetailManager.getClubFairwayByClubID(clubID);
			if(clubFairway != null){
				for(ClubFairway cf : clubFairway){
					this.clubDetailManager.deleteFairway(cf.getUID());
				}
			}
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrormessage("删除失败");
	}

}
