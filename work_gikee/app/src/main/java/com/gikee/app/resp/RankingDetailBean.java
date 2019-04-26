package com.gikee.app.resp;

public class RankingDetailBean {
    /**
     * rank :
     * symbol :
     * detail :
     */

    private int ranking;
    private String symbol;
    private String price;
    private String id;
    private String logo;
    private String marketValue;
    private String priceChange;
    private String priceBtc;
    private String marketValue_cny;
    private String price_cny;




    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }



    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }


    public String getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(String priceChange) {
        this.priceChange = priceChange;
    }

    public String getPriceBtc() {
        return priceBtc;
    }

    public void setPriceBtc(String priceBtc) {
        this.priceBtc = priceBtc;
    }

    public String getMarketValue_cny() {
        return marketValue_cny;
    }

    public void setMarketValue_cny(String marketValue_cny) {
        this.marketValue_cny = marketValue_cny;
    }

    public String getPrice_cny() {
        return price_cny;
    }

    public void setPrice_cny(String price_cny) {
        this.price_cny = price_cny;
    }
}
