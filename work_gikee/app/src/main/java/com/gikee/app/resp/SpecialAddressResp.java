package com.gikee.app.resp;


import java.util.List;

public class SpecialAddressResp  extends ResponseInfo{


    /**
     * errInfo :
     * result :
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

        private List<SpecialAddressBean> data;

        private PageBean page;

        public List<SpecialAddressBean> getData() {
            return data;
        }

        public void setData(List<SpecialAddressBean> data) {
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
