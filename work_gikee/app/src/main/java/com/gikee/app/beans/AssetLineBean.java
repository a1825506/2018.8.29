package com.gikee.app.beans;

public class AssetLineBean {
    /**
     * time : 2018-09-21
     * value_in : 20100
     * value_out : 121212
     */

    private String time;
    private double value_in;
    private double value_out;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getValue_in() {
        return value_in;
    }

    public void setValue_in(double value_in) {
        this.value_in = value_in;
    }

    public double getValue_out() {
        return value_out;
    }

    public void setValue_out(double value_out) {
        this.value_out = value_out;
    }
}
