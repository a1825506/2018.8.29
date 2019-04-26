package com.gikee.app.resp;

public class AllAddCountBean {
    /**
     * time :
     * allAddCount : 1
     * ownAddCount : 1
     */

    private String time;
    private int allAddCount;
    private int ownAddCount;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getAllAddCount() {
        return allAddCount;
    }

    public void setAllAddCount(int allAddCount) {
        this.allAddCount = allAddCount;
    }

    public int getOwnAddCount() {
        return ownAddCount;
    }

    public void setOwnAddCount(int ownAddCount) {
        this.ownAddCount = ownAddCount;
    }
}
