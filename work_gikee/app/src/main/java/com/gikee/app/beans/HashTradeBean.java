package com.gikee.app.beans;

public class HashTradeBean {


    /**
     * tradehash :
     * amount :
     * from :
     * to :
     * time :
     */

    private String tradehash;
    private String amount;
    private String from;
    private String fromTitle;
    private String to;
    private String toTitle;
    private String tradetime;
    private String status;


    private String sort;
    private String type;
    private String logo;
    private String symbol;
    private String direct;

    public String getTradehash() {
        return tradehash;
    }

    public void setTradehash(String tradehash) {
        this.tradehash = tradehash;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTime() {
        return tradetime;
    }

    public void setTime(String time) {
        this.tradetime = time;
    }

    public String getFrom_title() {
        return fromTitle;
    }

    public void setFrom_title(String from_title) {
        this.fromTitle = from_title;
    }


    public String getToTitle() {
        return toTitle;
    }

    public void setToTitle(String toTitle) {
        this.toTitle = toTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }


    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
