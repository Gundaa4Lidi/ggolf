package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.ArticleSubject;

public class ArticleSubjectRowMapper implements RowMapper<ArticleSubject> {
	
	public static final String COLUMN_SubjectID = "SubjectID";
	public static final String COLUMN_SubjectName = "SubjectName";
	public static final String COLUMN_Attr = "Attr";
	public static final String COLUMN_Image = "Image";
	public static final String COLUMN_TypeID = "TypeID";
    public static final String COLUMN_CategoryID = "CategoryID";
    public static final String COLUMN_Created_TS = "Created_TS";
    public static final String COLUMN_Updated_TS = "Updated_TS";
	
	@Override
	public ArticleSubject mapRow(ResultSet res) throws SQLException {
		String SubjectID = res.getString(COLUMN_SubjectID);
		String SubjectName = res.getString(COLUMN_SubjectName);
		String Attr = res.getString(COLUMN_Attr);
		String Image = res.getString(COLUMN_Image); 
		String TypeID = res.getString(COLUMN_TypeID);
		String CategoryID = res.getString(COLUMN_CategoryID);
		String Created_TS = res.getString(COLUMN_Created_TS);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		return new ArticleSubject(SubjectID, SubjectName, Attr, TypeID, Image, CategoryID, Created_TS, Updated_TS, null);
	}

}
