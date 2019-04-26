package com.gikee.app.resp;

import com.gikee.app.beans.ProjectInfoBean;


public class ProjectInfoResp extends ResponseInfo{


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

        private ProjectInfoBean data;

        public ProjectInfoBean getData() {
            return data;
        }

        public void setData(ProjectInfoBean data) {
            this.data = data;
        }
    }
}
