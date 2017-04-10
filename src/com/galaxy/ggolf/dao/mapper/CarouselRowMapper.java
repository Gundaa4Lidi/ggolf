package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.Carousel;

public class CarouselRowMapper implements RowMapper<Carousel> {
    private static final String COLUMN_UID = "UID";
    private static final String COLUMN_Title = "Title";
    private static final String COLUMN_Type = "Type";
    private static final String COLUMN_TypeID = "TypeID";
    private static final String COLUMN_Image = "Image";
    private static final String COLUMN_Url = "Url";
    private static final String COLUMN_Created_TS = "Created_TS";
    private static final String COLUMN_Updated_TS = "Updated_TS";
	@Override
	public Carousel mapRow(ResultSet res) throws SQLException {
		String UID = res.getString(COLUMN_UID);
		String Title = res.getString(COLUMN_Title);
		String Type = res.getString(COLUMN_Type);
		String TypeID = res.getString(COLUMN_TypeID);
		String Image = res.getString(COLUMN_Image);
		String Url = res.getString(COLUMN_Url);
		String Created_TS = res.getString(COLUMN_Created_TS);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		return new Carousel(UID,Title,Type,TypeID,Image,Url,Created_TS,Updated_TS);
	}

}
