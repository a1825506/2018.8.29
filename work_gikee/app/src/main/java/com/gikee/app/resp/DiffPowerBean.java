package com.gikee.app.resp;

public class DiffPowerBean{


    /**
     * time :
     * difficulty : 1
     * power : 1
     */

    private String time;
    private String averageDifficulty;
    private String hashRate;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getAverageDifficulty() {
        return averageDifficulty;
    }

    public void setAverageDifficulty(String averageDifficulty) {
        this.averageDifficulty = averageDifficulty;
    }

    public String getHashRate() {
        return hashRate;
    }

    public void setHashRate(String hashRate) {
        this.hashRate = hashRate;
    }
}
