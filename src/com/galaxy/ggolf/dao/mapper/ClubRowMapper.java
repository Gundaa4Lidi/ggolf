package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.galaxy.ggolf.domain.Club;

public class ClubRowMapper implements RowMapper<Club> {
	public static final String COLUMN_ClubID = "ClubID";
	public static final String COLUMN_ClubName = "ClubName";
	public static final String COLUMN_ClubPhoneNumber = "ClubPhoneNumber";
	public static final String COLUMN_ClubAddress = "ClubAddress";
	public static final String COLUMN_ClubType = "ClubType";
	public static final String COLUMN_ClubPhoto = "ClubPhoto";
	public static final String COLUMN_Province = "Province";
	public static final String COLUMN_City = "City";
	public static final String COLUMN_Area = "Area";
	public static final String COLUMN_Price = "Price";
	public static final String COLUMN_DiscountPrice = "DiscountPrice";
	public static final String COLUMN_TotalStemNum = "TotalStemNum";
	public static final String COLUMN_TotalHole = "TotalHole";
	public static final String COLUMN_Longitude = "Longitude";
	public static final String COLUMN_Latitude = "Latitude";
	public static final String COLUMN_Created_TS = "Created_TS";
	
	
	@Override
	public Club mapRow(ResultSet res) throws SQLException {
	    String ClubID = res.getString(COLUMN_ClubID);
	    String ClubName = res.getString(COLUMN_ClubName);
	    String ClubPhoneNumber = res.getString(COLUMN_ClubPhoneNumber);
	    String ClubAddress = res.getString(COLUMN_ClubAddress);
	    String ClubType = res.getString(COLUMN_ClubType);
	    String ClubPhoto = res.getString(COLUMN_ClubPhoto);
	    Collection<String> clubPhotos = new ArrayList<String>(); 
	    if(ClubPhoto != null)
			if(ClubPhoto.indexOf(";")!=-1){
				String[] string = ClubPhoto.split(";");
				for(String photoString : string){
					if(!photoString.equals("")){
						clubPhotos.add(photoString);
					}
				}
			}else{
				if(ClubPhoto.equals("")){
					clubPhotos.add(ClubPhoto);
				}
			}
	    String Province = res.getString(COLUMN_Province);
	    String City = res.getString(COLUMN_City);
	    String Area = res.getString(COLUMN_Area);
	    String Price = res.getString(COLUMN_Price);
	    String DiscountPrice = res.getString(COLUMN_DiscountPrice);
	    String TotalStemNum = res.getString(COLUMN_TotalStemNum);
	    String TotalHole = res.getString(COLUMN_TotalHole);
	    String Longitude = res.getString(COLUMN_Longitude);
	    String Latitude = res.getString(COLUMN_Latitude);
	    String Created_TS = res.getString(COLUMN_Created_TS);
        return new Club(ClubID,ClubName,ClubPhoneNumber,ClubAddress,ClubType,clubPhotos,Province,City,Area,Price,DiscountPrice,TotalStemNum,TotalHole,Longitude,Latitude,Created_TS);
	}

}
