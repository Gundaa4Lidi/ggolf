package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.PhotoAlbum;

public class PhotoAlbumRowMapper implements RowMapper<PhotoAlbum> {

    public final static String COLUMN_PhotoID = "PhotoID";
    public final static String COLUMN_Type = "Type";
    public final static String COLUMN_Url = "Url";
    public final static String COLUMN_Sort = "Sort";
    public final static String COLUMN_Created_TS = "Created_TS";
    public final static String COLUMN_Updated_TS = "Updated_TS";
	@Override
	public PhotoAlbum mapRow(ResultSet res) throws SQLException {

	    String PhotoID = res.getString(COLUMN_PhotoID);
	    String Type = res.getString(COLUMN_Type);
	    String Url = res.getString(COLUMN_Url);
	    String Sort = res.getString(COLUMN_Sort);
	    String Created_TS = res.getString(COLUMN_Created_TS);
	    String Updated_TS = res.getString(COLUMN_Updated_TS);
		return new PhotoAlbum(PhotoID,Type,Url,Sort,Created_TS,Updated_TS);
	}

}
