package com.gikee.app.resp;

import com.gikee.app.beans.AddressDetailBean;

public class AddressDetailResp extends ResponseInfo{

    private String errInfo;

    private AddressDetailBean result;

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public AddressDetailBean getResult() {
        return result;
    }

    public void setResult(AddressDetailBean result) {
        this.result = result;
    }

}
