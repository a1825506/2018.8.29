package com.gikee.app.beans;

import java.util.List;

/**
 * Created by THINKPAD on 2018/6/22.
 */

public class ZhangHuBean {

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
        private String from;
        private String to;
        private String unit;
        private List<Long> ordinate;
        private List<Datas> datas;

        public void setCoinSymbol(String coinSymbol) {
            this.coinSymbol = coinSymbol;
        }

        public String getCoinSymbol() {
            return coinSymbol;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getFrom() {
            return from;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getTo() {
            return to;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getUnit() {
            return unit;
        }

        public void setOrdinate(List<Long> ordinate) {
            this.ordinate = ordinate;
        }

        public List<Long> getOrdinate() {
            return ordinate;
        }

        public void setDatas(List<Datas> datas) {
            this.datas = datas;
        }

        public List<Datas> getDatas() {
            return datas;
        }

    }

    public class Datas {

        private int blockHeight;
        private String range;
        private int value;

        public void setBlockHeight(int blockHeight) {
            this.blockHeight = blockHeight;
        }

        public int getBlockHeight() {
            return blockHeight;
        }

        public void setRange(String range) {
            this.range = range;
        }

        public String getRange() {
            return range;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }
}
