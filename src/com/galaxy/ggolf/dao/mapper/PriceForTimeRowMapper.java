package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.PriceForTime;

public class PriceForTimeRowMapper implements RowMapper<PriceForTime> {

	private static final String COLUMN_ClubservePriceID = "ClubservePriceID";
	private static final String COLUMN_ClubserveID = "ClubserveID";
	private static final String COLUMN_ClubID = "ClubID";
	private static final String COLUMN_Week = "Week";
	private static final String COLUMN_Time = "Time";
	private static final String COLUMN_DownPayment = "DownPayment";
	private static final String COLUMN_OtherPrice = "OtherPrice";
	private static final String COLUMN_Type = "Type";
	private static final String COLUMN_IsPrivilege = "IsPrivilege";
	private static final String COLUMN_IsDeposit = "IsDeposit";
	private static final String COLUMN_DateTime = "DateTime";
	private static final String COLUMN_Created_TS = "Created_TS";
	private static final String COLUMN_Updated_TS = "Updated_TS";
	@Override
	public PriceForTime mapRow(ResultSet res) throws SQLException {
		String ClubservePriceID = res.getString(COLUMN_ClubservePriceID);
		String ClubserveID = res.getString(COLUMN_ClubserveID);
		String ClubID = res.getString(COLUMN_ClubID);
		String Week = res.getString(COLUMN_Week);
		String Time = res.getString(COLUMN_Time);
		String DownPayment = res.getString(COLUMN_DownPayment);
		String OtherPrice = res.getString(COLUMN_OtherPrice);
		String Type = res.getString(COLUMN_Type);
		String IsPrivilege = res.getString(COLUMN_IsPrivilege);
		String IsDeposit = res.getString(COLUMN_IsDeposit);
		String DateTime = res.getString(COLUMN_DateTime);
		String Created_TS = res.getString(COLUMN_Created_TS);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		return new PriceForTime(ClubservePriceID,ClubserveID,ClubID,Week,Time,
				DownPayment,OtherPrice,Type,IsPrivilege,IsDeposit,DateTime,Created_TS,Updated_TS);
	}

}
