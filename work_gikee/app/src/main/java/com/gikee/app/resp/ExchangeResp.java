package com.gikee.app.resp;

import com.gikee.app.beans.ExchangeBean;

public class ExchangeResp {

    private String errInfo;

    private ExchangeBean result;

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public ExchangeBean getResult() {
        return result;
    }

    public void setResult(ExchangeBean result) {
        this.result = result;
    }
}
