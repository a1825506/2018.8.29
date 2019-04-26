package com.gikee.app.beans;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class SearchResultBean implements MultiItemEntity {

    private String showType;

    private String exchange;

    private String id;

    private String symbol;

    private String logo;

    private String transaction_pair;//交易对

    private String turnover;//成交额

    private String transaction_amount;

    private String quateChange;

    private String price_pair;

    private String price_usd;

    private String price_cny;

    private String price_btc;

    private String marketValue_usd;

    private String marketValue_cny;

    private String balance;

    private String volume_24H;

    private String volume_24H_CNY;

    private boolean isCollect;


    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTransaction_pair() {
        return transaction_pair;
    }

    public void setTransaction_pair(String transaction_pair) {
        this.transaction_pair = transaction_pair;
    }

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }

    public String getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(String transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public String getQuateChange() {
        return quateChange;
    }

    public void setQuateChange(String quateChange) {
        this.quateChange = quateChange;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice_pair() {
        return price_pair;
    }

    public void setPrice_pair(String price_pair) {
        this.price_pair = price_pair;
    }

    public String getPrice_usd() {
        return price_usd;
    }

    public void setPrice_usd(String price_usd) {
        this.price_usd = price_usd;
    }

    public String getPrice_btc() {
        return price_btc;
    }

    public void setPrice_btc(String price_btc) {
        this.price_btc = price_btc;
    }

    public String getMarketValue_usd() {
        return marketValue_usd;
    }

    public void setMarketValue_usd(String marketValue_usd) {
        this.marketValue_usd = marketValue_usd;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getVolume_24H() {
        return volume_24H;
    }

    public void setVolume_24H(String volume_24H) {
        this.volume_24H = volume_24H;
    }

    public String getVolume_24H_CNY() {
        return volume_24H_CNY;
    }

    public void setVolume_24H_CNY(String volume_24H_CNY) {
        this.volume_24H_CNY = volume_24H_CNY;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getPrice_cny() {
        return price_cny;
    }

    public void setPrice_cny(String price_cny) {
        this.price_cny = price_cny;
    }

    public String getMarketValue_cny() {
        return marketValue_cny;
    }

    public void setMarketValue_cny(String marketValue_cny) {
        this.marketValue_cny = marketValue_cny;
    }
}
