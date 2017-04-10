package com.galaxy.ggolf.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.cache.GenericCache;
import com.galaxy.ggolf.dao.ArticleCategoryDAO;
import com.galaxy.ggolf.dao.ArticleContentDAO;
import com.galaxy.ggolf.dao.ArticleDAO;
import com.galaxy.ggolf.dao.ArticleSubjectDAO;
import com.galaxy.ggolf.dao.ArticleTypeDAO;
import com.galaxy.ggolf.domain.Article;
import com.galaxy.ggolf.domain.ArticleCategory;
import com.galaxy.ggolf.domain.ArticleContent;
import com.galaxy.ggolf.domain.ArticleSubject;
import com.galaxy.ggolf.domain.ArticleType;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.dto.GroupData;

public class ArticleManager {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private GenericCache<String, ArticleContent> acache;
	
	private ArticleDAO articleDAO;
	private ArticleContentDAO articleContentDAO;
	private ArticleTypeDAO articleTypeDAO ;
	private ArticleCategoryDAO articleCategoryDAO;
	private ArticleSubjectDAO articleSubjectDAO;
	
	public ArticleManager(ArticleDAO articleDAO, ArticleContentDAO articleContentDAO, ArticleTypeDAO articleTypeDAO,
			ArticleCategoryDAO articleCategoryDAO, ArticleSubjectDAO articleSubjectDAO) {
		this.acache = new GenericCache<String, ArticleContent>();
		this.articleDAO = articleDAO;
		this.articleContentDAO = articleContentDAO;
		this.articleTypeDAO = articleTypeDAO;
		this.articleCategoryDAO = articleCategoryDAO;
		this.articleSubjectDAO = articleSubjectDAO;
	}
	
	//创建或保存文集
	public void saveArticleCategory(ArticleCategory ac)throws Exception{
		if(ac.getCategoryID() != null){
			if(!this.articleCategoryDAO.update(ac)){
				throw new GalaxyLabException("Error in update ArticleCategory");
			}
		}else{
			if(!this.articleCategoryDAO.create(ac)){
				throw new GalaxyLabException("Error in create ArticleCategory");
			}
		}
	}
	
	//创建或保存文集类型
	public void saveArticleType(ArticleType at)throws Exception{
		if(at.getTypeID() != null){
			if(!this.articleTypeDAO.update(at)){
				throw new GalaxyLabException("Error in update ArticleType");
			}else{
				Collection<Article> articles = this.articleDAO.getbyCategoryIDAndTypeID(at.getCategoryID(), at.getTypeID());
				for(Article art : articles){
					if(!this.articleDAO.updateTypeName(art.getArticleID(), at.getTypeName())){
						throw new GalaxyLabException("Error in update TypeName with "+art.getArticleID());
					}
				}
			}
		}else{
			if(!this.articleTypeDAO.create(at)){
				throw new GalaxyLabException("Error in create ArticleType");
			}
		}
	}
	//创建或保存专题
	public void saveArticleSubject(ArticleSubject as)throws Exception{
		if(as.getSubjectID() != null){
			if(!this.articleSubjectDAO.update(as)){
				throw new GalaxyLabException("Error in update ArticleSubject");
			}
		}else{
			if(!this.articleSubjectDAO.create(as)){
				throw new GalaxyLabException("Error in create ArticleSubject");
			}
		}
	}
	
	//创建或保存文章
	public void saveArticle(Article art)throws Exception{
		if(art.getArticleID() != null){
			if(!this.articleDAO.saveArticle(art)){
				throw new GalaxyLabException("Error in update Article");
			}
		}else{
			if(!this.articleDAO.create(art)){
				throw new GalaxyLabException("Error in create Article");
			}
		}
	}
	//创建或保存文章内容
	public void saveArticleContent(ArticleContent ac)throws Exception{
		if(this.articleContentDAO.getByArticleID(ac.getArticleID())!= null){
			if(!this.articleContentDAO.updateContent(ac.getContent(), ac.getArticleID())){
				throw new GalaxyLabException("Error in update ArticleContent");
			}
		}else{
			if(!this.articleContentDAO.create(ac)){
				throw new GalaxyLabException("Error in create ArticleContent"); 
			}
		}
	}
	
