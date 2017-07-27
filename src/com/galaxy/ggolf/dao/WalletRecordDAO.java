package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.WalletRecordRowMapper;
import com.galaxy.ggolf.domain.WalletRecord;

public class WalletRecordDAO extends GenericDAO<WalletRecord> {

	public WalletRecordDAO() {
		super(new WalletRecordRowMapper());
		// TODO Auto-generated constructor stub
	}

	/**
	 * 创建交易订单
	 * @param wr
	 * @return
	 */
	public boolean create(WalletRecord wr){
		String sql = "insert into walletrecord("
				+ "RecordSn,"
				+ "FromUID,"
				+ "ToUID,"
				+ "Type,"
				+ "Money,"
				+ "PayType,"
				+ "Remark,"
				+ "PayStatus,"
				+ "PayTime,"
				+ "FetchStatus,"
				+ "FetchTime,"
				+ "CheckStatus,"
				+ "Created_TS)values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return super.executeUpdate(sql,wr.getRecordSn(),wr.getFromUID(),wr.getToUID(),
				wr.getType(),wr.getMoney(),wr.getPayType(),wr.getRemark(),
				wr.getPayStatus(),wr.getPayTime(),wr.getFetchStatus(),wr.getFetchTime(),"0",Time());
	}
	
	/**
	 * 查看账单列表
	 * @param sqlString
	 * @param rows
	 * @param pageNum
	 * @return
	 */
	public Collection<WalletRecord> getWalletRecord(String sqlString,String rows,String pageNum){
		String limit = super.limit(pageNum, rows);
		String sql = "select * from walletRecord where RecordSn is not null "+sqlString+" order by Created_TS desc "+limit+"";
		return super.executeQuery(sql);
	}
	
	/**
	 * 查看账单
	 * @param RecordSn
	 * @return
	 */
	public WalletRecord getByRecordSn(String RecordSn){
		String sql = "select * from walletrecord where RecordSn='"+RecordSn+"'";
		Collection<WalletRecord> result = super.executeQuery(sql);
		if(result.size()>0){
			return (WalletRecord) result.toArray()[0];
		}
		return null;
	}
	
	/**
	 * 修改
	 * @param sqlString
	 * @param RecordSn
	 * @return
	 */
	public boolean updateStatus(String sqlString,String RecordSn){
		String sql = "update walletrecord set "+sqlString+" Updated_TS='"+Time()+"' where RecordSn='"+RecordSn+"'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 添加企业付款编号
	 * @param TransferID
	 * @param RecordSn
	 * @return
	 */
	public boolean updateTransferID(String TransferID,String RecordSn){
		String sql = "update walletrecord set TransferID='"+TransferID+"', Updated_TS='"+Time()+"' where RecordSn='"+RecordSn+"' and PayStatus='0'";
		return super.executeUpdate(sql);
	}
}
