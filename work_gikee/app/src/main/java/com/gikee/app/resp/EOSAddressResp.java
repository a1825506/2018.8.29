package com.gikee.app.resp;

import com.gikee.app.beans.EOSAddressBean;

public class EOSAddressResp extends ResponseInfo{

    private String errInfo;

    private EOSAddressBean result;

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public EOSAddressBean getResult() {
        return result;
    }

    public void setResult(EOSAddressBean result) {
        this.result = result;
    }
}
