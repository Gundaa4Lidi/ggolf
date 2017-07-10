package com.galaxy.ggolf.domain;

/**
 * Created by Administrator on 2017-07-06.
 */
public class Wallet {
    private String UserID;
    private String Money;
    private String Salt;
    private String PayPassword;
    private String Name;
    private String IDCard;
    private String Tx_wechat;
    private String Tx_alipay;
    private String Created_TS;
    private String Updated_TS;
    
    
    public Wallet() {
	}
    
	public Wallet(String userID, String money, String salt, String name, String iDCard, String tx_wechat,
			String tx_alipay, String created_TS, String updated_TS) {
		UserID = userID;
		Money = money;
		Salt = salt;
		Name = name;
		IDCard = iDCard;
		Tx_wechat = tx_wechat;
		Tx_alipay = tx_alipay;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
	}

	public Wallet(String userID, String money, String salt, String payPassword, String name, String iDCard,
			String tx_wechat, String tx_alipay, String created_TS, String updated_TS) {
		UserID = userID;
		Money = money;
		Salt = salt;
		PayPassword = payPassword;
		Name = name;
		IDCard = iDCard;
		Tx_wechat = tx_wechat;
		Tx_alipay = tx_alipay;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
	}
	
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getMoney() {
		return Money;
	}
	public void setMoney(String money) {
		Money = money;
	}
	public String getSalt() {
		return Salt;
	}
	public void setSalt(String salt) {
		Salt = salt;
	}
	public String getPayPassword() {
		return PayPassword;
	}
	public void setPayPassword(String payPassword) {
		PayPassword = payPassword;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getIDCard() {
		return IDCard;
	}
	public void setIDCard(String iDCard) {
		IDCard = iDCard;
	}
	public String getTx_wechat() {
		return Tx_wechat;
	}
	public void setTx_wechat(String tx_wechat) {
		Tx_wechat = tx_wechat;
	}
	public String getTx_alipay() {
		return Tx_alipay;
	}
	public void setTx_alipay(String tx_alipay) {
		Tx_alipay = tx_alipay;
	}
	public String getCreated_TS() {
		return Created_TS;
	}
	public void setCreated_TS(String created_TS) {
		Created_TS = created_TS;
	}
	public String getUpdated_TS() {
		return Updated_TS;
	}
	public void setUpdated_TS(String updated_TS) {
		Updated_TS = updated_TS;
	}

    





}
