package com.gikee.app.resp;

import com.gikee.app.beans.BTCAddressTradeBean;

import java.util.List;

public class BTCTradeListResp {


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

        private List<BTCAddressTradeBean> hashDetail;


        public List<BTCAddressTradeBean> getHashDetail() {
            return hashDetail;
        }

        public void setHashDetail(List<BTCAddressTradeBean> hashDetail) {

            this.hashDetail = hashDetail;
        }
    }
}
