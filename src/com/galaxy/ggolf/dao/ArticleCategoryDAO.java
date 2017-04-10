package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.ArticleCategoryRowMapper;
import com.galaxy.ggolf.domain.ArticleCategory;

public class ArticleCategoryDAO extends GenericDAO<ArticleCategory> {

	public ArticleCategoryDAO() {
		super(new ArticleCategoryRowMapper());
	}

	public boolean create(ArticleCategory ac){
		String sql = "insert into article_category(CategoryName,SubOrNot,Created_TS)values(?,?,?)";
		return super.sqlUpdate(sql,ac.getCategoryName(),ac.getSubOrNot(),Time());
	}
	
	public boolean update(ArticleCategory ac){
		String sql = "update article_category set CategoryName='"+ac.getCategoryName()+"', SubOrNot='"+ac.getSubOrNot()+"' where CategoryID='"+ac.getCategoryID()+"' and DeletedFlag is null";
		return super.executeUpdate(sql);
	}
	
	public Collection<ArticleCategory> getAll(){
		String sql = "select * from article_category where DeletedFlag is null order by Created_TS";
		return super.executeQuery(sql);
	}
	
	public ArticleCategory getbyCategoryID(String CategoryID){
		String sql = "select * from article_category where CategoryID='"+CategoryID+"' and DeletedFlag is null";
		Collection<ArticleCategory> result = super.executeQuery(sql);
		if(result.size() > 0 ){
			return (ArticleCategory) result.toArray()[0];
		}
		return null;
	}
	
	public ArticleCategory getCategoryByDelete(String CategoryID){
		String sql = "select * from article_category where CategoryID='"+CategoryID+"' and DeletedFlag='Y'";
		Collection<ArticleCategory> result = super.executeQuery(sql);
		if(result.size() > 0 ){
			return (ArticleCategory) result.toArray()[0];
		}
		return null;
	}
	
	public boolean delete(String CategoryID){
		String sql = "update article_category set DeletedFlag='Y',Updated_TS='"+Time()+"' where CategoryID='"+CategoryID+"'";
		return super.executeUpdate(sql);
	}
	
	public boolean restore(String CategoryID){
		String sql = "update article_category set DeletedFlag=NULL,Updated_TS='"+Time()+"' where CategoryID='"+CategoryID+"' and DeletedFlag='Y'";
		return super.executeUpdate(sql);
	}
}
