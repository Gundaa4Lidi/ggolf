package com.galaxy.ggolf.domain;

public class WalletLog {
    private String LogID;
    private String RecordSn;
    private String UserID;
    private String ChangeMoney;
    private String Money;
    private String Remark;
    private String Created_TS;
    private String Display;

    public WalletLog() {
    }

    public WalletLog(String logID, String recordSn, String userID, String changeMoney, String money, String remark, String created_TS, String display) {
        LogID = logID;
        RecordSn = recordSn;
        UserID = userID;
        ChangeMoney = changeMoney;
        Money = money;
        Remark = remark;
        Created_TS = created_TS;
        Display = display;
    }

    public String getLogID() {
        return LogID;
    }

    public void setLogID(String logID) {
        LogID = logID;
    }

    public String getRecordSn() {
        return RecordSn;
    }

    public void setRecordSn(String recordSn) {
        RecordSn = recordSn;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getChangeMoney() {
        return ChangeMoney;
    }

    public void setChangeMoney(String changeMoney) {
        ChangeMoney = changeMoney;
    }

    public String getMoney() {
        return Money;
    }

    public void setMoney(String money) {
        Money = money;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getCreated_TS() {
        return Created_TS;
    }

    public void setCreated_TS(String created_TS) {
        Created_TS = created_TS;
    }

    public String getDisplay() {
        return Display;
    }

    public void setDisplay(String display) {
        Display = display;
    }
}
