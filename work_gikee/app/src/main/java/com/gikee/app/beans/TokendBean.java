package com.gikee.app.beans;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gikee.app.resp.ResponseInfo;

import java.util.List;

public class TokendBean extends ResponseInfo implements MultiItemEntity {

    private int type;

    private String trade_type;

    private String title;

    private String value;

    private List<String> value_list;

    private List<TokendetailBean> transferdetail;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    public List<String> getValue_list() {
        return value_list;
    }

    public void setValue_list(List<String> value_list) {
        this.value_list = value_list;
    }

    public List<TokendetailBean> getTransferdetail() {
        return transferdetail;
    }

    public void setTransferdetail(List<TokendetailBean> transferdetail) {
        this.transferdetail = transferdetail;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }
}
