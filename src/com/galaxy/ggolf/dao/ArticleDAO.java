package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.ArticleRowMapper;
import com.galaxy.ggolf.domain.Article;

public class ArticleDAO extends GenericDAO<Article> {
	
	public ArticleDAO() {
		super(new ArticleRowMapper());
		// TODO Auto-generated constructor stub
	}

	//创建文章
	public boolean create(Article art){
		String sql = "insert into article(CategoryID,Title,Cover,Content,TypeID,TypeName,SubjectID,Created_TS)"
				+ "values(?,?,?,?,?,?,?,?)";
		return super.sqlUpdate(sql, art.getCategoryID(),art.getTitle(),art.getCover(),art.getContent(),
				art.getTypeID(),art.getTypeName(),art.getSubjectID(),Time());
	}
	
	//获取全部文章
	public Collection<Article> getAll(){
		String sql = "select * from article where RemoveFlag is null and DeletedFlag is null order by Created_TS desc ";
		return super.executeQuery(sql);
	}
	
	//搜索文章
	public Collection<Article> getbyKeyword(String rows,String sqlString){
		String size = "";
		if(!rows.equals("")||rows != null){
			size = "limit 0 , "+Integer.parseInt(rows)+"";
		}
		String sql ="select * from article where ReleaseOrNot='Y' "+sqlString+" and DeletedFlag is null order by Created_TS desc "+size+"";
		return super.executeQuery(sql);
	}
	
	//根据文章类型获取文章
	public Collection<Article> getByType(String TypeName){
		String sql = "select * from article where TypeName like '%"+TypeName+"%' and RemoveFlag is null and DeletedFlag is null order by Created_TS desc";
		return super.executeQuery(sql);
	}
	
	public Collection<Article> groupbyCreateTime(){
		String sql = "select * from article where DeletedFlag is null and RemoveFlag is null GROUP BY date_format(`Created_TS`,'%Y-%m-%d') order by Created_TS desc";
		return super.executeQuery(sql);
	}
	
	public Collection<Article> getByCreated_TS(String date){
		String sql = "select * from article where date_format(Created_TS,'%Y-%m-%d')='"+date+"' and DeletedFlag is null and RemoveFlag is null order by Created_TS desc";
		return super.executeQuery(sql);
	}
	
	//修改文集类型名称
	public boolean updateTypeName(String ArticleID,String TypeName){
		String sql = "update article set TypeName='"+TypeName+"' where DeletedFlag is null and ArticleID='"+ArticleID+"'";
		return super.executeUpdate(sql);
	}
	
	//保存文章,发布文章
	public boolean saveArticle(Article art){
		String sql = "update article set CategoryID=?,Title=?,Cover=?,Content=?,RootIN=?,ReleaseName=?,ReleaseID=?,Released_TS=?,TypeID=?,TypeName=?,ReleaseOrNot=?,Updated_TS=? WHERE ArticleID=? and RemoveFlag is null and DeletedFlag is null";
		String Released_TS = null;
		if(art.getReleaseOrNot() == "Y"){
			Released_TS = Time();
		}
		return super.sqlUpdate(sql, art.getCategoryID(),art.getTitle(),art.getCover(),art.getContent(),art.getRootIN(),
				art.getReleaseName(),art.getReleaseID(),Released_TS,art.getTypeID(),art.getTypeName(),
				art.getReleaseOrNot(),Time(),art.getArticleID());
	}
	//查看文章是否存在
	public Article getByArticleID(String ArticleID){
		String sql = "select * from article where ArticleID='"+ArticleID+"' and RemoveFlag is null and DeletedFlag is null";
		Collection<Article> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (Article) result.toArray()[0];
		}
		return null;
	}
	
	//获取分类下的文章
	public Collection<Article> getbyCategoryIDAndTypeID(String CategoryID,String TypeID){
		String sql = "select * from article where CategoryID='"+CategoryID+"' and TypeID='"+TypeID+"' and SubjectID is null "
				+ "and RemoveFlag is null and DeletedFlag is null order by Created_TS desc";
		return super.executeQuery(sql);
	}
	//获取已发布的文章
	public Collection<Article> getRelease(String CategoryID,String TypeID){
		String sql = "select * from article where CategoryID='"+CategoryID+"' and TypeID='"+TypeID+"' and SubjectID is null"
				+ " and ReleaseOrNot='Y' and RemoveFlag is null and DeletedFlag is null"
				+ " order by(`Released_TS` or `Created_TS`) desc";
		return super.executeQuery(sql);
	}
	
	//获取专题的文章
	public Collection<Article> getbySubjectID(String SubjectID){
		String sql = "select * from article where SubjectID='"+SubjectID+"'"
				+ " and RemoveFlag is null and DeletedFlag is null order by Created_TS desc";
		return super.executeQuery(sql);
	}
	
	//获取专题下发布的文章
	public Collection<Article> getReleasebySubjectID(String SubjectID){
		String sql = "select * from article where SubjectID='"+SubjectID+"'"
				+ " and ReleaseOrNot='Y' and RemoveFlag is null and DeletedFlag is null"
				+ " order by(`Released_TS` or `Created_TS`) desc";
		return super.executeQuery(sql);
	}
	
	//获取回收站的文章
	public Collection<Article> getbyRemove(){
		String sql = "select * from article where RemoveFlag='Y' and DeletedFlag is null"
				+ " order by(`Released_TS` or `Created_TS`) desc";
		return super.executeQuery(sql);
	}
	
	//还原回收站的文章
	public boolean restoreArticle(String ArticleID){
		String sql = "update article set RemoveFlag=NULL,Updated_TS='"+Time()+"' where RemoveFlag='Y' and ArticleID='"+ArticleID+"' and DeletedFlag is null";
		return super.executeUpdate(sql);
	}
	
	//还原全部文章
	public boolean restoreAll(){
		String sql = "update article set RemoveFlag=NULL,Updated_TS='"+Time()+"' where RemoveFlag='Y' and DeletedFlag is null";
		return super.executeUpdate(sql);
	}
	
	//将文章放入回收站
	public boolean remove(String ArticleID){
		String sql = "update article set RemoveFlag='Y',Updated_TS='"+Time()+"' where ArticleID='"+ArticleID+"'";
		return super.executeUpdate(sql);
	}
	
	//将回收站的文章删除
	public boolean delete(String ArticleID){
		String sql = "update article set DeletedFlag='Y' where ArticleID='"+ArticleID+"' and RemoveFlag='Y'";
		return super.executeUpdate(sql);
	}
	
	
}
