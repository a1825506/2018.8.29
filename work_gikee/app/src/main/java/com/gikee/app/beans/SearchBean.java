package com.gikee.app.beans;

import com.gikee.app.resp.ResponseInfo;

import java.util.List;

/**
 * Created by THINKPAD on 2018/7/24.
 */

public class SearchBean extends ResponseInfo{

    private List<String> date;

    private String coinname;
    private String logo;
    private String id;
    private String type;
    private String price;
    private String quatechange;
    private boolean isCollect;

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
    }

    public void setIsCollect(boolean isCollect) {
        this.isCollect = isCollect;
    }

    public boolean getIsCollect() {
        return isCollect;
    }


    public String getCoinname() {
        return coinname;
    }

    public void setCoinname(String coinname) {
        this.coinname = coinname;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuatechange() {
        return quatechange;
    }

    public void setQuatechange(String quatechange) {
        this.quatechange = quatechange;
    }
}
