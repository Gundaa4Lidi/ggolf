package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.ArticleContentRowMapper;
import com.galaxy.ggolf.domain.ArticleContent;

public class ArticleContentDAO extends GenericDAO<ArticleContent> {

	public ArticleContentDAO() {
		super(new ArticleContentRowMapper());
		// TODO Auto-generated constructor stub
	}
	
	public boolean create(ArticleContent ac){
		String sql = "insert into article_content(ArticleID,Content,Created_TS)values('"+ac.getArticleID()
					+"','"+ac.getContent()+"','"+Time()+"')";
		return super.executeUpdate(sql);
	}
	
	public ArticleContent getByArticleID(String ArticleID){
		String sql = "select * from article_content where ArticleID='"+ArticleID+"' and DeletedFlag is null";
		Collection<ArticleContent> result = super.executeQuery(sql);
		if(result.size() > 0) {
			return (ArticleContent) result.toArray()[0];
		}
		return null; 
	}
	
	public boolean delete(String ArticleID){
		String sql = "update article_content set DeletedFlag='Y',Updated_TS='"+Time()+"' where ArticleID='"+ArticleID+"'";
		return super.executeUpdate(sql);
	}
	
	public boolean updateContent(String Content,String ArticleID){
		String sql = "update article_content set Content='"+Content+"',Updated_TS='"+Time()+"' where DeletedFlag is null and ArticleID='"+ArticleID+"'";
		return super.executeUpdate(sql);
	}
	
	

}
