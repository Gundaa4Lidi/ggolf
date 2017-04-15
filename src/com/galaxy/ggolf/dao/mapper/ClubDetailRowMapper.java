package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.galaxy.ggolf.domain.ClubDetail;

public class ClubDetailRowMapper implements RowMapper<ClubDetail> {
    public static final String COLUMN_UID = "UID";
    public static final String COLUMN_ClubID = "ClubID";
    public static final String COLUMN_ClubName = "ClubName";
    public static final String COLUMN_Mode = "Mode";
    public static final String COLUMN_TotalHole = "TotalHole";
    public static final String COLUMN_TotalStemNum = "TotalStemNum";
    public static final String COLUMN_PhoneNum = "PhoneNum";
    public static final String COLUMN_CreateTime = "CreateTime";
    public static final String COLUMN_Stylist = "Stylist";
    public static final String COLUMN_Square = "Square";
    public static final String COLUMN_Length = "Length";
    public static final String COLUMN_PuttingSeed = "PuttingSeed";
    public static final String COLUMN_FairwaySeed = "FairwaySeed";
    public static final String COLUMN_Address = "Address";
    public static final String COLUMN_Intro = "Intro";
    public static final String COLUMN_Updated_TS = "Updated_TS";
    public static final String COLUMN_PhotoList = "PhotoList";
    public static final String COLUMN_MapImg = "MapImg";
    public static final String COLUMN_Facility = "Facility";
    
	@Override
	public ClubDetail mapRow(ResultSet res) throws SQLException {
		String UID = res.getString(COLUMN_UID);
		String ClubID = res.getString(COLUMN_ClubID);
		String ClubName = res.getString(COLUMN_ClubName);
		String Mode = res.getString(COLUMN_Mode);
		String TotalHole = res.getString(COLUMN_TotalHole);
		String TotalStemNum = res.getString(COLUMN_TotalStemNum);
		String PhoneNum = res.getString(COLUMN_PhoneNum);
		String CreateTime = res.getString(COLUMN_CreateTime);
		String Stylist = res.getString(COLUMN_Stylist);
		String Square = res.getString(COLUMN_Square);
		String Length = res.getString(COLUMN_Length);
		String PuttingSeed = res.getString(COLUMN_PuttingSeed);
		String FairwaySeed = res.getString(COLUMN_FairwaySeed);
		String Address = res.getString(COLUMN_Address);
		String Intro = res.getString(COLUMN_Intro);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		
		String PhotoList = res.getString(COLUMN_PhotoList);
		Collection<String> Photos = StringToList(PhotoList);
		
		String MapImg = res.getString(COLUMN_MapImg);
		Collection<String> mapImgs = StringToList(MapImg);
		
		String Facility = res.getString(COLUMN_Facility);
//		Collection<String> facility = StringToList(Facility);

		return new ClubDetail(UID,ClubID,ClubName,Mode,TotalHole,TotalStemNum,PhoneNum,CreateTime,Stylist,Square,Length,PuttingSeed,
							FairwaySeed,Address,Intro,Updated_TS,Photos,mapImgs,Facility);
	}

	private Collection<String> StringToList(String str){
		Collection<String> list = new ArrayList<String>();
		if(str != null){
			if(str.indexOf(";")!=-1){
				String[] string = str.split(";");
				for(String s : string){
					if(!s.equals("")){
						list.add(s);
					}
				}
			}else{
				if(!str.equals("")){
					list.add(str);
				}
			}
		}
		return list;
	}

}
