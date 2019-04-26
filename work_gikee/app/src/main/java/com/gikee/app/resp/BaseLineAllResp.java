package com.gikee.app.resp;

import com.gikee.app.beans.BaseLineAllBean;

public class BaseLineAllResp {

    private String errInfo;

    private BaseLineAllBean result;


    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public BaseLineAllBean getResult() {
        return result;
    }

    public void setResult(BaseLineAllBean result) {
        this.result = result;
    }



}
