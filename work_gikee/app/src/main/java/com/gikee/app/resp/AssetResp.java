package com.gikee.app.resp;

import com.gikee.app.beans.AssetBean;
import com.gikee.app.beans.AssetLineBean;

import java.util.List;

public class AssetResp {

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

        private List<AssetLineBean> linedata;

        private List<AssetBean> assetdata;

        private int count_sum;

        private String value_sum;


        public List<AssetBean> getAssetdata() {
            return assetdata;
        }

        public void setAssetdata(List<AssetBean> assetdata) {
            this.assetdata = assetdata;
        }

        public List<AssetLineBean> getLinedata() {
            return linedata;
        }

        public void setLinedata(List<AssetLineBean> linedata) {
            this.linedata = linedata;
        }



        public String getValue_sum() {
            return value_sum;
        }

        public void setValue_sum(String value_sum) {
            this.value_sum = value_sum;
        }

        public int getCount_sum() {
            return count_sum;
        }

        public void setCount_sum(int count_sum) {
            this.count_sum = count_sum;
        }
    }
}
