package com.gikee.app.resp;

import java.util.List;

public class RankingDetailResp extends ResponseInfo{

    private String errInfo;

    private ResultBean result;

//    private PageBean page;
//
//    public PageBean getPage() {
//        return page;
//    }
//
//    public void setPage(PageBean page) {
//        this.page = page;
//    }


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

        private List<RankingDetailBean> data;

        public List<RankingDetailBean> getData() {
            return data;
        }

        public void setData(List<RankingDetailBean> data) {
            this.data = data;
        }


    }

}
