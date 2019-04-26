package com.gikee.app.beans;

/**
 * Created by THINKPAD on 2018/7/6.
 */

public class ZhanghuPopBean {
    private boolean isChose;
    private String name;

    public ZhanghuPopBean(boolean isChose, String name) {
        this.isChose = isChose;
        this.name = name;
    }

    public boolean isChose() {
        return isChose;
    }

    public void setChose(boolean chose) {
        isChose = chose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
