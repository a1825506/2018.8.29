package com.gikee.app.resp;

import com.gikee.app.beans.CurrentValueBean;
import com.gikee.app.beans.ValueBean;

import java.util.List;

public class ValueResp {

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

        private List<ValueBean> data;

        private CurrentValueBean currentValue;

        public List<ValueBean> getData() {
            return data;
        }

        public void setData(List<ValueBean> data) {
            this.data = data;
        }

        public CurrentValueBean getCurrentValue() {
            return currentValue;
        }

        public void setCurrentValue(CurrentValueBean currentValue) {
            this.currentValue = currentValue;
        }
    }

}
