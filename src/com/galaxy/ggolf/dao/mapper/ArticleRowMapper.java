package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.Article;

public class ArticleRowMapper implements RowMapper<Article> {

    public static final String COLUMN_ArticleID = "ArticleID";
    public static final String COLUMN_CategoryID = "CategoryID";
    public static final String COLUMN_Title = "Title";
    public static final String COLUMN_Cover = "Cover";
    public static final String COLUMN_Content = "Content";
    public static final String COLUMN_RootIN = "RootIN";
    public static final String COLUMN_ReleaseName = "ReleaseName";
    public static final String COLUMN_ReleaseID = "ReleaseID";
    public static final String COLUMN_Released_TS = "Released_TS";
    public static final String COLUMN_TypeID = "TypeID";
    public static final String COLUMN_TypeName = "TypeName";
    public static final String COLUMN_SubjectID = "SubjectID";
    public static final String COLUMN_ReleaseOrNot = "ReleaseOrNot";
    public static final String COLUMN_Created_TS = "Created_TS";
    public static final String COLUMN_Updated_TS = "Updated_TS";

	@Override
	public Article mapRow(ResultSet res) throws SQLException {
		String ArticleID = res.getString(COLUMN_ArticleID);
		String CategoryID = res.getString(COLUMN_CategoryID);
		String Title = res.getString(COLUMN_Title);
		String Cover = res.getString(COLUMN_Cover);
		String Content = res.getString(COLUMN_Content);
		String RootIN = res.getString(COLUMN_RootIN);
		String ReleaseName = res.getString(COLUMN_ReleaseName);
		String ReleaseID = res.getString(COLUMN_ReleaseID);
		String Released_TS = res.getString(COLUMN_Released_TS);
		String TypeID = res.getString(COLUMN_TypeID);
		String TypeName = res.getString(COLUMN_TypeName);
		String SubjectID = res.getString(COLUMN_SubjectID);
		String ReleaseOrNot = res.getString(COLUMN_ReleaseOrNot);
		String Created_TS = res.getString(COLUMN_Created_TS);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		return new Article(ArticleID,CategoryID,Title,Cover,Content,RootIN,ReleaseName,ReleaseID,Released_TS,TypeID,TypeName,SubjectID,ReleaseOrNot,Created_TS,Updated_TS);
	}

}
