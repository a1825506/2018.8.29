package com.gikee.app.beans;


import com.gikee.app.resp.ResponseInfo;

import java.util.List;

public class SearchResp extends ResponseInfo{

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

        private List<SearchResultBean> data;

        public List<SearchResultBean> getData() {
            return data;
        }

        public void setData(List<SearchResultBean> data) {
            this.data = data;
        }
    }







}
