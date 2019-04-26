package com.gikee.app.beans;

import java.util.List;

public class EOSAddressBean {

    private String Staked;

    private String Unstaked;

    private String RefundTime;

    private String createTime;

    private String createBy;

    private NetStatusBean netStatus;

    private List<TokendBean> token_detail;

    private List<HashTradeBean> hash_detail;

    public String getStaked() {
        return Staked;
    }

    public void setStaked(String staked) {
        Staked = staked;
    }

    public String getUnstaked() {
        return Unstaked;
    }

    public void setUnstaked(String unstaked) {
        Unstaked = unstaked;
    }

    public String getRefundTime() {
        return RefundTime;
    }

    public void setRefundTime(String refundTime) {
        RefundTime = refundTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public NetStatusBean getNetStatus() {
        return netStatus;
    }

    public void setNetStatus(NetStatusBean netStatus) {
        this.netStatus = netStatus;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
