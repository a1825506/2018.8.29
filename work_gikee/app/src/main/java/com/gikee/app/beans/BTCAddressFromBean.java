package com.gikee.app.beans;

import com.gikee.app.resp.ResponseInfo;

public class BTCAddressFromBean extends ResponseInfo {

    private String address;

    private String amount;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
