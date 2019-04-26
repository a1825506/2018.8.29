package com.gikee.app.resp;

public class PriceChangeBean {

    //price":"0.009301","increase":"-7.33","symbol":"BKBT"

    private String price;

    private String marketValue;

    private String symbol;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }



    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }
}
