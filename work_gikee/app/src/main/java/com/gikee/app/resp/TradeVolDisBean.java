package com.gikee.app.resp;

public class TradeVolDisBean {
    /**
     * time :
     * hugeCount :
     * bigCount :
     * mediumCount :
     * smallCount :
     * tinyCount :
     */

    private String time;
    private String hugeVolume;
    private String bigVolume;
    private String mediumVolume;
    private String smallVolume;
    private String tinyVolume;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHugeVolume() {
        return hugeVolume;
    }

    public void setHugeVolume(String hugeVolume) {
        this.hugeVolume = hugeVolume;
    }

    public String getBigVolume() {
        return bigVolume;
    }

    public void setBigVolume(String bigVolume) {
        this.bigVolume = bigVolume;
    }

    public String getMediumVolume() {
        return mediumVolume;
    }

    public void setMediumVolume(String mediumVolume) {
        this.mediumVolume = mediumVolume;
    }

    public String getSmallVolume() {
        return smallVolume;
    }

    public void setSmallVolume(String smallVolume) {
        this.smallVolume = smallVolume;
    }

    public String getTinyVolume() {
        return tinyVolume;
    }

    public void setTinyVolume(String tinyVolume) {
        this.tinyVolume = tinyVolume;
    }
}
