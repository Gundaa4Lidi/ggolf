package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.WalletRecord;

public class WalletRecordRowMapper implements RowMapper<WalletRecord> {
	private static final String COLUMN_RecordID = "RecordID";
	private static final String COLUMN_RecordSn = "RecordSn";
	private static final String COLUMN_FromUID = "FromUID";
	private static final String COLUMN_ToUID = "ToUID";
	private static final String COLUMN_Type = "Type";
	private static final String COLUMN_Money = "Money";
	private static final String COLUMN_PayType = "PayType";
	private static final String COLUMN_Remark = "Remark";
	private static final String COLUMN_PayStatus = "PayStatus";
	private static final String COLUMN_PayTime = "PayTime";
	private static final String COLUMN_FetchStatus = "FetchStatus";
	private static final String COLUMN_FetchTime = "FetchTime";
	private static final String COLUMN_CheckStatus = "CheckStatus";
	private static final String COLUMN_CheckTime = "CheckTime";
	private static final String COLUMN_Created_TS = "Created_TS";
	private static final String COLUMN_Updated_TS = "Updated_TS";
	@Override
	public WalletRecord mapRow(ResultSet res) throws SQLException {
		String RecordID = res.getString(COLUMN_RecordID);
		String RecordSn = res.getString(COLUMN_RecordSn);
		String FromUID = res.getString(COLUMN_FromUID);
		String ToUID = res.getString(COLUMN_ToUID);
		String Type = res.getString(COLUMN_Type);
		String Money = res.getString(COLUMN_Money);
		String PayType = res.getString(COLUMN_PayType);
		String Remark = res.getString(COLUMN_Remark);
		String PayStatus = res.getString(COLUMN_PayStatus);
		String PayTime = res.getString(COLUMN_PayTime);
		String FetchStatus = res.getString(COLUMN_FetchStatus);
		String FetchTime = res.getString(COLUMN_FetchTime);
		String CheckStatus = res.getString(COLUMN_CheckStatus);
		String CheckTime = res.getString(COLUMN_CheckTime);
		String Created_TS = res.getString(COLUMN_Created_TS);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		return new WalletRecord(RecordID, RecordSn, FromUID, ToUID, Type, Money, PayType, Remark, PayStatus, PayTime, FetchStatus, FetchTime, CheckStatus, CheckTime,Created_TS,Updated_TS);
	}

}
