package com.galaxy.ggolf.rest;

import java.util.ArrayList;
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

import com.galaxy.ggolf.dao.ArticleDAO;
import com.galaxy.ggolf.dao.ArticleSubjectDAO;
import com.galaxy.ggolf.dao.ClubDAO;
import com.galaxy.ggolf.dao.CollectDAO;
import com.galaxy.ggolf.dao.UserDAO;
import com.galaxy.ggolf.domain.Article;
import com.galaxy.ggolf.domain.ArticleSubject;
import com.galaxy.ggolf.domain.Club;
import com.galaxy.ggolf.domain.Collect;
import com.galaxy.ggolf.domain.User;
import com.galaxy.ggolf.dto.CollectData;
import com.galaxy.ggolf.jdbc.CommonConfig;

//@Consumes("multipart/form-data")
@Produces("application/json")
@Path("/Collect")
public class CollectService extends BaseService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final ClubDAO clubDAO;
	private final ArticleDAO articleDAO;
	private final ArticleSubjectDAO articleSubjectDAO;
	private final UserDAO userDAO;
	private final CollectDAO collectDAO;
	
	public CollectService(ClubDAO clubDAO, ArticleDAO articleDAO,
			ArticleSubjectDAO articleSubjectDAO, UserDAO userDAO,
			CollectDAO collectDAO) {
		this.clubDAO = clubDAO;
		this.articleDAO = articleDAO;
		this.articleSubjectDAO =articleSubjectDAO;
		this.userDAO = userDAO;
		this.collectDAO = collectDAO;
	}
	
	
	/**
	 * 添加/取消收藏
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/setCollect")
	public String collect(String data, @Context HttpHeaders headers){
		try {
			String result = "";
			Collect col = fromGson(data, Collect.class);
			Collect col1 = this.collectDAO.getCollectByUserID(col.getUserID(), col.getThemeID(), col.getType());
			if(col1 != null){
				if(col1.getStatus().equals("1")){
					this.collectDAO.update(col1.getUID(), "0");
					result = "取消收藏";
				}else if(col1.getStatus().equals("0")){
					this.collectDAO.update(col1.getUID(), "1");
					result = "收藏成功";
				}
			}else{
				this.collectDAO.create(col);
				result = "收藏成功";
			}
			return getResponse(result);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
		
	}
	
	/**
	 * 我的收藏(用户的收藏,Type不填获取该用户下的所有收藏)
	 * @param UserID
	 * @param Type
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getUserCollect")
	public String getUserCollect(@FormParam("UserID") String UserID,
			@FormParam("Type") String Type,
			@Context HttpHeaders headers){
		try {
			String sqlString = "";
			if(Type!=null&&!Type.equals("")&&!Type.equalsIgnoreCase("null")){
				sqlString = "and Type='"+Type+"' ";
			}
			Collection<Collect> cols = this.collectDAO.getUserCollect(UserID, sqlString);
			Collection<Article> articles = new ArrayList<Article>();
			Collection<ArticleSubject> subjects = new ArrayList<ArticleSubject>();
			Collection<Club> clubs = new ArrayList<Club>();
			CollectData result = new CollectData();
			int count = this.collectDAO.getUserCollectCount(UserID, sqlString);
			if(cols!=null&&cols.size()>0){
				for(Collect col : cols){
					switch (col.getType()) {
						case CommonConfig.THEME_TYPE_A:
							Article art = this.articleDAO.getByArticleID(col.getThemeID());
							if(art!=null){
								articles.add(art);
							}
							break;
						case CommonConfig.THEME_TYPE_C:
							Club club = this.clubDAO.getClubByClubID(col.getThemeID());
							if(club!=null){
								clubs.add(club);
							}
							break;
						case CommonConfig.THEME_TYPE_S:
							ArticleSubject as = this.articleSubjectDAO.getSubject(col.getThemeID());
							if(as!=null){
								String sqlStr = "and SubjectID='"+as.getSubjectID()+"' ";
								Collection<Article> articleList = this.articleDAO.getBykeyword(null, null, sqlStr);
								if(articleList!=null&&articleList.size()>0){
									as.setArticles(articleList);
								}
								subjects.add(as);
							}
							break;
						default:
							break;
					}
				}
				if(articles.size()>0){
					result.setArticles(articles);
				}
				if(subjects.size()>0){
					result.setSubjects(subjects);
				}
				if(clubs.size()>0){
					result.setClubs(clubs);
				}
			}
			result.setCount(count);
			return getResponse(result);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 获取该主题类型下的收藏人员
	 * @param ThemeID
	 * @param Type
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getCollectUser")
	public String getCollectUser(@FormParam("ThemeID") String ThemeID,
			@FormParam("Type") String Type,
			@Context HttpHeaders headers){
		try {
			Collection<Collect> cols = this.collectDAO.getUserList(ThemeID, Type);
			Collection<User> users = new ArrayList<User>();
			CollectData result = new CollectData();
			int count = this.collectDAO.getcount(ThemeID, Type);
			if(cols!=null&&cols.size()>0){
				for(Collect col : cols){
					User user = this.userDAO.getUserByUserID(col.getUserID()); 
					if(user!=null){
						users.add(user);
					}
				}
				if(users.size()>0){
					result.setUserlist(users);
				}
			}
			result.setCount(count);
			return getResponse(result);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	
}
