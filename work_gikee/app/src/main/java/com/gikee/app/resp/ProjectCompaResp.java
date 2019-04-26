package com.gikee.app.resp;

import com.gikee.app.beans.ProjectCompaBean;

import java.util.List;

public class ProjectCompaResp {

    private String errInfo;

    private ResultBean result;



    public String getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public class ResultBean {

        private List<ProjectCompaBean> data;


        public List<ProjectCompaBean> getData() {
            return data;
        }

        public void setData(List<ProjectCompaBean> data) {
            this.data = data;
        }
    }



}
