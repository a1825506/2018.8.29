package com.gikee.app.resp;

import com.gikee.app.beans.RateBean;

import java.util.List;

public class RateBeanResp {

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

        private List<RateBean> data;


        public List<RateBean> getData() {
            return data;
        }

        public void setData(List<RateBean> data) {
            this.data = data;
        }

    }
}
