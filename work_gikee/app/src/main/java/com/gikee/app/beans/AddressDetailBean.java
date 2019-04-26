package com.gikee.app.beans;

import java.util.List;

public class AddressDetailBean {


    /**
     * token_balance :
     * balance :
     */

    private String token_balance;
    private String balance;
    private String totalt_trade;
    private String balance_usd;
    private boolean is_contract;
    private List<TokendBean> token_detail;
    private List<HashTradeBean> hash_detail;



    public String getToken_balance() {
        return token_balance;
    }

    public void setToken_balance(String token_balance) {
        this.token_balance = token_balance;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }


    public String getTotal_trade() {
        return totalt_trade;
    }

    public void setTotal_trade(String total_trade) {
        this.totalt_trade = total_trade;
    }

    public List<TokendBean> getToken_detail() {
        return token_detail;
    }

    public void setToken_detail(List<TokendBean> token_detail) {
        this.token_detail = token_detail;
    }

    public List<HashTradeBean> getHash_detail() {
        return hash_detail;
    }

    public void setHash_detail(List<HashTradeBean> hash_detail) {
        this.hash_detail = hash_detail;
    }

    public boolean isIs_contract() {
        return is_contract;
    }

    public void setIs_contract(boolean is_contract) {
        this.is_contract = is_contract;
    }

    public String getBalance_usd() {
        return balance_usd;
    }

    public void setBalance_usd(String balance_usd) {
        this.balance_usd = balance_usd;
    }
}
