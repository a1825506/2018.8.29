package com.gikee.app.resp;

import com.gikee.app.beans.EOSTradeDetailBean;

public class EOSTradeDetailResp {

    private String errInfo;

    private EOSTradeDetailBean result;

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public EOSTradeDetailBean getResult() {
        return result;
    }

    public void setResult(EOSTradeDetailBean result) {
        this.result = result;
    }
}
