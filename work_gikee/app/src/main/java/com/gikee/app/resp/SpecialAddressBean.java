package com.gikee.app.resp;

public class SpecialAddressBean {


    /**
     * from :
     * fromName :
     * to :
     * toName :
     * value : 1
     * unit :
     * latestTime :
     */

    private String from;
    private String fromName;
    private String to;
    private String toName;
    private float value;
    private String unit;
    private String latestTime;
    private String txHash;
    private String fromExchange;
    private String toExchange;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(String latestTime) {
        this.latestTime = latestTime;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public String getFromExchange() {
        return fromExchange;
    }

    public void setFromExchange(String fromExchange) {
        this.fromExchange = fromExchange;
    }

    public String getToExchange() {
        return toExchange;
    }

    public void setToExchange(String toExchane) {
        this.toExchange = toExchane;
    }
}
