package com.gikee.app.beans;

import com.gikee.app.resp.PieDataBean;

import java.util.List;

public class BaseLineAllBean {

    private List<PieDataBean> pieData;


    private List<BaseLineLineBean> lineLineBeans;

    public List<PieDataBean> getPieData() {
        return pieData;
    }

    public void setPieData(List<PieDataBean> pieData) {
        this.pieData = pieData;
    }

    public List<BaseLineLineBean> getLineLineBeans() {
        return lineLineBeans;
    }

    public void setLineLineBeans(List<BaseLineLineBean> lineLineBeans) {
        this.lineLineBeans = lineLineBeans;
    }
}
