package com.gikee.app.beans;

public class RemindInfoBean {

    private String title;
    private String title_en;

    private String context;
    private String context_en;
    private boolean isHot;

    private String addr_from;

    private String amount;

    private String symbol;

    private String time;

    private String addr_to;

    private String tx_id;

    private String type;

    private String id;

    private String yesterdayValue;

    private String todayValue;

    private String quatechange;

    public String getAddr_from() {
        return addr_from;
    }

    public void setAddr_from(String addr_from) {
        this.addr_from = addr_from;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTime() {
        return   time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddr_to() {
        return addr_to;
    }

    public void setAddr_to(String addr_to) {
        this.addr_to = addr_to;
    }

    public String getTx_id() {
        return tx_id;
    }

    public void setTx_id(String tx_id) {
        this.tx_id = tx_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYesterdayValue() {
        return yesterdayValue;
    }

    public void setYesterdayValue(String yesterdayValue) {
        this.yesterdayValue = yesterdayValue;
    }

    public String getTodayValue() {
        return todayValue;
    }

    public void setTodayValue(String todayValue) {
        this.todayValue = todayValue;
    }

    public String getQuatechange() {
        return quatechange;
    }

    public void setQuatechange(String quatechange) {
        this.quatechange = quatechange;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getContext_en() {
        return context_en;
    }

    public void setContext_en(String context_en) {
        this.context_en = context_en;
    }
}
