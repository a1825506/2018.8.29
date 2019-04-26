package com.gikee.app.beans;

import com.gikee.app.resp.ResponseInfo;

import java.util.List;

/**
 * Created by THINKPAD on 2018/6/22.
 */

public class TransationFenBuBean extends ResponseInfo{

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
        private String date;
        private String blockHeight;
        private List<Pies> pies;

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

        public String getBlockHeight() {
            return blockHeight;
        }

        public void setBlockHeight(String blockHeight) {
            this.blockHeight = blockHeight;
        }

        public void setPies(List<Pies> pies) {
            this.pies = pies;
        }

        public List<Pies> getPies() {
            return pies;
        }

    }

    public class Pies {

        private String name;
        private String value;
        private double percentage;
        private String rise;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setPercentage(double percentage) {
            this.percentage = percentage;
        }

        public double getPercentage() {
            return percentage;
        }

        public void setRise(String rise) {
            this.rise = rise;
        }

        public String getRise() {
            return rise;
        }

    }
}
