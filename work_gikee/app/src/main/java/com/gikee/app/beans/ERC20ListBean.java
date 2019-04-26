package com.gikee.app.beans;

import java.util.List;

public class ERC20ListBean {


    private String total_count;


    private List<ERC20Bean> data;


    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public List<ERC20Bean> getData() {
        return data;
    }

    public void setData(List<ERC20Bean> data) {
        this.data = data;
    }
}
