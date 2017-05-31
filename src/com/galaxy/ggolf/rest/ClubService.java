package com.galaxy.ggolf.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.dao.ClubScoreDAO;
import com.galaxy.ggolf.dao.ClubServeDAO;
import com.galaxy.ggolf.dao.ClubTotalScoreDAO;
import com.galaxy.ggolf.dao.ClubserveLimitTimeDAO;
import com.galaxy.ggolf.dao.OtherDateDAO;
import com.galaxy.ggolf.dao.PriceForTimeDAO;
import com.galaxy.ggolf.domain.Club;
import com.galaxy.ggolf.domain.ClubDetail;
import com.galaxy.ggolf.domain.ClubFairway;
import com.galaxy.ggolf.domain.ClubScore;
import com.galaxy.ggolf.domain.ClubServe;
import com.galaxy.ggolf.domain.ClubserveLimitTime;
import com.galaxy.ggolf.domain.Comment;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.domain.OtherDate;
import com.galaxy.ggolf.domain.PriceForTime;
import com.galaxy.ggolf.dto.ClubData;
import com.galaxy.ggolf.dto.ClubServeData;
import com.galaxy.ggolf.dto.GenericData;
import com.galaxy.ggolf.jdbc.CommonConfig;
import com.galaxy.ggolf.manager.ClubDetailManager;
import com.galaxy.ggolf.manager.ClubManager;
import com.galaxy.ggolf.manager.CommentManager;

//@Consumes("multipart/form-data")
@Produces("application/json")
@Path("/Club")
public class ClubService extends BaseService {
	
	private ClubManager manager;
	private ClubDetailManager clubDetailManager; 
	private CommentManager commentManager;
	private ClubServeDAO clubServeDAO;
	private PriceForTimeDAO priceForTimeDAO;
	private ClubserveLimitTimeDAO clubserveLimitTimeDAO;
	private ClubScoreDAO clubScoreDAO;
	private ClubTotalScoreDAO clubTotalScoreDAO;
	private OtherDateDAO otherDateDAO;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public ClubService(ClubManager manager, ClubDetailManager clubDetailManager,
			CommentManager commentManager,ClubServeDAO clubServeDAO,
			PriceForTimeDAO priceForTimeDAO,ClubserveLimitTimeDAO clubserveLimitTimeDAO,
			ClubScoreDAO clubScoreDAO,ClubTotalScoreDAO clubTotalScoreDAO,
			OtherDateDAO otherDateDAO) {
		this.manager = manager;
		this.clubDetailManager = clubDetailManager;
		this.commentManager = commentManager;
		this.clubServeDAO = clubServeDAO;
		this.priceForTimeDAO = priceForTimeDAO;
		this.clubserveLimitTimeDAO = clubserveLimitTimeDAO;
		this.clubScoreDAO = clubScoreDAO;
		this.clubTotalScoreDAO = clubTotalScoreDAO;
		this.otherDateDAO = otherDateDAO;
	}
	
	/**
	 * 保存球场,练习场
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/saveClub")
	public String saveClub(String data, @Context HttpHeaders headers){
		try {
			Club club = super.fromGson(data,Club.class);
			this.manager.saveClub(club);
			ClubDetail clubDetail = new ClubDetail(club.getClubID(), club.getClubName(), club.getTotalHole(),
					club.getTotalStemNum(), club.getClubPhoneNumber(), club.getClubPhoto());
			this.clubDetailManager.saveClubDetail(clubDetail);
			return getSuccessResponse();
		} catch (GalaxyLabException ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	
	/**
	 * 获取全部球场,练习场
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/getAll")
	public String getAll(@Context HttpHeaders headers)throws Exception{
		return getResponse(this.manager.getAll());
	}
	
	/**
	 * 获取全部球场,练习场的数量
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/getCount")
	public String getCount(@Context HttpHeaders headers)throws Exception{
		return getResponse(this.manager.getCount());
	}
	
	/**
	 * 根据关键字搜索球场或练习场
	 * @param keyword
	 * @param rows
	 * @param pageNum
	 * @param clubType
	 * @param headers
	 * @return
	 */
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
			Collection<Club> data = this.manager.getSearchClub(sqlString, rows, pageNum);
			int pageCount = this.manager.getSearchCount(sqlString);
			ClubData clubData = new ClubData(pageCount,data);
			return getResponse(clubData);
			
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 根据搜索关键字获取球场的数量
	 * @param keyword
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getCountByKeyword")
	public String getCountByKeyword(@FormParam("keyword") String keyword, @Context HttpHeaders headers){
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
			int count = this.manager.getSearchCount(sqlString);
			return getResponse(count);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return getErrorResponse();
	}
	
