package com.gikee.app.resp;

import com.gikee.app.beans.HashTradeBean;

import java.util.List;

public class HashTradeResp {




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

        private List<HashTradeBean> hashDetail;


        public List<HashTradeBean> getHashDetail() {
            return hashDetail;
        }

        public void setHashDetail(List<HashTradeBean> hashDetail) {
            this.hashDetail = hashDetail;
        }
    }
}
