package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.WalletLog;

public class WalletLogRowMapper implements RowMapper<WalletLog> {
    private static final String COLUMN_LogID = "LogID";
    private static final String COLUMN_RecordSn = "RecordSn";
    private static final String COLUMN_UserID = "UserID";
    private static final String COLUMN_ChangeMoney = "ChangeMoney";
    private static final String COLUMN_Money = "Money";
    private static final String COLUMN_Remark = "Remark";
    private static final String COLUMN_Created_TS = "Created_TS";
    private static final String COLUMN_Display = "Display";
	@Override
	public WalletLog mapRow(ResultSet res) throws SQLException {
        String LogID = res.getString(COLUMN_LogID);
        String RecordSn = res.getString(COLUMN_RecordSn);
        String UserID = res.getString(COLUMN_UserID);
        String ChangeMoney = res.getString(COLUMN_ChangeMoney);
        String Money = res.getString(COLUMN_Money);
        String Remark = res.getString(COLUMN_Remark);
        String Created_TS = res.getString(COLUMN_Created_TS);
        String Display = res.getString(COLUMN_Display);
		return new WalletLog(LogID, RecordSn, UserID, ChangeMoney, Money, Remark, Created_TS, Display);
	}

}
