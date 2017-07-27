package com.galaxy.ggolf.rest;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.dao.ArticleDAO;
import com.galaxy.ggolf.dao.ClubDAO;
import com.galaxy.ggolf.dao.CoachDAO;
import com.galaxy.ggolf.dao.MessageDAO;
import com.galaxy.ggolf.dao.UserDAO;
import com.galaxy.ggolf.domain.Article;
import com.galaxy.ggolf.domain.Club;
import com.galaxy.ggolf.domain.Coach;
import com.galaxy.ggolf.domain.Message;
import com.galaxy.ggolf.domain.User;
import com.galaxy.ggolf.dto.SearchData;

//@Consumes("multipart/form-data")
@Produces("application/json")
@Path("/Search")
public class SearchService extends BaseService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private ArticleDAO articleDAO;
	
	private UserDAO userDAO;
	
	private MessageDAO messageDAO;
	
	private ClubDAO clubDAO;
	
	private CoachDAO coachDAO;
	
	private final static String TYPE_ALL = "全部";
	private final static String TYPE_GOOD = "商品";
	private final static String TYPE_ARTICLE = "头条";
	private final static String TYPE_USER = "用户";
	private final static String TYPE_DYNAMIC = "动态";
	private final static String TYPE_COACH = "教练";
	private final static String TYPE_CLUB =  "球场";

	public SearchService(ArticleDAO articleDAO, UserDAO userDAO, MessageDAO messageDAO, ClubDAO clubDAO,
			CoachDAO coachDAO) {
		this.articleDAO = articleDAO;
		this.userDAO = userDAO;
		this.messageDAO = messageDAO;
		this.clubDAO = clubDAO;
		this.coachDAO = coachDAO;
	}
	
	
	@GET
	@Path("/getSearch")
	public String getSearch(@FormParam("rows") String rows,
			@FormParam("keyword") String keyword,
			@FormParam("type") String type,
			@Context HttpHeaders headers){
		try {
			logger.debug("类型-------{}",type);
			logger.debug("关键字------{}",keyword);
			SearchData data = new SearchData();
			if(rows == null || rows.equals("") || rows.equalsIgnoreCase("null") || rows.equalsIgnoreCase("undefined")){
				rows = null;
			}
			if(!keyword.equalsIgnoreCase("null")&&keyword!=null){
				String userSql = "and Name like '%"+keyword+"%' ";
				String articleSql = "and Title like '%"+keyword+"%' ";
				String messageSql = "and Type='"+TYPE_DYNAMIC+"' and (Details like '%"+keyword+"%' or Site like '%"+keyword+"%') ";
				String clubSql = "and ClubName like '%"+keyword+"%' ";
				String coachSql = "and UserName like'%"+keyword+"%' ";
				if(type != null && !type.equals("") && !type.equals(TYPE_ALL)){
					data = getSearchData(rows,keyword,type);
				}else{
					Collection<User> users = this.userDAO.getUsers(null, rows, userSql);
					Collection<Article> articles = this.articleDAO.getRelease(null,rows, articleSql);
					Collection<Message> messages = this.messageDAO.getSearch(rows, null, messageSql);
					Collection<Club> clubs = this.clubDAO.getSearch(null, rows, clubSql);
					Collection<Coach> coachs =this.coachDAO.getAll(rows, coachSql);
					if(users.size() > 0){
						data.setUsers(users);
					}
					if(articles.size() > 0){
						data.setArticles(articles);
					}
					if(messages.size() >0){
						data.setMessages(messages);
					}
					if(clubs.size() > 0){
						data.setClubs(clubs);
					}
					if(coachs.size() > 0){
						data.setCoachs(coachs);
					}
				}
				return getResponse(data);
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	
	public SearchData getSearchData(String rows,String keyword,String type){
		SearchData data = new SearchData();
		String userSql = "and Name like '%"+keyword+"%' ";
		String articleSql = "and Title like '%"+keyword+"%' ";
		String messageSql = "and Type='"+TYPE_DYNAMIC+"'(Details like '%"+keyword+"%' or Site like '%"+keyword+"%' ";
		String clubSql = "and ClubName like '%"+keyword+"%' ";
		String coachSql = "and UserName like '%"+keyword+"%' ";
		switch (type) {
		case TYPE_ARTICLE:
			Collection<Article> articles = this.articleDAO.getRelease(null,rows, articleSql);
			if(articles.size() > 0){
				data.setArticles(articles);
			}
			break;
		case TYPE_CLUB:
			Collection<Club> clubs = this.clubDAO.getSearch(null, rows, clubSql);
			if(clubs.size() > 0){
				data.setClubs(clubs);
			}
			break;		
		case TYPE_USER:
			Collection<User> users = this.userDAO.getUsers(null, rows, userSql);
			if(users.size() > 0){
				data.setUsers(users);
			}
			break;
		case TYPE_DYNAMIC:
			Collection<Message> messages = this.messageDAO.getSearch(rows, null, messageSql);
			if(messages.size() > 0){
				data.setMessages(messages);
			}
			break;
		case TYPE_GOOD:
			
			break;
		case TYPE_COACH:
			Collection<Coach> coachs =this.coachDAO.getAll(rows, coachSql);
			if(coachs.size() > 0){
				data.setCoachs(coachs);
			}
			break;
		default:
			break;
		}
		return data;
	}
}
