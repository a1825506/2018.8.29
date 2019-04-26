package com.gikee.app.resp;

import com.gikee.app.beans.BaseLineBean;

import java.util.List;

public class BaseLineResp {

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

        private List<BaseLineBean> data;

        public List<BaseLineBean> getData() {
            return data;
        }

        public void setData(List<BaseLineBean> data) {
            this.data = data;
        }

    }
}
