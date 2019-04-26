package com.gikee.app.beans;

import java.util.List;

public class HashDetailBean {


    /**
     * tradehash :
     * from :
     * to :
     * amount :
     * time :
     * status :
     * gasdetail : {"gaslimit":"","gas_used_by_txn":"","gas_price":"","actual_tx_cost":""}
     * tokendetail : {"from":"","to":"","token":""}
     * transferdetail : {"from":"","to":"","token":""}
     */


    private ETHBaseInfoBean hashDetail;
    private GasdetailBean gasDetail;
    private List<TokendetailBean> tokenDetail;//ETH代币转账
    private List<TokendetailBean> transferdetail;//ETH合约内部转账
    private List<InlineTradeBean> inlineTransaction;//EOS内联交易



    public GasdetailBean getGasdetail() {
        return gasDetail;
    }

    public void setGasdetail(GasdetailBean gasdetail) {
        this.gasDetail = gasdetail;
    }



    public ETHBaseInfoBean getBaseInfo() {
        return hashDetail;
    }

    public void setBaseInfo(ETHBaseInfoBean baseInfo) {
        this.hashDetail = baseInfo;
    }

    public List<TokendetailBean> getTokendetail() {
        return tokenDetail;
    }

    public void setTokendetail(List<TokendetailBean> tokendetail) {
        this.tokenDetail = tokendetail;
    }

    public List<TokendetailBean> getTransferdetail() {
        return transferdetail;
    }

    public void setTransferdetail(List<TokendetailBean> transferdetail) {
        this.transferdetail = transferdetail;
    }

}
