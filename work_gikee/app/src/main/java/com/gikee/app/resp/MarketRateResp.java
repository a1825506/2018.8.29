package com.gikee.app.resp;

import com.gikee.app.beans.MarketRateBean;

import java.util.List;

public class MarketRateResp extends ResponseInfo{

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

        private List<MarketRateBean> data;

        public List<MarketRateBean> getData() {
            return data;
        }

        public void setData(List<MarketRateBean> data) {
            this.data = data;
        }
    }
}
