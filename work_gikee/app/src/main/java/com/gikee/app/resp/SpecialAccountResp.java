package com.gikee.app.resp;


import com.gikee.app.beans.SpecialAccountBean;

import java.util.List;

public class SpecialAccountResp {

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

        private List<SpecialAccountBean> data;


        public List<SpecialAccountBean> getData() {
            return data;
        }

        public void setData(List<SpecialAccountBean> data) {
            this.data = data;
        }

    }


}
