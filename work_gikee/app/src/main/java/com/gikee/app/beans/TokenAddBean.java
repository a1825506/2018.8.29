package com.gikee.app.beans;

public class TokenAddBean {


    /**
     * symbol :
     * cnName :
     * price : 1
     * newAccounts : 1
     * activeAccounts : 1
     * tradeCount : 1
     * logo :
     */
    private String id;
    private String symbol;
    private String price;
    private String price_cny;
    private String quateChange;
    private String newAccounts;
    private String price_btc;
    private String activeAccounts;
    private String tradeCount;
    private String logo;
    private String symbol_id;
    private String marketValue;
    private String marketValue_cny;
    private String block;


    private boolean showReal_timeData=false;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNewAccounts() {
        return newAccounts;
    }

    public void setNewAccounts(String newAccounts) {
        this.newAccounts = newAccounts;
    }

    public String getActiveAccounts() {
        return activeAccounts;
    }

    public void setActiveAccounts(String activeAccounts) {
        this.activeAccounts = activeAccounts;
    }

    public String getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(String tradeCount) {
        this.tradeCount = tradeCount;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


    public String getSymbol_id() {
        return symbol_id;
    }

    public void setSymbol_id(String symbol_id) {
        this.symbol_id = symbol_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice_cny() {
        return price_cny;
    }

    public void setPrice_cny(String price_cny) {
        this.price_cny = price_cny;
    }

    public boolean isShowReal_timeData() {
        return showReal_timeData;
    }

    public void setShowReal_timeData(boolean showReal_timeData) {
        this.showReal_timeData = showReal_timeData;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    public String getMarketValue_cny() {
        return marketValue_cny;
    }

    public void setMarketValue_cny(String marketValue_cny) {
        this.marketValue_cny = marketValue_cny;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getQuateChange() {
        return quateChange;
    }

    public void setQuateChange(String quateChange) {
        this.quateChange = quateChange;
    }

    public String getPrice_btc() {
        return price_btc;
    }

    public void setPrice_btc(String price_btc) {
        this.price_btc = price_btc;
    }
}
