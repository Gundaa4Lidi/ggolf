package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.WalletRowMapper;
import com.galaxy.ggolf.domain.Wallet;

public class WalletDAO extends GenericDAO<Wallet> {

	public WalletDAO() {
		super(new WalletRowMapper());
	}

	/**
	 * 创建钱包
	 * @param w
	 * @return
	 */
	public boolean create(Wallet w){
		String sql = "insert into wallet("
				+ "UserID,"
				+ "Salt,"
				+ "PayPassword,"
				+ "Created_TS)values(?,?,?,?)";
		return super.executeUpdate(sql, w.getUserID(),w.getSalt(),w.getPayPassword(),Time());
	}
	
	/**
	 * 查看用户钱包
	 * @param UserID
	 * @param flag 0:可带密码, 否则不带密码
	 * @return
	 */
	public Wallet getByUserID(String UserID,String flag){
		String sql = "select * from wallet where UserID='"+UserID+"'";
		Collection<Wallet> result = super.executeQuery(sql);
		Wallet wallet = new Wallet();
		if(result.size()>0){
			if(flag.equals("0")){
				wallet = (Wallet) result.toArray()[0];
			}else{
				wallet = GetWallet((Wallet) result.toArray()[0]);
			}
			return wallet;
		}
		return null;
	}
	
	/**
	 * 修改支付密码
	 * @param PayPassword
	 * @param UserID
	 * @return
	 */
	public boolean updatePayPWD(String PayPassword,String UserID){
		String sql = "update wallet set PayPassword = '"+PayPassword+"',Updated_TS='"+Time()+"' where UserID='"+UserID+"'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 修改相关资料
	 * @param sqlString
	 * @param UserID
	 * @return
	 */
	public boolean update(String sqlString,String UserID){
		String sql = "update wallet set "+sqlString+" Updated_TS='"+Time()+"' where UserID='"+UserID+"'";
		return super.executeUpdate(sql);
	}
	
	public Wallet GetWallet(Wallet w){
		w = new Wallet(w.getUserID(), w.getMoney(), w.getSalt(), w.getName(), w.getIDCard(), w.getTx_wechat(), w.getTx_alipay(), w.getCreated_TS(), w.getUpdated_TS());
		return w;
	}
}
