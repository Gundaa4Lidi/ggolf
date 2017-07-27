package com.galaxy.ggolf.domain;

public class WalletRecord {
    private String RecordID;
    private String RecordSn;
    private String FromUID;
    private String ToUID;
    private String Type;
    private String Money;
    private String PayType;
    private String Remark;
    private String PayStatus;
    private String PayTime;
    private String FetchStatus;
    private String FetchTime;
    private String CheckStatus;
    private String CheckTime;
    private String TransferID;
    private String Created_TS;
    private String Updated_TS;

    public WalletRecord() {
    }

    public WalletRecord(String recordID, String recordSn, String fromUID,
                        String toUID, String type, String money, String payType, String remark,
                        String payStatus, String payTime, String fetchStatus, String fetchTime,
                        String checkStatus, String checkTime, String transferID, String created_TS, String updated_TS) {
        RecordID = recordID;
        RecordSn = recordSn;
        FromUID = fromUID;
        ToUID = toUID;
        Type = type;
        Money = money;
        PayType = payType;
        Remark = remark;
        PayStatus = payStatus;
        PayTime = payTime;
        FetchStatus = fetchStatus;
        FetchTime = fetchTime;
        CheckStatus = checkStatus;
        CheckTime = checkTime;
        TransferID = transferID;
        Created_TS = created_TS;
        Updated_TS = updated_TS;
    }

    public String getRecordID() {
        return RecordID;
    }

    public void setRecordID(String recordID) {
        RecordID = recordID;
    }

    public String getRecordSn() {
        return RecordSn;
    }

    public void setRecordSn(String recordSn) {
        RecordSn = recordSn;
    }

    public String getFromUID() {
        return FromUID;
    }

    public void setFromUID(String fromUID) {
        FromUID = fromUID;
    }

    public String getToUID() {
        return ToUID;
    }

    public void setToUID(String toUID) {
        ToUID = toUID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getMoney() {
        return Money;
    }

    public void setMoney(String money) {
        Money = money;
    }

    public String getPayType() {
        return PayType;
    }

    public void setPayType(String payType) {
        PayType = payType;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getPayStatus() {
        return PayStatus;
    }

    public void setPayStatus(String payStatus) {
        PayStatus = payStatus;
    }

    public String getPayTime() {
        return PayTime;
    }

    public void setPayTime(String payTime) {
        PayTime = payTime;
    }

    public String getFetchStatus() {
        return FetchStatus;
    }

    public void setFetchStatus(String fetchStatus) {
        FetchStatus = fetchStatus;
    }

    public String getFetchTime() {
        return FetchTime;
    }

    public void setFetchTime(String fetchTime) {
        FetchTime = fetchTime;
    }

    public String getCheckStatus() {
        return CheckStatus;
    }

    public void setCheckStatus(String checkStatus) {
        CheckStatus = checkStatus;
    }

    public String getCheckTime() {
        return CheckTime;
    }

    public void setCheckTime(String checkTime) {
        CheckTime = checkTime;
    }

	public String getTransferID() {
		return TransferID;
	}

	public void setTransferID(String transferID) {
		TransferID = transferID;
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
