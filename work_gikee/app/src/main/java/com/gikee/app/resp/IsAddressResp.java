package com.gikee.app.resp;

import com.gikee.app.beans.IsAddressBean;

public class IsAddressResp extends ResponseInfo{

    private String errInfo;

    private IsAddressBean result;


    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public IsAddressBean getResult() {
        return result;
    }

    public void setResult(IsAddressBean result) {
        this.result = result;
    }



}
