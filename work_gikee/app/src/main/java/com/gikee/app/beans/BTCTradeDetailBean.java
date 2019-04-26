package com.gikee.app.beans;

import com.gikee.app.resp.ResponseInfo;

import java.util.List;

public class BTCTradeDetailBean extends ResponseInfo {

    private BTCBaseInfoBean baseInfo;

    private String in_account;

    private String address_in_count;

    private String out_account;

    private String address_out_count;

    private List<BTCAddressFromBean> address_in;

    private List<BTCAddressFromBean> address_out;



    public String getIn_account() {
        return in_account;
    }

    public void setIn_account(String in_account) {
        this.in_account = in_account;
    }



    public String getOut_account() {
        return out_account;
    }

    public void setOut_account(String out_account) {
        this.out_account = out_account;
    }



    public BTCBaseInfoBean getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(BTCBaseInfoBean baseInfo) {
        this.baseInfo = baseInfo;
    }

    public List<BTCAddressFromBean> getAddress_in() {
        return address_in;
    }

    public void setAddress_in(List<BTCAddressFromBean> address_in) {
        this.address_in = address_in;
    }

    public List<BTCAddressFromBean> getAddress_out() {
        return address_out;
    }

    public void setAddress_out(List<BTCAddressFromBean> address_out) {
        this.address_out = address_out;
    }

    public String getAddress_in_count() {
        return address_in_count;
    }

    public void setAddress_in_count(String address_in_count) {
        this.address_in_count = address_in_count;
    }

    public String getAddress_out_count() {
        return address_out_count;
    }

    public void setAddress_out_count(String address_out_count) {
        this.address_out_count = address_out_count;
    }
}
