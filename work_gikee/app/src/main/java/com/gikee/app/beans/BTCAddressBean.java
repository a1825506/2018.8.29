package com.gikee.app.beans;

import java.util.List;


public class BTCAddressBean {

    private String balance;

    private String total_received;

    private String trade_count;

    private List<BTCAddressTradeBean>  address_trade;


    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        balance = balance;
    }

    public String getTotal_received() {
        return total_received;
    }

    public void setTotal_received(String total_received) {
        this.total_received = total_received;
    }


    public List<BTCAddressTradeBean> getAddress_trade() {
        return address_trade;
    }

    public void setAddress_trade(List<BTCAddressTradeBean> address_trade) {
        this.address_trade = address_trade;
    }

    public String getTrade_count() {
        return trade_count;
    }

    public void setTrade_count(String trade_count) {
        this.trade_count = trade_count;
    }
}
