package com.gikee.app.beans;

import java.util.List;

/**
 * Created by THINKPAD on 2018/6/19.
 */

public class TodayAddBean {

    private String errInfo;
    private Result result;

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public class Result {

        private String coinSymbol;
        private int blockHeight;
        private List<Datas> datas;

        public void setCoinSymbol(String coinSymbol) {
            this.coinSymbol = coinSymbol;
        }

        public String getCoinSymbol() {
            return coinSymbol;
        }

        public void setBlockHeight(int blockHeight) {
            this.blockHeight = blockHeight;
        }

        public int getBlockHeight() {
            return blockHeight;
        }

        public void setDatas(List<Datas> datas) {
            this.datas = datas;
        }

        public List<Datas> getDatas() {
            return datas;
        }

    }

    public class Datas {

        private String date;
        private List<Values> values;

        public void setDate(String date) {
            this.date = date;
        }

        public String getDate() {
            return date;
        }

        public void setValues(List<Values> values) {
            this.values = values;
        }

        public List<Values> getValues() {
            return values;
        }

    }

    public class Values {

        private int value;
        private String ratio;

        public void setValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }

        public String getRatio() {
            return ratio;
        }

    }
}
