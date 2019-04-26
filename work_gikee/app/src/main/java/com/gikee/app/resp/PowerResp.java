package com.gikee.app.resp;

import java.util.List;

public class PowerResp {


    private String errInfo;

    private PowerBean result;


    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public PowerBean getResult() {
        return result;
    }

    public void setResult(PowerBean result) {
        this.result = result;
    }




}
