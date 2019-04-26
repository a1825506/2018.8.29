package com.gikee.app.resp;

import com.gikee.app.beans.TokenAddBean;

import java.util.List;

public class TokensAddedResp extends ResponseInfo {
    /**
     * errInfo :
     * result : {"data":[{"symbol":"","cnName":"","price":1,"newAccounts":1,"activeAccounts":1,"tradeCount":1,"logo":""}]}
     */

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

        private List<TokenAddBean> data;


        public List<TokenAddBean> getData() {
            return data;
        }

        public void setData(List<TokenAddBean> data) {
            this.data = data;
        }

    }





}

