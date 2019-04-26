package com.gikee.app.beans;

import com.gikee.app.resp.SummaryBean;

import java.util.List;

public class ProjectCompaBean {
    private String id;
    private String logo;
    private String symbol;
    private List<SummaryBean> coinInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<SummaryBean> getCoinInfo() {
        return coinInfo;
    }

    public void setCoinInfo(List<SummaryBean> coinInfo) {
        this.coinInfo = coinInfo;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
