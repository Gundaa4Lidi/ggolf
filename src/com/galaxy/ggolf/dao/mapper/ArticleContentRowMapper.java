package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.ArticleContent;

public class ArticleContentRowMapper implements RowMapper<ArticleContent> {
    public static final String COLUMN_ACID = "ACID";
    public static final String COLUMN_ArticleID = "ArticleID";
    public static final String COLUMN_Content = "Content";
    public static final String COLUMN_Created_TS = "Created_TS";
    public static final String COLUMN_Updated_TS = "Updated_TS";
	@Override
	public ArticleContent mapRow(ResultSet res) throws SQLException {
		String ACID = res.getString(COLUMN_ACID);
		String ArticleID = res.getString(COLUMN_ArticleID);
		String Content = res.getString(COLUMN_Content);
		String Created_TS = res.getString(COLUMN_Created_TS);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		return new ArticleContent(ACID,ArticleID,Content,Created_TS,Updated_TS);
	}

}
