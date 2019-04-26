package com.gikee.app.resp;

import com.gikee.app.beans.MonitorTradeBean;

import java.util.List;

public class MonitorTradeResp {



    private String errInfo;

    private MonitorTradeBean tradeMonitor;


    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getErrInfo() {
        return errInfo;
    }


    public MonitorTradeBean getTradeMonitor() {
        return tradeMonitor;
    }

    public void setTradeMonitor(MonitorTradeBean tradeMonitor) {
        this.tradeMonitor = tradeMonitor;
    }
}
