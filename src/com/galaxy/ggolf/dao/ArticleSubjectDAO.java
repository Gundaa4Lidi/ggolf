package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.ArticleSubjectRowMapper;
import com.galaxy.ggolf.domain.ArticleSubject;
import com.galaxy.ggolf.domain.ArticleType;

public class ArticleSubjectDAO extends GenericDAO<ArticleSubject> {

	public ArticleSubjectDAO() {
		super(new ArticleSubjectRowMapper());
		// TODO Auto-generated constructor stub
	}
	
	public boolean create(ArticleSubject as){
		String sql = "insert into article_subject(SubjectName,Attr,TypeID,CategoryID,Created_TS)values("
				+ "'"+as.getSubjectName()+"','"+as.getAttr()+"','"+as.getTypeID()+"','"+as.getCategoryID()+"','"+Time()+"')";
		return super.executeUpdate(sql);
	}
	public boolean update(ArticleSubject as){
		String sql = "update article_subject set SubjectName='"+as.getSubjectName()+"', Attr='"+as.getAttr()+"' where SubjectID='"+as.getSubjectID()+"' and DeletedFlag is null";
		return super.executeUpdate(sql);
	}
	public Collection<ArticleSubject> getByTypeID(String TypeID){
		String sql = "select * from article_subject where TypeID='"+TypeID+"' and DeletedFlag is null";
		return super.executeQuery(sql);
	}
	
	public ArticleSubject getSubject(String SubjectID){
		String sql = "select * from article_subject where SubjectID='"+SubjectID+"' and DeletedFlag is null";
		Collection<ArticleSubject> result = super.executeQuery(sql);
		if(result.size() > 0) {
			return (ArticleSubject) result.toArray()[0];
		}
		return null;
	}
	
	public ArticleSubject getSubjectByDelete(String SubjectID){
		String sql = "select * from article_subject where SubjectID='"+SubjectID+"' and DeletedFlag='Y'";
		Collection<ArticleSubject> result = super.executeQuery(sql);
		if(result.size() > 0) {
			return (ArticleSubject) result.toArray()[0];
		}
		return null;
	}
	
	public boolean delete(String SubjectID){
		String sql = "update article_subject set DeletedFlag='Y',Updated_TS='"+Time()+"' where SubjectID='"+SubjectID+"'";
		return super.executeUpdate(sql);
	}
	
	
	
	public boolean restore(String SubjectID){
		String sql = "update article_subject set DeletedFlag=NULL,Updated_TS='"+Time()+"' where SubjectID='"+SubjectID+"' and DeletedFlag='Y'";
		return super.executeUpdate(sql);
	}
}
