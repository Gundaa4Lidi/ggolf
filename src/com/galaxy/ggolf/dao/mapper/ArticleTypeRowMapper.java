package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.ArticleType;

public class ArticleTypeRowMapper implements RowMapper<ArticleType> {

    public static final String COLUMN_TypeID = "TypeID";
    public static final String COLUMN_TypeName = "TypeName";
    public static final String COLUMN_TypeKey = "TypeKey";
    public static final String COLUMN_CategoryID = "CategoryID";
    public static final String COLUMN_Created_TS = "Created_TS";
    public static final String COLUMN_Updated_TS = "Updated_TS";

	@Override
	public ArticleType mapRow(ResultSet res) throws SQLException {
		String TypeID = res.getString(COLUMN_TypeID);
		String TypeName = res.getString(COLUMN_TypeName);
		String TypeKey = res.getString(COLUMN_TypeKey);
		String CategoryID = res.getString(COLUMN_CategoryID);
		String Created_TS = res.getString(COLUMN_Created_TS);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		return new ArticleType(TypeID, TypeName, TypeKey, CategoryID, Created_TS, Updated_TS, null,null);
	}

}
