package com.gikee.app.beans;

import com.gikee.app.resp.ResponseInfo;

public class AddressJSBean extends ResponseInfo {

    private String address;

    private String token;

    private String startDate;

    private String endDate;

    private String collectLen;

    private String collectName;

    private String language;

    private boolean collectFlag;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCollectLen() {
        return collectLen;
    }

    public void setCollectLen(String collectLen) {
        this.collectLen = collectLen;
    }

    public String getCollectName() {
        return collectName;
    }

    public void setCollectName(String collectName) {
        this.collectName = collectName;
    }

    public boolean getCollectFlag() {
        return collectFlag;
    }

    public void setCollectFlag(boolean collectFlag) {
        this.collectFlag = collectFlag;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
