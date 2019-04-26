package com.gikee.app.beans;

/**
 *
 */
public class IsAddressBean {
    private boolean isAddress;
    private String balance;
    private String type;

    public boolean isAddress() {
        return isAddress;
    }

    public void setAddress(boolean address) {
        isAddress = address;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