	//获取文章下的内容
	public ArticleContent getArticleContent(String ArticleID)throws Exception{
		return this.articleContentDAO.getByArticleID(ArticleID);
	}
	
	//获取回收站的文章
	public Collection<Article> getbyRemove()throws Exception{
		return this.articleDAO.getbyRemove();
	}
	
	//获取分类下的文章
	public Collection<Article> getByCategoryIDAndTypeID(String CategoryID, String TypeID)throws Exception{
		return this.articleDAO.getbyCategoryIDAndTypeID(CategoryID, TypeID);
	}
	//获取已发布的文章
	public Collection<Article> getRelease(String CategoryID, String TypeID)throws Exception{
		return this.articleDAO.getRelease(CategoryID, TypeID);
	}
	//获取专题下的文章
	public Collection<Article> getBySubjectID(String SubjectID)throws Exception{
		return this.articleDAO.getbySubjectID(SubjectID);
	}
	
	//获取专题下发布的文章
	public Collection<Article> getReleaseBySubjectID(String SubjectID)throws Exception{
		return this.articleDAO.getReleasebySubjectID(SubjectID);
	}
	
	//获取全部文章
	public Collection<GroupData<Article>> getAllArticle()throws Exception{
		Collection<Article> group = this.articleDAO.groupbyCreateTime();
		Collection<GroupData<Article>> articleDatas = new ArrayList<GroupData<Article>>();
		for(Article art : group){
			String date = art.getCreated_TS().substring(0, 10);
			Collection<Article> article = this.articleDAO.getByCreated_TS(date);
			if(article.size() > 0){
				GroupData<Article> result = new GroupData<Article>(date,article);
				articleDatas.add(result);
			}
		}
		return articleDatas;
	}
	//根据类别名称获取文章
	public Collection<Article> getByTypeName(String TypeName)throws Exception{
		return this.articleDAO.getByType(TypeName);
	}
	//查看文章是否存在
	public Article getByArticleID(String ArticleID){
		return this.articleDAO.getByArticleID(ArticleID);
	}
	
	//获取全部文集
	public Collection<ArticleCategory> getAllCategory()throws Exception{
		return this.articleCategoryDAO.getAll();
	}
	//查看文集是否存在
	public ArticleCategory getbyCategoryID(String CategoryID)throws Exception{
		return this.articleCategoryDAO.getbyCategoryID(CategoryID);
	}
	//获取文集下的类别
	public Collection<ArticleType> getTypeByCategoryID(String CategoryID)throws Exception{
		return this.articleTypeDAO.getByCategoryID(CategoryID);
	}
	//获取类别下的专题
	public Collection<ArticleSubject> getSubjectByTypeID(String TypeID)throws Exception{
		return this.articleSubjectDAO.getByTypeID(TypeID);
	}
	
	//查看类别是否存在
	public ArticleType getType(String TypeID)throws Exception{
		return this.articleTypeDAO.getType(TypeID);
	}
	
	//彻底删除文章及内容
	public void deleteArticle(String ArticleID)throws Exception{
		if(!this.articleDAO.delete(ArticleID)){
			throw new GalaxyLabException("Error in delete Article");
		}else{
			ArticleContent ac = this.articleContentDAO.getByArticleID(ArticleID);
			if(ac!=null){
				if(!this.articleContentDAO.delete(ArticleID)){
					throw new GalaxyLabException("Error in delete ArticleContent");
				}
			}
		}
		
	}
	
