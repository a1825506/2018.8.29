package com.gikee.app.resp;

import com.gikee.app.beans.BTCAddressBean;

public class BTCAddressReap extends ResponseInfo{

    private String errInfo;

    private BTCAddressBean result;

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public BTCAddressBean getResult() {
        return result;
    }

    public void setResult(BTCAddressBean result) {
        this.result = result;
    }
}
