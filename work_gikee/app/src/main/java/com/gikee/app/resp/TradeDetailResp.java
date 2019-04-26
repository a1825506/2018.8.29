package com.gikee.app.resp;

import java.util.List;

public class TradeDetailResp extends ResponseInfo{


    private String errInfo;
    private List<TradeDetailBean> result;

    public String getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public List<TradeDetailBean> getResult() {
        return result;
    }

    public void setResult(List<TradeDetailBean> result) {
        this.result = result;
    }
}
