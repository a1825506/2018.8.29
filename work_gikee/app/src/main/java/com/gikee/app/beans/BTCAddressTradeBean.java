package com.gikee.app.beans;

import java.util.List;

public class BTCAddressTradeBean {

    private String confirmations;

    private String tx_time;

    private String tx_hash;

    private List<BTCAddressFromBean> address_from;

    private List<BTCAddressFromBean> address_to;

    public String getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(String confirmations) {
        confirmations = confirmations;
    }


    public String getTime() {
        return tx_time;
    }

    public void setTime(String time) {
        this.tx_time = time;
    }

    public List<BTCAddressFromBean> getAddress_from() {
        return address_from;
    }

    public void setAddress_from(List<BTCAddressFromBean> address_from) {
        this.address_from = address_from;
    }

    public List<BTCAddressFromBean> getAddress_to() {
        return address_to;
    }

    public void setAddress_to(List<BTCAddressFromBean> address_to) {
        this.address_to = address_to;
    }

    public String getTx_hash() {
        return tx_hash;
    }

    public void setTx_hash(String tx_hash) {
        this.tx_hash = tx_hash;
    }
}
