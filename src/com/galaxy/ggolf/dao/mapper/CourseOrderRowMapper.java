package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.galaxy.ggolf.domain.CourseOrder;
import com.galaxy.ggolf.dto.Activity;

public class CourseOrderRowMapper implements RowMapper<CourseOrder> {

	private static final String COLUMN_CourseOrderID = "CourseOrderID";
	private static final String COLUMN_CoachID = "CoachID";
	private static final String COLUMN_CoachName = "CoachName";
	private static final String COLUMN_CourseID = "CourseID";
	private static final String COLUMN_CourseTitle = "CourseTitle";
	private static final String COLUMN_UserID = "UserID";
	private static final String COLUMN_UserName = "UserName";
	private static final String COLUMN_State = "State";
	private static final String COLUMN_Type = "Type";
	private static final String COLUMN_TeachingMethod = "TeachingMethod";
	private static final String COLUMN_ClassHour = "ClassHour";
	private static final String COLUMN_DownPayment = "DownPayment";
	private static final String COLUMN_StartDateTime = "StartDateTime";
	private static final String COLUMN_Tel = "Tel";
	private static final String COLUMN_IsBatch = "IsBatch";
	private static final String COLUMN_IsTaught = "IsTaught";
	private static final String COLUMN_IsRead = "IsRead";
	private static final String COLUMN_Marks = "Marks";
	private static final String COLUMN_ChargeID = "ChargeID";
	private static final String COLUMN_Refund = "Refund";
	private static final String COLUMN_Description = "Description";
	private static final String COLUMN_ServiceExplain = "ServiceExplain";
	private static final String COLUMN_Created_TS = "Created_TS";
	private static final String COLUMN_Updated_TS = "Updated_TS";
	private static final String COLUMN_Activity = "Activity";
	@Override
	public CourseOrder mapRow(ResultSet res) throws SQLException {

		String CourseOrderID = res.getString(COLUMN_CourseOrderID);
		String CoachID = res.getString(COLUMN_CoachID);
		String CoachName = res.getString(COLUMN_CoachName);
		String CourseID = res.getString(COLUMN_CourseID);
		String CourseTitle = res.getString(COLUMN_CourseTitle);
		String UserID = res.getString(COLUMN_UserID);
		String UserName = res.getString(COLUMN_UserName);
		String State = res.getString(COLUMN_State);
		String Type = res.getString(COLUMN_Type);
		String TeachingMethod = res.getString(COLUMN_TeachingMethod);
		String ClassHour = res.getString(COLUMN_ClassHour);
		String DownPayment = res.getString(COLUMN_DownPayment);
		String StartDateTime = res.getString(COLUMN_StartDateTime);
		String Tel = res.getString(COLUMN_Tel);
		String IsBatch = res.getString(COLUMN_IsBatch);
		String IsTaught = res.getString(COLUMN_IsTaught);
		String IsRead = res.getString(COLUMN_IsRead);
		String Marks = res.getString(COLUMN_Marks);
		String ChargeID = res.getString(COLUMN_ChargeID);
		String Refund = res.getString(COLUMN_Refund);
		String Description = res.getString(COLUMN_Description);
		String ServiceExplain = res.getString(COLUMN_ServiceExplain);
		String Created_TS = res.getString(COLUMN_Created_TS);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		String act = res.getString(COLUMN_Activity);
		ArrayList<Activity> Activities  = new ArrayList<Activity>();
		String[] activities = act.split("\\|");
		for(String activity:activities){
			Activity a = new Activity();
			String[] properties = activity.split(",");
			a.setTime(properties[0]);
			a.setAction(properties[1]);
			Activities.add(a);
		}
		return new CourseOrder(CourseOrderID, CoachID, CoachName, CourseID, CourseTitle, UserID,
				UserName, State, Type, TeachingMethod, ClassHour, DownPayment, StartDateTime,
				Tel, IsBatch, IsTaught, IsRead, Marks, ChargeID,Refund, Description, ServiceExplain,
				Created_TS, Updated_TS, Activities);
	}

}
