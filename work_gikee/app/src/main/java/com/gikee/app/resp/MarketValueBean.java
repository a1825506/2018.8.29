package com.gikee.app.resp;

public class MarketValueBean {
    /**
     * time :
     * marketValue : 1
     * marketRatio : 1
     * totalVolume : 1
     * turnRatio : 1
     */

    private String time;
    private String marketValue;
    private String marketRatio;
    private String totalVolume;
    private String turnRatio;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    public String getMarketRatio() {
        return marketRatio;
    }

    public void setMarketRatio(String marketRatio) {
        this.marketRatio = marketRatio;
    }

    public String getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(String totalVolume) {
        this.totalVolume = totalVolume;
    }

    public String getTurnRatio() {
        return turnRatio;
    }

    public void setTurnRatio(String turnRatio) {
        this.turnRatio = turnRatio;
    }
}
