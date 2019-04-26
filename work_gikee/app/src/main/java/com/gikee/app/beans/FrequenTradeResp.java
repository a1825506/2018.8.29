package com.gikee.app.beans;

import com.gikee.app.resp.PageBean;
import com.gikee.app.resp.ResponseInfo;

import java.util.List;

public class FrequenTradeResp extends ResponseInfo{

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

        private List<FrequenTradeBean> data;

        private PageBean page;

        public List<FrequenTradeBean> getData() {
            return data;
        }

        public void setData(List<FrequenTradeBean> data) {
            this.data = data;
        }

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }
    }


}
