package com.gikee.app.beans;

import java.util.List;

public class EOSTradeDetailBean {

    private EOSBaseInfoBean baseInfo;

    private List<TokendetailBean> tokenTrade;

    private List<InlineTradeBean> inlineTransaction;

    public EOSBaseInfoBean getBaseInfoBean() {
        return baseInfo;
    }

    public void setBaseInfoBean(EOSBaseInfoBean baseInfo) {
        this.baseInfo = baseInfo;
    }


    public List<TokendetailBean> getTokenTrade() {
        return tokenTrade;
    }

    public void setTokenTrade(List<TokendetailBean> tokenTrade) {
        this.tokenTrade = tokenTrade;
    }

    public List<InlineTradeBean> getInlineTransaction() {
        return inlineTransaction;
    }

    public void setInlineTransaction(List<InlineTradeBean> inlineTransaction) {
        this.inlineTransaction = inlineTransaction;
    }
}
