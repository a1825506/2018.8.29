package com.gikee.app.resp;

import com.gikee.app.beans.HashDetailBean;

public class HashDetailResp extends ResponseInfo{

    private String errInfo;

    private HashDetailBean result;

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public HashDetailBean getResult() {
        return result;
    }

    public void setResult(HashDetailBean result) {
        this.result = result;
    }
}
