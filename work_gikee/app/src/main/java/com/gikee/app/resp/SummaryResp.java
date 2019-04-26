package com.gikee.app.resp;

import java.util.List;

public class SummaryResp extends ResponseInfo{
    /**
     * errInfo :
     * result : {"data":[{"itemName":"","titile":"","showType":"","pieData":{"percent":"","name":""},"tableData":[{"from":"","fromName":"","to":"","toName":"","value":1,"unit":""}]}]}
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

        private List<SummaryBean> data;

        public List<SummaryBean> getData() {
            return data;
        }

        public void setData(List<SummaryBean> data) {
            this.data = data;
        }
    }



}
