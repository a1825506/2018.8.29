package com.gikee.app.resp;



public class VersionResp extends ResponseInfo{

    private String errInfo;
    private VersionBean result;

    public String getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public VersionBean getResult() {
        return result;
    }

    public void setResult(VersionBean result) {
        this.result = result;
    }


}