	/**
	 * 删除球场,练习场
	 * @param clubID
	 * @param headers
	 * @return
	 */
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
	
	/**
	 * 获取球场明细
	 * @param clubID
	 * @param headers
	 * @return
	 */
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
	
	/**
	 * 获取球场的球道信息
	 * @param clubID
	 * @param headers
	 * @return
	 */
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
	
	/**
	 * 保存球场,练习场的明细
	 * @param data
	 * @param headers
	 * @return
	 */
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
	/**
	 * 保存球道信息
	 * @param data
	 * @param headers
	 * @return
	 */
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
	
	/**
	 * 删除球道信息
	 * @param clubID
	 * @param headers
	 * @return
	 */
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
				String ClubserveID = this.clubServeDAO.getClubserveID() + "";
				cs.setClubserveID(ClubserveID);
				if(!this.clubServeDAO.create(cs)){
					throw new GalaxyLabException("Error in create ClubServe");
				}
				return getResponse(cs);
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
			return getSuccessResponse();
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
				sqlString += "and (Name like '%"
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
			int count = this.clubServeDAO.getCountByClubID(sqlString,ClubID);
			ClubServeData csd = new ClubServeData(count, clubServes); 
			return getResponse(csd);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 修改供应商每日每个时段的价格
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/dailyPriceSave")
	public String dailyPriceSave(String data,
			@Context HttpHeaders headers){
		try {
			PriceForTime pft = super.fromGson(data, PriceForTime.class);
			String sqlString = "and DateTime is null";
			PriceForTime pft1 = this.priceForTimeDAO.getPriceByClubServeID(pft.getWeek(), pft.getTime(), pft.getClubserveID(), sqlString);
			if(pft1!=null){
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
	 * 创建其他时段的价格
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/saveOtherPrice")
	public String createOtherPrice(String data,
			@Context HttpHeaders headers){
		try {
			PriceForTime pft = super.fromGson(data, PriceForTime.class);
			if(pft!=null){
				if(pft.getDateTime()!=null&&!pft.getDateTime().equals("")&&!pft.getDateTime().equalsIgnoreCase("null")){
					String sqlString = "and DateTime='"+pft.getDateTime()+"'";
					PriceForTime time = this.priceForTimeDAO.getPriceByClubServeID(pft.getWeek(), pft.getTime(), pft.getClubserveID(), sqlString);
					if(time!=null){
						if(!this.priceForTimeDAO.update(pft)){
							throw new GalaxyLabException("Error in update pricefortime");
						}
					}else{
						if(!this.priceForTimeDAO.create(pft)){
							throw new GalaxyLabException("Error in create pricefortime");
						}
					}
				}
			}
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 创建供应商每日每个时段的价格
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/createPriceForClubServe")
	public String createPriceForClubServe(String data,
			@Context HttpHeaders headers){
		try {
			Collection<PriceForTime> timeList = super.fromJsonArray(data, PriceForTime.class);
			if(timeList!=null && timeList.size() > 0){
				if(!this.priceForTimeDAO.create2(timeList)){
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
			@FormParam("DateTime") String DateTime,
			@Context HttpHeaders headers){
		try {
			String sqlString = "";
			if(DateTime!=null&&!DateTime.equals("")&&!DateTime.equalsIgnoreCase("null")){
				sqlString = "and DateTime='"+DateTime+"' ";
			}else{
				sqlString = "and DateTime is null";
			}
			if(Time!=null&&!Time.equals("")){
				PriceForTime pft = this.priceForTimeDAO.getPriceByClubServeID(Week, Time, ClubserveID,sqlString);
				if(pft!=null){
					return getResponse(pft);
				}
			}else{
				Collection<PriceForTime> priceForTimes = this.priceForTimeDAO.getPricesByClubserveID(Week, ClubserveID,sqlString);
				return getResponse(priceForTimes);
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 获取特殊时段的时间组
	 * @param ClubserveID
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getGroupDateByClubserve")
	public String getGroupDateByClubserve(
			@FormParam("ClubserveID") String ClubserveID,
			@Context HttpHeaders headers){
		try {
			String sqlString = "";
			if(ClubserveID!=null&&!ClubserveID.equals("")&&!ClubserveID.equalsIgnoreCase("null")){
				sqlString = "and ClubserveID='"+ClubserveID+"' group by DateTime order by DateTime desc";
				Collection<PriceForTime> pft = this.priceForTimeDAO.getByString(sqlString,null,null);
				Collection<String> dateTime = new ArrayList<String>();
				if(pft.size()>0){
					for(PriceForTime p : pft){
						dateTime.add(p.getDateTime());
					}
					return getResponse(dateTime);
				}
			}
			
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 查询可订球场
	 * @param date
	 * @param time
	 * @param rows
	 * @param pageNum
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/reserveList")
	public String reserveList(
			@FormParam("date") String date,
			@FormParam("time") String time,
			@FormParam("rows") String rows,
			@FormParam("pageNum") String pageNum,
			@Context HttpHeaders headers){
		try {
			int row = 10;
			if(rows!=null&&!rows.equals("")&&!rows.equalsIgnoreCase("null")){
				row = Integer.parseInt(rows);
			}
			Map<String,String> cMap = new HashMap<String,String>();
			String sqlString = "and DateTime='"+date+"' "
					+ "and Time='"+time+"' "
					+ "and IsValid='1' "
					+ "group by ClubID";
			//先查询特殊时段有无球场可提供预订
			Collection<PriceForTime> priceForTimes = this.priceForTimeDAO.getByString(sqlString,row+"",pageNum);
			if(priceForTimes.size()>0){
				for(PriceForTime pft : priceForTimes){
					cMap.put(pft.getClubID(), pft.getClubID());
				}
			}
			row -= priceForTimes.size();
			String sqlString1 = "and Time='"+time+"' group by ClubID";
			//在查询所有时段有无球场可提供预订,进行过滤
			if(row > 0){
				Collection<PriceForTime> priceForTimes1 = this.priceForTimeDAO.getByString(sqlString1,row+"",pageNum);
				if(priceForTimes1.size()>0){
					for(PriceForTime pft1 : priceForTimes1){
						if(!cMap.containsKey(pft1.getClubID())){
							cMap.put(pft1.getClubID(),pft1.getClubID());
						}
					}
				}
			}
			Collection<Club> clubs = new ArrayList<Club>();
			int count = cMap.size();
			if(cMap.size()>0){
				for(String key : cMap.keySet()){
					Club club = this.manager.getClub(cMap.get(key));
					if(club!=null){
						clubs.add(club);
					}
				}
			}
			ClubData data = new ClubData(count, clubs);
			return getResponse(data);
			
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	
	/**
	 * 保存限时抢购
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/saveClubserveLimitTime")
	public String saveClubserveLimitTime(String data,@Context HttpHeaders headers){
		try {
			ClubserveLimitTime clt = super.fromGson(data, ClubserveLimitTime.class);
			logger.info("限时------{}",clt);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if(clt.getClubserveLimitTimeID()!=null&&!clt.getClubserveLimitTimeID().equals("")&&!clt.getClubserveLimitTimeID().equalsIgnoreCase("null")){
				if(!this.clubserveLimitTimeDAO.update(clt)){
					throw new GalaxyLabException("Error in update ClubserveLimitTime");
				}
			}else {
				String id = this.clubserveLimitTimeDAO.getClubserveLimitTimeID()+"";
				clt.setClubserveLimitTimeID(id);
				ClubserveLimitTime result = this.clubserveLimitTimeDAO.getLimitTimeByClubserveID(clt.getClubserveID(), clt.getDate());
				if(result!=null){
					String cst = clt.getDate() + " " +clt.getStartTime();//传递数据的开始时间
					String cet = clt.getDate() + " " + clt.getEndTime();//传递数据的结束时间
					Date csT = sdf.parse(cst);
					Date ceT = sdf.parse(cet);
					String rst = result.getDate() + " " +result.getStartTime();//查询结果的开始时间
					String ret = result.getDate() + " " + result.getEndTime();//查询结果的结束时间
					Date rsT = sdf.parse(rst);
					Date reT = sdf.parse(ret);
					//判断开始时间和结束时间在同一天是否在原有时间内
					if((csT.getTime()>=rsT.getTime()&&csT.getTime()<reT.getTime())||
							(ceT.getTime()>=rsT.getTime()&&ceT.getTime()<reT.getTime())){
						return getErrormessage("设置限时时间不能重叠");
					}
					
				}
				if(!this.clubserveLimitTimeDAO.create(clt)){
					throw new GalaxyLabException("Error in create ClubserveLimitTime");
				}
				return getSuccessResponse();
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 根据供应商获取限时抢购
	 * @param ClubserveID
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getbyClubserveID")
	public String getbyClubserveID(@FormParam("ClubserveID") String ClubserveID,
			@Context HttpHeaders headers){
		try {
			Collection<ClubserveLimitTime> result = this.clubserveLimitTimeDAO.getbyClubserveID(ClubserveID);
			return getResponse(result);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 根据编号获取限时抢购信息
	 * @param ClubserveLimitTimeID
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getByClubserveLimitTimeID")
	public String getByClubserveLimitTimeID(@FormParam("ClubserveLimitTimeID") String ClubserveLimitTimeID,
			@Context HttpHeaders headers){
		try {
			ClubserveLimitTime result = this.clubserveLimitTimeDAO.getByClubserveLimitTimeID(ClubserveLimitTimeID);
			return getResponse(result);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 删除限时
	 * @param ClubserveLimitTimeID
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/removeClubserveLimitTime")
	public String removeClubserveLimitTime(@FormParam("ClubserveLimitTimeID") String ClubserveLimitTimeID,
			@Context HttpHeaders headers){
		try {
			if(!this.clubserveLimitTimeDAO.delete(ClubserveLimitTimeID)){
				throw new GalaxyLabException("Error in delete ClubserveLimitTime");
			}
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 评论球场
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/publishScore")
	public String publishScore(String data,
			@Context HttpHeaders headers){
		try {
			ClubScore cs = super.fromGson(data, ClubScore.class);
			if(this.clubScoreDAO.getClubScoreByUserID(cs.getUserID(), cs.getClubID())!=null){
				return getErrormessage("您今天已经点评过该球场，再次感谢您的慷慨。");
			}
			if(!this.clubScoreDAO.create(cs)){
				throw new GalaxyLabException("Error in create ClubScore");
			}
			if(cs.getContent()!=null){
				Comment com = new Comment();
				com.setAction(CommonConfig.ACTION_COMMENT);
				com.setRootType(CommonConfig.TYPE_CLUB_COMMENT);
				com.setRootID(cs.getClubID());
				com.setUserName(cs.getUserName());
				com.setUserHead(cs.getUserHead());
				com.setUserID(cs.getUserID());
				com.setParentType(CommonConfig.ACTION_COMMENT);
				com.setParentID(cs.getClubID());
				com.setMemo(cs.getContent());
				this.commentManager.create(com);
			}
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 获取对应球场的评论
	 * @param ClubID
	 * @param pageNum
	 * @param rows
	 * @return
	 */
	@GET
	@Path("/getCommentByClubID")
	public String getCommentByClubID(@FormParam("ClubID") String ClubID,
			@FormParam("pageNum") String pageNum,
			@FormParam("rows") String rows,
			@Context HttpHeaders headers){
		try {
			int page = 1;
			String sqlString = "";
			if(pageNum!=null&&!pageNum.equalsIgnoreCase("null")&&!pageNum.equals("")){
				page = Integer.parseInt(pageNum);
			}
			if(rows!=null&&!rows.equalsIgnoreCase("null")&&!rows.equals("")){
				int Rows = Integer.parseInt(rows);
				sqlString = "limit "+ ((page - 1) * Rows) + " , " + Rows + " ";
			}
			Collection<ClubScore> result = this.clubScoreDAO.getCommentByClubID(ClubID, sqlString);
			return getResponse(result);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 获取球场的总评分
	 * @param ClubID
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getScoreByClubID")
	public String getScoreByClubID(@FormParam("ClubID") String ClubID,
			@Context HttpHeaders headers){
		try {
			return getResponse(this.clubTotalScoreDAO.getTotalScoreByClubID(ClubID));
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
		
	}
	
	/**
	 * 创建供应商特殊时段日期
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/saveOtherDate")
	public String saveOtherDate(String data,
			@Context HttpHeaders headers){
		try {
			OtherDate od = super.fromGson(data, OtherDate.class);
			if(this.otherDateDAO.check(od.getClubserveID(), od.getDateTime())!=null){
				return getErrormessage("该日期已存在");
			}else{
				if(!this.otherDateDAO.create(od)){
					throw new GalaxyLabException("Error in create otherdate");
				}
			}
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 删除特殊时段日期
	 * @param ClubserveID
	 * @param DateTime
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/removeOtherDate")
	public String removeOtherDate(@FormParam("ClubserveID") String ClubserveID,
			@FormParam("DateTime") String DateTime,
			@Context HttpHeaders headers){
		try {
			if(!this.otherDateDAO.delete(ClubserveID,DateTime)){
				throw new GalaxyLabException("Error in delete otherdate");
			}
			PriceForTime pft = this.priceForTimeDAO.checkByDateTime(ClubserveID, DateTime);
			if(pft!=null){
				if(!this.priceForTimeDAO.deleteDateTime(ClubserveID, DateTime)){
					throw new GalaxyLabException("Error in delete priceForTime");
				}
			}
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	
	/**
	 * 查询供应商特殊时段日期
	 * @param ClubserveID
	 * @param rows
	 * @param pageNum
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getOtherDate")
	public String getOtherDate(@FormParam("ClubserveID") String ClubserveID,
			@FormParam("keyword") String keyword,
			@FormParam("rows") String rows,
			@FormParam("pageNum") String pageNum,
			@Context HttpHeaders headers){
		try {
			String sqlString = "";
			if(keyword!=null&&!keyword.equals("")&&!keyword.equalsIgnoreCase("null")){
				sqlString = "and DateTime like'%"+ keyword + "%'"; 
			}
			Collection<OtherDate> otherDates = this.otherDateDAO.getByClubserveID(ClubserveID, sqlString, rows, pageNum);
			int count = this.otherDateDAO.getCount(ClubserveID, sqlString);
			GenericData<OtherDate> result = new GenericData<OtherDate>(count, otherDates);
			return getResponse(result);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
		
	}
	

}
