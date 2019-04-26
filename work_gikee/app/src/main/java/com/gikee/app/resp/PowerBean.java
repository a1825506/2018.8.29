package com.gikee.app.resp;

import com.gikee.app.beans.TotalHashRateBean;

import java.util.List;

public class PowerBean {

    private List<SummaryBean> data;

    private TotalHashRateBean totalHashRate;


    public TotalHashRateBean getTotalHashRate() {
        return totalHashRate;
    }

    public void setTotalHashRate(TotalHashRateBean totalHashRate) {
        this.totalHashRate = totalHashRate;
    }

    public List<SummaryBean> getData() {
        return data;
    }

    public void setData(List<SummaryBean> data) {
        this.data = data;
    }
}
