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

import com.galaxy.ggolf.dao.ClubServeDAO;
import com.galaxy.ggolf.dao.PriceForTimeDAO;
import com.galaxy.ggolf.domain.Club;
import com.galaxy.ggolf.domain.ClubDetail;
import com.galaxy.ggolf.domain.ClubFairway;
import com.galaxy.ggolf.domain.ClubServe;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.domain.PriceForTime;
import com.galaxy.ggolf.dto.ClubData;
import com.galaxy.ggolf.dto.ClubServeData;
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
	private ClubServeDAO clubServeDAO;
	private PriceForTimeDAO priceForTimeDAO;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public ClubService(ClubManager manager, ClubDetailManager clubDetailManager,
			CommentManager commentManager,ClubServeDAO clubServeDAO,
			PriceForTimeDAO priceForTimeDAO) {
		this.manager = manager;
		this.clubDetailManager = clubDetailManager;
		this.commentManager = commentManager;
		this.clubServeDAO = clubServeDAO;
		this.priceForTimeDAO = priceForTimeDAO;
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
			if(result!=null){
				return getResponse(result);
			}
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
	
	/**
	 * 保存球场供应商
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/saveClubServe")
	public String saveClubServe(String data,@Context HttpHeaders headers){
		try {
			ClubServe cs = super.fromGson(data, ClubServe.class);
			if(cs.getClubserveID()!=null){
				if(!this.clubServeDAO.update(cs)){
					throw new GalaxyLabException("Error in update ClubServe");
				}
			} else {
				if(!this.clubServeDAO.create(cs)){
					throw new GalaxyLabException("Error in create ClubServe");
				}
			}
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 删除供应商
	 * @param ClubserveID
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/removeClubServe")
	public String removeClubServe(@FormParam("ClubserveID") String ClubserveID,
			@Context HttpHeaders headers){
		try {
			if(!this.clubServeDAO.delete(ClubserveID)){
				throw new GalaxyLabException("Error in delete ClubServe");
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	/**
	 * 获取对应球场的供应商(分页)
	 * @param keyword
	 * @param pageNum
	 * @param rows
	 * @param ClubID
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getClubServeByClubID")
	public String getClubServeByClubID(@FormParam("keyword") String keyword,
			@FormParam("pageNum") String pageNum,
			@FormParam("rows") String rows,
			@FormParam("ClubID") String ClubID,
			@Context HttpHeaders headers){
		try {
			String sqlString = "";
			if(keyword!=null&&!keyword.equalsIgnoreCase("null")&&!keyword.equals("")){
				sqlString = "and (Name like '%"
						+ keyword
						+ "%' or CancelExplain like '%"
						+ keyword 
						+ "%' or ReserveExplain like '%"
						+ keyword 
						+ "%' or ServiceExplain like '%"
						+ keyword 
						+ "%' or MaxDay like '%"
						+ keyword
						+ "%' or date_format(`Created_TS`,'%Y-%m-%d') like '%"
						+ keyword +"%') ";
			}
			Collection<ClubServe> clubServes = this.clubServeDAO.getClubServeByClubID(sqlString, pageNum, rows, ClubID);
			int count = this.clubServeDAO.getCountByClubID(sqlString, pageNum, rows, ClubID);
			ClubServeData csd = new ClubServeData(count, clubServes); 
			return getResponse(csd);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 保存每日每个时段的价格
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/savePriceForClubServe")
	public String savePriceForClubServe(String data,@Context HttpHeaders headers){
		try {
			PriceForTime pft = super.fromGson(data, PriceForTime.class);
			if(pft.getClubservePriceID()!=null){
				if(!this.priceForTimeDAO.update(pft)){
					throw new GalaxyLabException("Error in update pricefortime");
				}
			}else{
				if(!this.priceForTimeDAO.create(pft)){
					throw new GalaxyLabException("Error in create pricefortime");
				}
			}
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 根据供应商编号获取当前时段的价格 或
	 * 获取对应当天所有时段价格
	 * @param Week
	 * @param Time
	 * @param ClubserveID
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getPricesByClubserveID")
	public String getPricesByClubserveID(@FormParam("Week") String Week,
			@FormParam("Time") String Time,
			@FormParam("ClubserveID") String ClubserveID,
			@Context HttpHeaders headers){
		try {
			if(Time!=null){
				PriceForTime pft = this.priceForTimeDAO.getPriceByClubServeID(Week, Time, ClubserveID);
				return getResponse(pft);
			}else{
				Collection<PriceForTime> priceForTimes = this.priceForTimeDAO.getPricesByClubserveID(Week, ClubserveID);
				return getResponse(priceForTimes);
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}

}
