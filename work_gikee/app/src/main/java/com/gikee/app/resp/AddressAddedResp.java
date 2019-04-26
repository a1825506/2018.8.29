package com.gikee.app.resp;

import com.gikee.app.beans.AddressAddedBean;

import java.util.List;

public class AddressAddedResp {

    private String errInfo;

     private List<AddressAddedBean> data;


    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getErrInfo() {
        return errInfo;
    }


    public List<AddressAddedBean> getData() {
        return data;
    }

    public void setData(List<AddressAddedBean> data) {
        this.data = data;
    }
}
