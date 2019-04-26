package com.gikee.app.beans;

import java.util.List;

public class PairBean {

    private List<PairItemBean> data;

    private int total_count;

    public List<PairItemBean> getData() {
        return data;
    }

    public void setData(List<PairItemBean> data) {
        this.data = data;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }
}