	//还原文章
	public void restoreArticle(String ArticleID)throws Exception{
		if(!this.articleDAO.restoreArticle(ArticleID)){
			throw new GalaxyLabException("Error in restore Article");
		}else{
			Article art = this.articleDAO.getByArticleID(ArticleID);
			if(art!=null){
				if(art.getSubjectID()!=null){
					if(this.articleSubjectDAO.getSubjectByDelete(art.getSubjectID())!=null){
						if(!this.articleSubjectDAO.restore(art.getSubjectID())){
							throw new GalaxyLabException("Error in restore ArticleSubject");
						}
					}
				}
				if(this.articleTypeDAO.getTypeByDelete(art.getTypeID())!=null){
					if(!this.articleTypeDAO.restore(art.getTypeID())){
						throw new GalaxyLabException("Error in restore ArticleType");
					}else{
						if(this.articleCategoryDAO.getCategoryByDelete(art.getCategoryID())!=null){
							if(!this.articleCategoryDAO.restore(art.getCategoryID())){
								throw new GalaxyLabException("Error in restore ArticleCategory");
							}
						}
					}
				}
			}
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
	 * @throws Exception
	 */
	public void remove(String CategoryID,String TypeID,String SubjectID,String ArticleID)throws Exception{
		//删除文集,将其下所有文集类型删除,并移除所有文章到回收站;
		if(CategoryID!=null && TypeID==null && SubjectID==null && ArticleID==null){
			if(!this.articleCategoryDAO.delete(CategoryID)){
				throw new GalaxyLabException("Error in delete ArticleCategory");
			}else{
				Collection<ArticleType> atList = this.articleTypeDAO.getByCategoryID(CategoryID);
				if(atList .size() > 0){
					for(ArticleType at : atList){
						if(!this.articleTypeDAO.delete(at.getTypeID())){
							throw new GalaxyLabException("Error in delete ArticleType");
						}else{
							Collection<ArticleSubject> articleSubjects = this.articleSubjectDAO.getByTypeID(at.getTypeID());
							if(articleSubjects.size() > 0){
								for(ArticleSubject as : articleSubjects){
									if(!this.articleSubjectDAO.delete(as.getSubjectID())){
										throw new GalaxyLabException("Error in remove ArticleSubject");
									}else{
										Collection<Article> artList = this.articleDAO.getbySubjectID(as.getSubjectID());
										removeArt(artList);
									}
								}
							}
							Collection<Article> artList = this.articleDAO.getbyCategoryIDAndTypeID(at.getCategoryID(),at.getTypeID());
							removeArt(artList);
						}
					}
					
				}
			}
		}
		//删除文集类型,将其下所有文章移动到回收站
		if(CategoryID!=null && TypeID!=null && SubjectID==null && ArticleID==null){
			if(!this.articleTypeDAO.delete(TypeID)){
				throw new GalaxyLabException("Error in delete ArticleType");
			}else{
				Collection<ArticleSubject> articleSubjects = this.articleSubjectDAO.getByTypeID(TypeID);
				if(articleSubjects.size() > 0){
					for(ArticleSubject as : articleSubjects){
						if(!this.articleSubjectDAO.delete(as.getSubjectID())){
							throw new GalaxyLabException("Error in remove ArticleSubject");
						}else{
							Collection<Article> artList = this.articleDAO.getbySubjectID(as.getSubjectID());
							removeArt(artList);
						}
					}
				}
				Collection<Article> artList = this.articleDAO.getbyCategoryIDAndTypeID(CategoryID,TypeID);
				removeArt(artList);
			}
		}
		//删除专题下的文章,移动回收站
		if(CategoryID!=null && TypeID!=null && SubjectID!=null && ArticleID==null){
			if(!this.articleSubjectDAO.delete(SubjectID)){
				throw new GalaxyLabException("Error in remove ArticleSubject");
			}else{
				Collection<Article> artList = this.articleDAO.getbySubjectID(SubjectID);
				removeArt(artList);
			}
		}
		//删除文章,移动到回收站
		if(CategoryID!=null && TypeID!=null && ArticleID!=null){
			if(!this.articleDAO.remove(ArticleID)){
				throw new GalaxyLabException("Error in remove Article");
			}
		}
	}
	
	public void removeArt(Collection<Article> artList)throws Exception{
		if(artList.size() > 0){
			for(Article art : artList){
				if(!this.articleDAO.remove(art.getArticleID())){
					throw new GalaxyLabException("Error in remove Article");
				}
			}
		}
	}
	
	
	
}
