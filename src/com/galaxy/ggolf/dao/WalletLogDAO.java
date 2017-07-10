package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.WalletLogRowMapper;
import com.galaxy.ggolf.domain.WalletLog;

public class WalletLogDAO extends GenericDAO<WalletLog> {

	public WalletLogDAO() {
		super(new WalletLogRowMapper());
	}

	/**
	 * 生成钱包日志
	 * @param wl
	 * @return
	 */
	public boolean create(WalletLog wl){
		String sql = "insert into walletlog("
				+ "RecordSn,"
				+ "UserID,"
				+ "ChangeMoney,"
				+ "Money,"
				+ "Remark,"
				+ "Created_TS)values(?,?,?,?,?,?)";
		return super.executeUpdate(sql,wl.getRecordSn(),wl.getUserID(),
				wl.getChangeMoney(),wl.getMoney(),wl.getRemark(),Time());
	}
	
	/**
	 * 查看用户的流水账
	 * @param UserID
	 * @return
	 */
	public Collection<WalletLog> getByUserID(String UserID){
		String sql = "select * from walletlog where RecordSn is not null and UserID='"+UserID+"'";
		return super.executeQuery(sql);
	}
	
	/**
	 * 查看对应账单日志
	 * @param LogID
	 * @return
	 */
	public WalletLog getByLogID(String LogID){
		String sql = "select * from walletLog where RecordSn is not null and LogID='"+LogID+"'";
		Collection<WalletLog> result = super.executeQuery(sql);
		if(result.size()>0){
			return (WalletLog) result.toArray()[0];
		}
		return null;
	}
}
