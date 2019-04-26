package com.gikee.app.resp;

import com.gikee.app.beans.TokenTypeBean;

import java.util.List;

public class TokenTypeResp extends ResponseInfo{


    private String errInfo;
    private List<TokenTypeBean> result;

    public String getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public List<TokenTypeBean> getResult() {
        return result;
    }

    public void setResult(List<TokenTypeBean> result) {
        this.result = result;
    }
}
