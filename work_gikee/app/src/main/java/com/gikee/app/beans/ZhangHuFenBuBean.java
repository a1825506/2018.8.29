package com.gikee.app.beans;

import java.util.List;

/**
 * Created by THINKPAD on 2018/6/22.
 */

public class ZhangHuFenBuBean {

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

        private long blockHeight;
        private String coinSymbol;
        private String date;
        private List<Pie> pie;

        public void setBlockHeight(long blockHeight) {
            this.blockHeight = blockHeight;
        }

        public long getBlockHeight() {
            return blockHeight;
        }

        public void setCoinSymbol(String coinSymbol) {
            this.coinSymbol = coinSymbol;
        }

        public String getCoinSymbol() {
            return coinSymbol;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDate() {
            return date;
        }

        public void setPie(List<Pie> pie) {
            this.pie = pie;
        }

        public List<Pie> getPie() {
            return pie;
        }

    }

    public class Pie {

        private String name;
        private String ratio;
        private String value;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }

        public String getRatio() {
            return ratio;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }
}
