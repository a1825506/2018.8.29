package com.gikee.app.resp;

import com.gikee.app.beans.BTCTradeDetailBean;

public class BTCTradeDetailResp extends ResponseInfo{

    private String errInfo;

    private BTCTradeDetailBean result;

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public BTCTradeDetailBean getResult() {
        return result;
    }

    public void setResult(BTCTradeDetailBean result) {
        this.result = result;
    }
}
