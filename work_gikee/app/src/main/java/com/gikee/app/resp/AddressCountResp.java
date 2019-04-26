package com.gikee.app.resp;

import com.gikee.app.beans.AddressCountBean;

import java.util.List;

public class AddressCountResp {

    private String errInfo;

    private List<AddressCountBean> data;


    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getErrInfo() {
        return errInfo;
    }


    public List<AddressCountBean> getData() {
        return data;
    }

    public void setData(List<AddressCountBean> data) {
        this.data = data;
    }
}
