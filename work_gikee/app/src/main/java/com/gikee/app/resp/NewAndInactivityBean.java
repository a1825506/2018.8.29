package com.gikee.app.resp;

public class NewAndInactivityBean {


    /**
     * time : 1
     * newCount : 1
     * inactiveCount : 1
     * wakeCount : 1
     */

    private String time;
    private String newCount;
    private String inactiveCount;
    private String wakeAccount;
    private String choosetype;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNewCount() {
        return newCount;
    }

    public void setNewCount(String newCount) {
        this.newCount = newCount;
    }

    public String getInactiveCount() {
        return inactiveCount;
    }

    public void setInactiveCount(String inactiveCount) {
        this.inactiveCount = inactiveCount;
    }

    public String getWakeCount() {
        return wakeAccount;
    }

    public void setWakeCount(String wakeCount) {
        this.wakeAccount = wakeCount;
    }

    public String getChoosetype() {
        return choosetype;
    }

    public void setChoosetype(String choosetype) {
        this.choosetype = choosetype;
    }
}
