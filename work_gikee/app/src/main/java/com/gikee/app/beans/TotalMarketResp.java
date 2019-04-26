package com.gikee.app.beans;

import java.util.List;

public class TotalMarketResp {

    private String type;

    private List<RemindInfoBean> message;



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<RemindInfoBean> getMessage() {
        return message;
    }

    public void setMessage(List<RemindInfoBean> message) {
        this.message = message;
    }
}
