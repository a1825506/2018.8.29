package com.gikee.app.beans;

public class EOSBaseInfoBean {


    /**
     * expireDate :
     * status :
     * blockHeight :
     * blockHash :
     */

    private String expireDate;
    private String status;
    private String blockHeight;
    private String blockHash;

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(String blockHeight) {
        this.blockHeight = blockHeight;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }
}
