package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.galaxy.ggolf.domain.ClubOrder;
import com.galaxy.ggolf.dto.Activity;

public class OrderRowMapper implements RowMapper<ClubOrder> {
	
	private static final String COLUMN_UID = "UID";
	private static final String COLUMN_OrderID = "OrderID";
	private static final String COLUMN_UserID = "UserID";
	private static final String COLUMN_ClubID = "ClubID";
	private static final String COLUMN_ClubName = "ClubName";
	private static final String COLUMN_ClubserveID = "ClubserveID";
	private static final String COLUMN_ClubserveName = "ClubserveName";
	private static final String COLUMN_ClubserveLimitTimeID = "ClubserveLimitTimeID";
	private static final String COLUMN_ClubservePriceID = "ClubservePriceID";
	private static final String COLUMN_State = "State";
	private static final String COLUMN_CreateTime = "CreateTime";
	private static final String COLUMN_Type = "Type";
	private static final String COLUMN_DownPayment = "DownPayment";
	private static final String COLUMN_PayBillorNot = "PayBillorNot";
	private static final String COLUMN_StartDate = "StartDate";
	private static final String COLUMN_StartTime = "StartTime";
	private static final String COLUMN_Names = "Names";
	private static final String COLUMN_Tel = "Tel";
	private static final String COLUMN_ServiceExplain = "ServiceExplain";
	private static final String COLUMN_ChargeID = "ChargeID";
	private static final String COLUMN_Created_TS = "Created_TS";
	private static final String COLUMN_Updated_TS = "Updated_TS";
	private static final String COLUMN_Activity = "Activity";

	@Override
	public ClubOrder mapRow(ResultSet res) throws SQLException {
		String UID = res.getString(COLUMN_UID);
		String OrderID = res.getString(COLUMN_OrderID);
		String UserID = res.getString(COLUMN_UserID);
		String ClubID = res.getString(COLUMN_ClubID);
		String ClubName = res.getString(COLUMN_ClubName);
		String ClubserveID = res.getString(COLUMN_ClubserveID);
		String ClubserveName = res.getString(COLUMN_ClubserveName);
		String ClubserveLimitTimeID = res.getString(COLUMN_ClubserveLimitTimeID);
		String ClubservePriceID = res.getString(COLUMN_ClubservePriceID);
		String State = res.getString(COLUMN_State);
		String CreateTime = res.getString(COLUMN_CreateTime);
		String Type = res.getString(COLUMN_Type);
		String DownPayment = res.getString(COLUMN_DownPayment);
		String PayBillorNot = res.getString(COLUMN_PayBillorNot);
		String StartDate = res.getString(COLUMN_StartDate);
		String StartTime = res.getString(COLUMN_StartTime);
		String Names = res.getString(COLUMN_Names);
		String Tel = res.getString(COLUMN_Tel);
		String ServiceExplain = res.getString(COLUMN_ServiceExplain);
		String ChargeID = res.getString(COLUMN_ChargeID);
		String Created_TS = res.getString(COLUMN_Created_TS);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		ArrayList<Activity> Activities  = new ArrayList<Activity>();
		String act = res.getString(COLUMN_Activity);
		if(act!=null&&act.indexOf("\\|")!=-1){
			String[] activities = act.split("\\|");
			for(String activity:activities){
				Activity a = new Activity();
				String[] properties = activity.split(",");
				a.setTime(properties[0]);
				a.setAction(properties[1]);
				Activities.add(a);
			}
		}
		return new ClubOrder(UID,OrderID,UserID,ClubID,ClubName,ClubserveID,ClubserveName,ClubserveLimitTimeID,ClubservePriceID,State,CreateTime,Type,DownPayment,PayBillorNot,StartDate,StartTime,Names,Tel,ServiceExplain,ChargeID,Created_TS,Updated_TS,Activities);
	}

}
