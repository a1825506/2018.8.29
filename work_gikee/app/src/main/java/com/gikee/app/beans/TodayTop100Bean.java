package com.gikee.app.beans;

import java.util.List;

/**
 * Created by THINKPAD on 2018/7/24.
 */

public class TodayTop100Bean {
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
        private Datas datas;

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

        public void setDatas(Datas datas) {
            this.datas = datas;
        }

        public Datas getDatas() {
            return datas;
        }

    }

    public class Datas {

        private List<ChartData> chartData;
        private List<Details> Details;

        public void setChartData(List<ChartData> chartData) {
            this.chartData = chartData;
        }

        public List<ChartData> getChartData() {
            return chartData;
        }

        public void setDetails(List<Details> Details) {
            this.Details = Details;
        }

        public List<Details> getDetails() {
            return Details;
        }

    }

    public class Details {

        private String address;
        private int assets;
        private int change;
        private double rario;

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress() {
            return address;
        }

        public void setAssets(int assets) {
            this.assets = assets;
        }

        public int getAssets() {
            return assets;
        }

        public void setChange(int change) {
            this.change = change;
        }

        public int getChange() {
            return change;
        }

        public void setRario(double rario) {
            this.rario = rario;
        }

        public double getRario() {
            return rario;
        }

    }

    public class ChartData {

        private String range;
        private long assets;
        private String blockHeight;

        public void setRange(String range) {
            this.range = range;
        }

        public String getRange() {
            return range;
        }

        public void setAssets(long assets) {
            this.assets = assets;
        }

        public long getAssets() {
            return assets;
        }

        public String getBlockHeight() {
            return blockHeight;
        }

        public void setBlockHeight(String blockHeight) {
            this.blockHeight = blockHeight;
        }
    }
}
