package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.Wallet;

public class WalletRowMapper implements RowMapper<Wallet> {
	private static final String COLUMN_UserID = "UserID";
	private static final String COLUMN_Money = "Money";
	private static final String COLUMN_Salt = "Salt";
	private static final String COLUMN_PayPassword = "PayPassword";
	private static final String COLUMN_Name = "Name";
	private static final String COLUMN_IDCard = "IDCard";
	private static final String COLUMN_Tx_wechat = "Tx_wechat";
	private static final String COLUMN_Tx_alipay = "Tx_alipay";
	private static final String COLUMN_Created_TS = "Created_TS";
	private static final String COLUMN_Updated_TS = "Updated_TS";


	@Override
	public Wallet mapRow(ResultSet res) throws SQLException {
		String UserID = res.getString(COLUMN_UserID);
		String Money = res.getString(COLUMN_Money);
		String Salt = res.getString(COLUMN_Salt);
		String PayPassword = res.getString(COLUMN_PayPassword);
		String Name = res.getString(COLUMN_Name);
		String IDCard = res.getString(COLUMN_IDCard);
		String Tx_wechat = res.getString(COLUMN_Tx_wechat);
		String Tx_alipay = res.getString(COLUMN_Tx_alipay);
		String Created_TS = res.getString(COLUMN_Created_TS);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		return new Wallet(UserID, Money, Salt, PayPassword, Name, IDCard, Tx_wechat, Tx_alipay, Created_TS, Updated_TS);
	}

}
