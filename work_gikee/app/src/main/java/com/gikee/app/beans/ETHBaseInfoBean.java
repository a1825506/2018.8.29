package com.gikee.app.beans;

public class ETHBaseInfoBean {

    private String tradehash;
    private String from;
    private String to;
    private String amount;
    private String time;
    private String height;
    private String status;
    private String block_height;
    private Boolean is_contract;

    public String getTradehash() {
        return tradehash;
    }

    public void setTradehash(String tradehash) {
        this.tradehash = tradehash;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Boolean getIs_contract() {
        return is_contract;
    }

    public void setIs_contract(Boolean is_contract) {
        this.is_contract = is_contract;
    }

    public String getBlock_height() {
        return block_height;
    }

    public void setBlock_height(String block_height) {
        this.block_height = block_height;
    }
}
