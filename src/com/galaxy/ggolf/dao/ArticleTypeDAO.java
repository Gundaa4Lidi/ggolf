package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.ArticleTypeRowMapper;
import com.galaxy.ggolf.domain.ArticleType;

public class ArticleTypeDAO extends GenericDAO<ArticleType> {

	public ArticleTypeDAO() {
		super(new ArticleTypeRowMapper());
		// TODO Auto-generated constructor stub
	}
	
	public boolean create(ArticleType at){
		String sql = "insert into article_type(TypeName,TypeKey,CategoryID,Created_TS)values("
				+ "'"+at.getTypeName()+"','"+at.getTypeKey()+"','"+at.getCategoryID()+"','"+Time()+"')";
		return super.executeUpdate(sql);
	}
	
	public boolean update(ArticleType at){
		String sql = "update article_type set TypeName='"+at.getTypeName()+"',TypeKey='"+at.getTypeKey()+"' where TypeID='"+at.getTypeID()+"' and DeletedFlag is null";
		return super.executeUpdate(sql);
	}
	public Collection<ArticleType> getByCategoryID(String CategoryID){
		String sql = "select * from article_type where CategoryID='"+CategoryID+"' and DeletedFlag is null";
		return super.executeQuery(sql);
	}
	
	public ArticleType getType(String TypeID){
		String sql = "select * from article_type where TypeID='"+TypeID+"' and DeletedFlag is null";
		Collection<ArticleType> result = super.executeQuery(sql);
		if(result.size() > 0) {
			return (ArticleType) result.toArray()[0];
		}
		return null;
	}
	public ArticleType getTypeByDelete(String TypeID){
		String sql = "select * from article_type where TypeID='"+TypeID+"' and DeletedFlag='Y'";
		Collection<ArticleType> result = super.executeQuery(sql);
		if(result.size() > 0) {
			return (ArticleType) result.toArray()[0];
		}
		return null;
	}
	
	public boolean delete(String TypeID){
		String sql = "update article_type set DeletedFlag='Y',Updated_TS='"+Time()+"' where TypeID='"+TypeID+"'";
		return super.executeUpdate(sql);
	}
	
	public boolean restore(String TypeID){
		String sql = "update article_type set DeletedFlag=NULL,Updated_TS='"+Time()+"' where TypeID='"+TypeID+"' and DeletedFlag='Y'";
		return super.executeUpdate(sql);
	}

}
