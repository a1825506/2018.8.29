package com.gikee.app.resp;

import java.util.List;

public class LookAroundResp extends ResponseInfo{

    private String errInfo;

    private ResultBean result;


    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }


    public class ResultBean{

        private List<LookAroundBean> data;

        public List<LookAroundBean> getData() {
            return data;
        }

        public void setData(List<LookAroundBean> data) {
            this.data = data;
        }
    }


}
