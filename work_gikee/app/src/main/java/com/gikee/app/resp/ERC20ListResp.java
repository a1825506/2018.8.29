package com.gikee.app.resp;

import com.gikee.app.beans.ERC20ListBean;

public class ERC20ListResp {

    private String errInfo;

    private ERC20ListBean result;

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public ERC20ListBean getResult() {
        return result;
    }

    public void setResult(ERC20ListBean result) {
        this.result = result;
    }


}
