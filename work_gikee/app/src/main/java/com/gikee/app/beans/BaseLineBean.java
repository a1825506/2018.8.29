package com.gikee.app.beans;

import com.gikee.app.resp.ResponseInfo;

import java.util.List;

public class BaseLineBean extends ResponseInfo{


    /**
     * symbol :
     * logo :
     * currentValue :
     * currentName :
     * lineData : [{"line":{"time":"","value":""}}]
     */
    private String id;
    private String symbol;
    private String logo;
    private List<LineBean> lineData;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


    public List<LineBean> getLineData() {
        return lineData;
    }

    public void setLineData(List<LineBean> lineData) {
        this.lineData = lineData;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
