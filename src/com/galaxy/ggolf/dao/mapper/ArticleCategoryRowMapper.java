package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.ArticleCategory;

public class ArticleCategoryRowMapper implements RowMapper<ArticleCategory> {
    public static final String COLUMN_CategoryID = "CategoryID";
    public static final String COLUMN_CategoryName = "CategoryName";
    public static final String COLUMN_SubOrNot = "SubOrNot";
    public static final String COLUMN_Created_TS = "Created_TS";
    public static final String COLUMN_Updated_TS = "Updated_TS";

	@Override
	public ArticleCategory mapRow(ResultSet res) throws SQLException {
	    String CategoryID = res.getString(COLUMN_CategoryID);
	    String CategoryName = res.getString(COLUMN_CategoryName);
	    String SubOrNot = res.getString(COLUMN_SubOrNot);
	    String Created_TS = res.getString(COLUMN_Created_TS);
	    String Updated_TS = res.getString(COLUMN_Updated_TS);
		return new ArticleCategory(CategoryID, CategoryName, SubOrNot, Created_TS, Updated_TS, null);
	}

}
