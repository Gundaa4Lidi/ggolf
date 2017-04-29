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

import com.galaxy.ggolf.domain.Article;
import com.galaxy.ggolf.domain.ArticleCategory;
import com.galaxy.ggolf.domain.ArticleContent;
import com.galaxy.ggolf.domain.ArticleSubject;
import com.galaxy.ggolf.domain.ArticleType;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.manager.ArticleManager;
import com.galaxy.ggolf.manager.CommentManager;


@Produces("application/json")
@Path("/Article")
public class ArticleService extends BaseService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass()); 
	
	private ArticleManager articleManager;
	
	private CommentManager commentManager;

	public ArticleService(ArticleManager articleManager,CommentManager commentManager) {
		this.articleManager = articleManager;
		this.commentManager = commentManager;
	}
	
	/**
	 * 保存文集分类
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/saveArticleCategory")
	public String saveArticleCategory(String data, @Context HttpHeaders headers){
		try {
			ArticleCategory ac = fromGson(data, ArticleCategory.class);
			this.articleManager.saveArticleCategory(ac);
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
	 * 保存文集类别
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/saveArticleType")
	public String saveArticleType(String data, @Context HttpHeaders headers){
		try {
			ArticleType at = fromGson(data, ArticleType.class);
			this.articleManager.saveArticleType(at);
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
	 * 保存文集类别下的专题
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/saveArticleSubject")
	public String saveArticleSubject(String data, @Context HttpHeaders headers){
		try {
			ArticleSubject as = fromGson(data, ArticleSubject.class);
			this.articleManager.saveArticleSubject(as);
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
	 * 保存文章
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/saveArticle")
	public String saveArticle(String data, @Context HttpHeaders headers){
		try {
			Article art = fromGson(data, Article.class);
			this.articleManager.saveArticle(art);
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
	 * 保存文章内容
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/saveArticleContent")
	public String saveArticleContent(String data, @Context HttpHeaders headers){
		try {
			ArticleContent ac = fromGson(data, ArticleContent.class);
			this.articleManager.saveArticleContent(ac);
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
	 * 根据是否发布获取文章
	 * @param ReleaseOrAll
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getArticle")
	public String getArticle(@FormParam("ReleaseOrAll") String ReleaseOrAll, @Context HttpHeaders headers){
		try {
			Collection<ArticleCategory> articleCategories = this.articleManager.getAllCategory();
			if(articleCategories.size() > 0){
				for(ArticleCategory ac : articleCategories){
					Collection<ArticleType> articleTypes = this.articleManager.getTypeByCategoryID(ac.getCategoryID());
					if(articleTypes.size() > 0){
						for(ArticleType at : articleTypes){
							if(ReleaseOrAll.equals("release")){
								Collection<Article> releaseaRticles = this.articleManager.getRelease(at.getCategoryID(), at.getTypeID());
								if(releaseaRticles.size() > 0){
									at.setArticle(releaseaRticles);
								}
							}else if(ReleaseOrAll.equals("all")){
								Collection<Article> allArticles = this.articleManager.getByCategoryIDAndTypeID(at.getCategoryID(), at.getTypeID());
								if(allArticles.size() > 0){
									at.setArticle(allArticles);
								}
							}
							if(ac.getSubOrNot()!=null&&ac.getSubOrNot().equals("1")){
								Collection<ArticleSubject> articleSubjects = this.articleManager.getSubjectByTypeID(at.getTypeID());
								if(articleSubjects.size() > 0){
									for(ArticleSubject as : articleSubjects){
										if(ReleaseOrAll.equals("release")){
											Collection<Article> releaseaRticles = this.articleManager.getReleaseBySubjectID(as.getSubjectID());
											if(releaseaRticles.size() > 0){
												as.setArticles(releaseaRticles);
											}
										}else if(ReleaseOrAll.equals("all")){
											Collection<Article> allArticles = this.articleManager.getBySubjectID(as.getSubjectID());
											if(allArticles.size() > 0){
												as.setArticles(allArticles);
											}
										}
									}
									at.setArticleSubject(articleSubjects);
								}
							}
						}
						ac.setArticleType(articleTypes);
					}
				}
				return getResponse(articleCategories);
			}
		} catch (GalaxyLabException ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
		return getErrorResponse();
	}
	
	/**
	 * 根据文集类型获取其下的专题
	 * @param TypeID
	 * @return
	 */
	@GET
	@Path("/getSubjectByTypeID")
	public String getSubjectByTypeID(@FormParam("TypeID") String TypeID){
		try {
			return getResponse(this.articleManager.getSubjectByTypeID(TypeID));
		} catch (GalaxyLabException ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	
	/**
	 * 根据专题编号获取其下是否发布的文章
	 * @param ReleaseOrAll
	 * @param SubjectID
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getArticleBySubjectID")
	public String getArticleBySubjectID(@FormParam("ReleaseOrAll") String ReleaseOrAll,
			@FormParam("SubjectID") String SubjectID,
			@Context HttpHeaders headers){
		try {
			if(ReleaseOrAll.equals("release")){
				Collection<Article> releaseaRticles = this.articleManager.getReleaseBySubjectID(SubjectID);
				if(releaseaRticles.size() > 0){
					return getResponse(releaseaRticles);
				}
			}else if(ReleaseOrAll.equals("all")){
				Collection<Article> allArticles = this.articleManager.getBySubjectID(SubjectID);
				if(allArticles.size() > 0){
					return getResponse(allArticles);
				}
			}
		} catch (GalaxyLabException ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
		return getErrorResponse();
	}
	
	/**
	 * 根据文集类型编号获取其下是否发布的文章
	 * @param ReleaseOrAll
	 * @param CategoryID
	 * @param TypeID
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getArticleByTypeID")
	public String getArticleByTypeID(@FormParam("ReleaseOrAll") String ReleaseOrAll,
			@FormParam("CategoryID") String CategoryID,
			@FormParam("TypeID") String TypeID, @Context HttpHeaders headers){
		try {
			if(ReleaseOrAll.equals("release")){
				Collection<Article> releaseaRticles = this.articleManager.getRelease(CategoryID,TypeID);
				if(releaseaRticles.size() > 0){
					return getResponse(releaseaRticles);
				}
			}else if(ReleaseOrAll.equals("all")){
				Collection<Article> allArticles = this.articleManager.getByCategoryIDAndTypeID(CategoryID, TypeID);
				if(allArticles.size() > 0){
					return getResponse(allArticles);
				}
			}
		} catch (GalaxyLabException ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
		return getErrorResponse();
	}
	/**
	 * 获取所有文章
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getAllArticle")
	public String getAllArticle(@Context HttpHeaders headers){
		try {
			return getResponse(this.articleManager.getAllArticle());
		} catch (GalaxyLabException ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	/**
	 * 根据类别名称获取文章
	 * @param TypeName
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getByTypeName")
	public String getByTypeName(@FormParam("TypeName") String TypeName, @Context HttpHeaders headers){
		try {
			return getResponse(this.articleManager.getByTypeName(TypeName));
		} catch (GalaxyLabException ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	/**
	 * 获取文章下的内容
	 * @param ArticleID
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getArticleContent")
	public String getArticleContent(@FormParam("ArticleID") String ArticleID, @Context HttpHeaders headers){
		try {
			return getResponse(this.articleManager.getArticleContent(ArticleID));
		} catch (GalaxyLabException ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	/**
	 * 获取回收站的文章
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getbyRemove")
	public String getbyRemove(@Context HttpHeaders headers){
		try {
			return getResponse(this.articleManager.getbyRemove());
		} catch (GalaxyLabException ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	/**
	 * 彻底删除文章及内容
	 * @param ArticleID
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/deleteArticle")
	public String deleteArticle(@FormParam("ArticleID") String ArticleID, @Context HttpHeaders headers){
		try {
			this.articleManager.deleteArticle(ArticleID);
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
	 * 还原文章
	 * @param ArticleID
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/restoreArticle")
	public String restoreArticle(@FormParam("ArticleID") String ArticleID, @Context HttpHeaders headers){
		try {
			this.articleManager.restoreArticle(ArticleID);
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
	 * 删除文集,将其下所有文集类型删除,并移除所有文章到回收站;
	 * 删除文集类型,将其下所有文章移动到回收站;
	 * 删除专题下的文章,移动回收站;
	 * 删除文章,移动到回收站
	 * @param CategoryID
	 * @param TypeID
	 * @param SubjectID
	 * @param ArticleID
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/remove")
	public String remove(@FormParam("CategoryID") String CategoryID,
			@FormParam("TypeID") String TypeID,
			@FormParam("SubjectID") String SubjectID,
			@FormParam("ArticleID") String ArticleID, @Context HttpHeaders headers){
		try {
			this.articleManager.remove(CategoryID, TypeID, SubjectID, ArticleID);
			return getSuccessResponse();
		} catch (GalaxyLabException ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	
	
	
}
