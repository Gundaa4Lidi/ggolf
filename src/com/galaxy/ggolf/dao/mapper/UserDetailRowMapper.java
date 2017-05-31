package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.galaxy.ggolf.domain.UserDetail;
import com.galaxy.ggolf.tools.ListUtil;

public class UserDetailRowMapper implements RowMapper<UserDetail> {
	public static final String COLUMN_UID = "UID";
	public static final String COLUMN_UserID = "UserID";
	public static final String COLUMN_PhoneNum = "PhoneNum";
    public static final String COLUMN_HeadPhoto = "HeadPhoto";
    public static final String COLUMN_UserName = "UserName";
    public static final String COLUMN_Sex = "Sex";
    public static final String COLUMN_Birthday = "Birthday";
    public static final String COLUMN_Age = "Age";
    public static final String COLUMN_Score = "Score";
    public static final String COLUMN_City = "City";
    public static final String COLUMN_PhotoList = "PhotoList";
    public static final String COLUMN_Signature = "Signature";
    public static final String COLUMN_Status = "Status";
    public static final String COLUMN_CustomTag = "CustomTag";
    public static final String COLUMN_Updated_TS = "Updated_TS";


	@Override
	public UserDetail mapRow(ResultSet res) throws SQLException {
		String UID = res.getString(COLUMN_UID);
		String UserID = res.getString(COLUMN_UserID);
		String PhoneNum = res.getString(COLUMN_PhoneNum);
		String HeadPhoto = res.getString(COLUMN_HeadPhoto);
		String UserName = res.getString(COLUMN_UserName);
		String Sex = res.getString(COLUMN_Sex);
		String Birthday = res.getString(COLUMN_Birthday);
		String Age = res.getString(COLUMN_Age);
		String Score = res.getString(COLUMN_Score);
		String City = res.getString(COLUMN_City);
		String PhotoList = res.getString(COLUMN_PhotoList);
		Collection<String> Photos = new ListUtil().StringToList(PhotoList,";");
		/*if(PhotoList != null)
		if(PhotoList.indexOf(";")!=-1){
			String[] string = PhotoList.split(";");
			for(String photoString : string){
				if(!photoString.equals("")){
					Photos.add(photoString);
				}
			}
		}else{
			if(PhotoList.equals("")){
				Photos.add(PhotoList);
			}
		}*/
		String Signature = res.getString(COLUMN_Signature);
		String Status = res.getString(COLUMN_Status);
		String CustomTag = res.getString(COLUMN_CustomTag);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		return new UserDetail(UID,UserID,PhoneNum,HeadPhoto,UserName,Sex,Birthday,Age,Score,City,Photos,Signature,Status,CustomTag,Updated_TS);
	}

}
